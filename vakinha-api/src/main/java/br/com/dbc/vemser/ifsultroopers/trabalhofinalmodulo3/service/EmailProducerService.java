package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.service;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.request.RequestDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.request.RequestEmailDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.RequestEntity;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.UsersEntity;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.repository.UsersRepository;
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
public class EmailProducerService {

    private final KafkaTemplate<String,String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final UsersRepository usersRepository;

    public void send (RequestEntity requestEntity) {
        UsersEntity usersEntity = usersRepository.getById(requestEntity.getIdUser());
        String emailToBeUsed = usersEntity.getEmail();

        RequestEmailDTO requestEmailDTO = objectMapper.convertValue(requestEntity, RequestEmailDTO.class);
        requestEmailDTO.setUsername(usersEntity.getUsername());
        requestEmailDTO.setOwnerEmail(emailToBeUsed);
        requestEmailDTO.setTitle(requestEntity.getTitle());
        requestEmailDTO.setGoal(requestEntity.getGoal());

        String jsonMessage = null;

        try {
            jsonMessage = objectMapper.writeValueAsString(requestEmailDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (jsonMessage != null){
            Message<String> message = MessageBuilder.withPayload(jsonMessage)
                    .setHeader(KafkaHeaders.TOPIC, "emails")
                    .setHeader(KafkaHeaders.MESSAGE_KEY, UUID.randomUUID().toString())
                    .build();

            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(message);

            future.addCallback(new ListenableFutureCallback<>() {
                @Override
                public void onSuccess(SendResult result) {
                }

                @Override
                public void onFailure(Throwable ex) {
                    log.error("Erro ao publicar o email no kafka com a mensagem");
                }
            });
        }
    }
}
