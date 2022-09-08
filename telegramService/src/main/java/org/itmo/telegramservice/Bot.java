package org.itmo.telegramservice;

import org.itmo.telegramservice.entity.StatRecordDTO;
import org.itmo.telegramservice.entity.User;
import org.itmo.telegramservice.service.TgUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class Bot extends TelegramLongPollingBot {
    Map<Integer, String> userStatus = new HashMap<>();

    RestTemplate restTemplate;
    TgUserDAO tgUserDAO;

    @Autowired
    public Bot(TgUserDAO tgUserDAO) {
        this.tgUserDAO = tgUserDAO;
        restTemplate = new RestTemplate();
    }

    @Value("${username}")
    private String username;
    @Value("${token}")
    private String token;

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) {
            Message message = update.getMessage();
            if(!userStatus.containsKey(Math.toIntExact(message.getChatId()))) {
                SendMessage response = new SendMessage();
                Long chatId = message.getChatId();
                response.setChatId(String.valueOf(chatId));
                String text = "Please enter login";
                userStatus.put(Math.toIntExact(message.getChatId()), "login");
                response.setText(text);
                try {
                    execute(response);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                return;
            }
            if(userStatus.get(Math.toIntExact(message.getChatId())).equals("login")) {
                tgUserDAO.save(message.getText(), Math.toIntExact(message.getChatId()));
                userStatus.put(Math.toIntExact(message.getChatId()), "password");
                return;
            }

            if(userStatus.get(Math.toIntExact(message.getChatId())).equals("password")) {
                User user = new User();
                user.setUsername(tgUserDAO.getLogin(Math.toIntExact(message.getChatId())));
                user.setPassword(message.getText());
                HttpHeaders headers = new HttpHeaders();
                headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
                ResponseEntity<String> result = restTemplate.exchange("http://localhost:9006/login",HttpMethod.POST, new HttpEntity<User>(user, headers), String.class);
                tgUserDAO.saveJWT(Math.toIntExact(message.getChatId()), result.getHeaders().getFirst("Authorization"));
                userStatus.put(Math.toIntExact(message.getChatId()), "ok");
                return;
            }

            if(message.getText().equals("/stat")) {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add("Authorization", tgUserDAO.getJWT(Math.toIntExact(message.getChatId())));
                ResponseEntity<StatRecordDTO> response = restTemplate.exchange("http://localhost:9001/current", HttpMethod.GET,
                        new HttpEntity<String>(httpHeaders),
                        StatRecordDTO.class);
                SendMessage responseMessage = new SendMessage();
                responseMessage.setChatId(String.valueOf(message.getChatId()));
                responseMessage.setText(response.getBody().getAmount().toString());
                try {
                    execute(responseMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
