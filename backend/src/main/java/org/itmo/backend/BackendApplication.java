package org.itmo.backend;

import org.itmo.backend.entity.StatRecord;
import org.itmo.backend.service.StatRecordService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.sql.Date;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableJpaRepositories("org.itmo.backend.repository")
@EntityScan("org.itmo.backend.entity")
public class BackendApplication {

	private StatRecordService statRecordService;

	@Autowired
	public BackendApplication(StatRecordService statRecordService) {
		this.statRecordService = statRecordService;
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@RabbitListener(queues = "myqueue")
	public void listen(String input) {
		StatRecord statRecord = new StatRecord();
		statRecord.setDate(new Date(System.currentTimeMillis()));
		statRecord.setAmount((long) Integer.parseInt(input));
		statRecordService.save(statRecord);
	}
}