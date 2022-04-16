package com.dbc.vakinhadashboard.service;

import com.dbc.vakinhadashboard.entity.DonateDashBoardEntity;
import com.dbc.vakinhadashboard.repository.DonateDashBoardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumerService {

    private final ObjectMapper objectMapper;
    private final DonateDashBoardRepository donateDashBoardRepository;

    @KafkaListener(
            topics = "${kafka.topic.log}",
            groupId = "dashBoardDonate",
            containerFactory = "listenerContainerFactory")
    public void consumeDashBoardDonate(@Payload String message,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                        @Header(KafkaHeaders.OFFSET) Long offset,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition) throws Exception {

        DonateDashBoardEntity donateDashBoardEntity= objectMapper.readValue(message, DonateDashBoardEntity.class);

        switch (partition) {
            case 0 -> {
                donateDashBoardRepository.insert(donateDashBoardEntity);
                log.info("Donate de id {} criada!", donateDashBoardEntity.getIdDonate());
            }
            case 1 -> {
                donateDashBoardRepository.update(donateDashBoardEntity);
                log.info("Donate de id {} atualizada!", donateDashBoardEntity.getIdDonate());
            }
            case 2 -> {
                donateDashBoardRepository.deleteById(donateDashBoardEntity.getIdDonate());
                log.info("Donate de id {} deletada!", donateDashBoardEntity.getIdDonate());
            }
            default -> log.error("Operação inválida!");
        }
    }
}
