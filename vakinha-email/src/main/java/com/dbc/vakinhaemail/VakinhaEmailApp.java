package com.dbc.vakinhaemail;


import java.util.Arrays;

import com.dbc.vakinhaemail.dto.MessageDTO;
import com.dbc.vakinhaemail.service.MailService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class VakinhaEmailApp {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				VakinhaEmailApp.class.getPackage().getName());

		MailService mailService = applicationContext.getBean(MailService.class);
		mailService.enviar(new MessageDTO("Vakinha <vakinhavemser@gmail.com\n>",
				//SE FOR TESTAR TROCA ESSE E-MAIL AQUI
				Arrays.asList("Gabriel Poersch <gabpoersch@gmail.com>")
				, "Aula Spring E-mail", "Olá! \n\n O envio de e-mail deu certo!"));

		applicationContext.close();

		System.out.println("Fim!");
	}
}
