package org.example.kafka;

import org.example.dao.entity.LinksEntity;
import org.example.dao.repository.LinksRepository;
import org.example.service.DBServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class KafkaConsumer {
    @Autowired
    private final DBServiceImpl dbService;
    @Autowired
    private final LinksRepository linksRepository;

    public KafkaConsumer(DBServiceImpl dbService, LinksRepository linksRepository) {
        this.dbService = dbService;
        this.linksRepository = linksRepository;
    }

    @KafkaListener(topics = "to_delete", groupId = "my_consumer")
    public void listen(String shortCode) {
        System.out.println("To delete: " + shortCode);

        Optional<LinksEntity> entity = linksRepository.findById(shortCode);

        if(entity.isPresent() && ChronoUnit.SECONDS.between(entity.get().getUpdatedAt(), Instant.now()) >= 30) {
            dbService.deletePairOfLinks(entity.get().getLongLink(), entity.get().getShortCode());
            System.out.println(shortCode + " deleted");
        }
    }
}

