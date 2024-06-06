package org.example.job;

import org.example.dao.entity.LinksEntity;
import org.example.dao.repository.LinksRepository;
import org.example.kafka.KafkaProducer;
import org.example.service.DBServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class LinksDeleteScheduler {
    //@Scheduled(cron = "0 * * * * *", zone = "Europe/Moscow")
    @Autowired
    private final KafkaProducer kafkaProducer;
    @Autowired
    private final LinksRepository linksRepository;
    @Autowired
    private final DBServiceImpl dbService;

    public LinksDeleteScheduler(KafkaProducer kafkaProducer, LinksRepository linksRepository, DBServiceImpl dbService) {
        this.kafkaProducer = kafkaProducer;
        this.linksRepository = linksRepository;
        this.dbService = dbService;
    }

    @Scheduled(cron = "0 * * * * *", zone = "Europe/Moscow")
    public void deleteLinks() {
        System.out.println("scheduler");

        List<LinksEntity> entities = linksRepository.findAll();

        for (int i = 0; i < entities.size(); i++) {
            if(ChronoUnit.SECONDS.between(entities.get(i).getUpdatedAt(), Instant.now()) >= 30) {
                kafkaProducer.sendMessage(entities.get(i).getShortCode());
            }
        }
    }
}
