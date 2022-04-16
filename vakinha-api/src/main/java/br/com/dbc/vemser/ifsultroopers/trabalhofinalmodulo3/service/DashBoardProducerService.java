package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.service;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.donateLog.DonateLogDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.DonateEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashBoardProducerService {

    private final KafkaTemplate<String,String> kafkaTemplate;
    private final ObjectMapper objectMapper;


    public void send (DonateEntity donateEntity, String category, Integer operation) {
        String jsonMessege = null;
        DonateLogDTO donateLogDTO = objectMapper.convertValue(donateEntity, DonateLogDTO.class);
        donateLogDTO.setCategory(category);
        donateLogDTO.setOperation(operation);
        try {
            jsonMessege = objectMapper.writeValueAsString(donateLogDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (jsonMessege != null){
            Message<String> message = MessageBuilder.withPayload(jsonMessege)
                    .setHeader(KafkaHeaders.TOPIC, "dashBoardLogs")
                    .setHeader(KafkaHeaders.MESSAGE_KEY, UUID.randomUUID().toString())
                    .setHeader(KafkaHeaders.PARTITION_ID, operation)
                    .build();

            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(message);

            String finalMensagemJson = jsonMessege;
            future.addCallback(new ListenableFutureCallback<>() {
                @Override
                public void onSuccess(SendResult result) {
                }

                @Override
                public void onFailure(Throwable ex) {
                    log.error(" Erro ao publicar no kafka com a mensagem: {}", finalMensagemJson, ex);
                }
            });
        }
    }
}
