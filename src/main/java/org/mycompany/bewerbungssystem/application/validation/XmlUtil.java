package org.mycompany.bewerbungssystem.application.validation;

import java.io.StringWriter;

import com.mycompany.bewerber.BewerberDTO;
import com.mycompany.bewerbung.BewerbungDTO;
import com.mycompany.job.JobDTO;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class XmlUtil {

	public static <T> String marshalToXml(T dto) throws JAXBException {
		StringWriter writer = new StringWriter();

		JAXBElement element = getElement(dto);

		// JAXBContext erstellen
		JAXBContext context = JAXBContext.newInstance(dto.getClass());
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

		writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		marshaller.marshal(element, writer);

		return writer.toString();
	}

	private static <T> JAXBElement getElement(T dto) {
		if (dto.getClass() == BewerbungDTO.class) {
			return new com.mycompany.bewerbung.ObjectFactory().createBewerbung((BewerbungDTO) dto);
		} else if (dto.getClass() == BewerberDTO.class) {
			return new com.mycompany.bewerber.ObjectFactory().createBewerber((BewerberDTO) dto);
		} else if (dto.getClass() == JobDTO.class) {
			return new com.mycompany.job.ObjectFactory().createJob((JobDTO) dto);
		} else {
			throw new IllegalArgumentException("No matching ObjectFactory for class: " + dto.getClass());
		}
	}
}