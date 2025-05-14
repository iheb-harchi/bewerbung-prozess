package org.mycompany.bewerbungssystem.infrastructure.messaging;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.jms.Destination;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSException;
import jakarta.jms.JMSProducer;
import jakarta.jms.TextMessage;

@ApplicationScoped
public class BewerbungMessageProducer {
	private static final String QUEUE_NAME = "DEV.QUEUE.1";

	public void send(String messageBody) {
		try (JMSContext context = MqUtils.createContext()) {
			Destination destination = context.createQueue("queue:///" + QUEUE_NAME);
			JMSProducer producer = context.createProducer();

			TextMessage message = context.createTextMessage(messageBody);
			producer.send(destination, message);

			System.out.println("[BewerbungMessageProducer] Gesendet: " + messageBody);
		} catch (JMSException e) {
			System.err.println("[BewerbungMessageProducer] Sende-Fehler: " + e.getMessage());
		}
	}

}
