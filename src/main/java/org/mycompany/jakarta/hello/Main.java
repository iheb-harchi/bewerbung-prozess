package org.mycompany.jakarta.hello;

import org.mycompany.bewerbungssystem.infrastructure.messaging.BewerbungMessageProducer;

import jakarta.xml.bind.JAXBException;

public class Main {

	public static void main(String[] args) throws JAXBException {
		BewerbungMessageProducer sender = new BewerbungMessageProducer();
		String messageBody = String.format("Neue Bewerbung eingereicht. ID: %d, BewerberId: %s, JobId: %s",
				1, 2, 3);
		sender.send(messageBody);
	}
}
