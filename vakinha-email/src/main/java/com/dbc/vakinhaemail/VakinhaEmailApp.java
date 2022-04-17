package com.dbc.vakinhaemail;


import com.dbc.vakinhaemail.dto.MessageDTO;
import com.dbc.vakinhaemail.service.EmailService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class VakinhaEmailApp {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				VakinhaEmailApp.class.getPackage().getName());

		EmailService emailService = applicationContext.getBean(EmailService.class);
		emailService.send(new MessageDTO("Vakinha <vakinhavemser@gmail.com\n>",
				"Gabriel Poersch <gabpoersch@gmail.com>",
				"Aula Spring E-mail",
				"Olá! \n\n O envio de e-mail deu certo!"));

		applicationContext.close();

		System.out.println("Fim!");
	}
}
