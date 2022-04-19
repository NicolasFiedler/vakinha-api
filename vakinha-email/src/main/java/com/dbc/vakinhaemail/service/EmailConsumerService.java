package com.dbc.vakinhaemail.service;

import com.dbc.vakinhaemail.dto.MessageDTO;
import com.dbc.vakinhaemail.entity.EmailEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailConsumerService {

    private final ObjectMapper objectMapper;
    private final EmailService emailService;
    private Queue<MessageDTO> queue = new LinkedList<>();

    @KafkaListener(
            topics = "${kafka.topic.log}",
            groupId = "emails",
            containerFactory = "listenerContainerFactory")
    public void consumeEmailService(@Payload String message,
                                    @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                                    @Header(KafkaHeaders.OFFSET) Long offset) throws Exception {

        EmailEntity emailEntity = objectMapper.readValue(message, EmailEntity.class);

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setSender("Vakinha <vakinhavemser@gmail.com>");
        messageDTO.setReceiver(emailEntity.getOwnerEmail());
        messageDTO.setSubject("Sua Vakinha atingiu a meta!");
        messageDTO.setText("Olá, "+emailEntity.getUsername()+"!\n\n"+
                "Sua meta de R$ "+String.format("%.2f", emailEntity.getGoal())+" da sua Vakinha \""+emailEntity.getTitle()+"\" foi atingida com sucesso!");

        queue.add(messageDTO);
    }

    @Scheduled(fixedDelay = 30000)
    private void enviaEmail () {
        int i = 0;
        while (!queue.isEmpty() && i < 10){

            log.info("Vakinha fechada e e-mail enviado!");
            emailService.send(Objects.requireNonNull(queue.poll()));

            i++;
        }
    }
}