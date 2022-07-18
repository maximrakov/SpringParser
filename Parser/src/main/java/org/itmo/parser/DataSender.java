package org.itmo.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
@EnableScheduling
public class DataSender {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Scheduled(fixedRate = 100000)
    public void getInfo() {
        Document page = null;
        try {
            page = Jsoup.connect("https://www.worldometers.info/coronavirus/").get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String population = Jsoup.parseBodyFragment(page.getElementsByClass("maincounter-number").html()).body().getElementsByTag("span").first().text();
        StringBuilder tmpPopulation = new StringBuilder(population);
        for(int i = 0; i < tmpPopulation.length(); i++) {
            if(tmpPopulation.charAt(i) == ',') {
                tmpPopulation.deleteCharAt(i);
                i--;
            }
        }
        rabbitTemplate.convertAndSend("myqueue", tmpPopulation.toString());
    }
}
