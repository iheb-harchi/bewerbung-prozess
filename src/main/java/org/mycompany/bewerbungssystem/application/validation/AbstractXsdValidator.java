package org.mycompany.bewerbungssystem.application.validation;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import com.mycompany.bewerbung.BewerbungDTO;
import com.mycompany.bewerbung.ObjectFactory;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public abstract class AbstractXsdValidator {

	public static <T> boolean validateDto(T dto, Class<T> dtoClass, String xsdPath) {
		try {
			String xml = convertToXml(dto, dtoClass);
			return validateXml(xml, xsdPath);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

//	private static <T> String convertToXml(T dto, Class<T> dtoClass) throws JAXBException {
//		
//		JAXBContext context = JAXBContext.newInstance(dtoClass);
//		Marshaller marshaller = context.createMarshaller();
//		StringWriter writer = new StringWriter();
//		marshaller.marshal(dto, new StreamResult(writer));
//		return writer.toString();
//	}

	private static boolean validateXml(String xml, String xsdPath) throws IOException, SAXException {
		SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		Schema schema = factory.newSchema(new File(xsdPath));
		javax.xml.validation.Validator validator = schema.newValidator();
		try {
			validator.validate(new StreamSource(new StringReader(xml)));
			return true;
		} catch (SAXException e) {
			return false;
		}
	}
	
	private static <T> String convertToXml(T dto, Class<T> dtoClass) throws JAXBException {
	    // ObjectFactory verwenden
	    ObjectFactory factory = new ObjectFactory();
		JAXBElement<BewerbungDTO> element = factory.createBewerbung((BewerbungDTO) dto);
	    
	    JAXBContext context = JAXBContext.newInstance(BewerbungDTO.class);
	    Marshaller marshaller = context.createMarshaller();
	    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	    marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
	    
	    StringWriter writer = new StringWriter();
	    writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
	    marshaller.marshal(element, writer);
	    
	    return writer.toString();
	}
}

