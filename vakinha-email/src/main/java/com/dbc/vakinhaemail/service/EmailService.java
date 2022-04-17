package com.dbc.vakinhaemail.service;

import com.dbc.vakinhaemail.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void send(MessageDTO messageDTO) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(messageDTO.getSender());
        simpleMailMessage.setTo(messageDTO.getReceiver());
        simpleMailMessage.setSubject(messageDTO.getSubject());
        simpleMailMessage.setText(messageDTO.getText());

        javaMailSender.send(simpleMailMessage);
    }

}
