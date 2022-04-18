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
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailConsumerService {

    private final ObjectMapper objectMapper;
    private final EmailService emailService;

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
                "Sua a meta de R$ "+String.format("%.2f", emailEntity.getGoal())+" da sua Vakinha \""+emailEntity.getTitle()+"\" foi atingida com sucesso!");

        try {
            log.info("Vakinha fechada e e-mail enviado!");
            emailService.send(messageDTO);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}