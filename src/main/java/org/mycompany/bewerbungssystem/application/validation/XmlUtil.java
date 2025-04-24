package org.mycompany.bewerbungssystem.application.validation;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.mycompany.bewerbung.BewerbungDTO;

public class XmlUtil {

	public static String convertToXml(BewerbungDTO bewerbungDTO) {
		try {
			// JAXBContext für das DTO erstellen
			JAXBContext context = JAXBContext.newInstance(BewerbungDTO.class);

			// Marshaller erstellen
			Marshaller marshaller = context.createMarshaller();

			// StringWriter für den XML-String
			StringWriter writer = new StringWriter();

			// DTO in XML umwandeln und in den StringWriter schreiben
			marshaller.marshal(bewerbungDTO, writer);

			return writer.toString(); // Gibt den XML-String zurück
		} catch (JAXBException e) {
			e.printStackTrace();
			return null; // Fehler bei der Umwandlung
		}
	}
}
