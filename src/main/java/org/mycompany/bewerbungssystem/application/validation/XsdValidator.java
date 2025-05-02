package org.mycompany.bewerbungssystem.application.validation;

import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.mycompany.bewerbung.BewerbungDTO;
import com.mycompany.bewerbung.ObjectFactory;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public final class XsdValidator {

	private static final String SCHEMA_LANG = "http://www.w3.org/2001/XMLSchema";

	private XsdValidator() {
	} // Utility class

	public static <T> ValidationResult validate(T dto, Class<T> dtoClass, String xsdPath) {
		try {
			String xml = marshalToXml(dto, dtoClass);
			Schema schema = loadSchema(xsdPath);
			List<String> errors = validateAgainstSchema(xml, schema);
			return new ValidationResult(errors.isEmpty(), errors);
		} catch (Exception e) {
			return ValidationResult.error("Validation failed: " + e.getMessage());
		}
	}

	private static Schema loadSchema(String xsdPath) throws SAXException {
		SchemaFactory factory = SchemaFactory.newInstance(SCHEMA_LANG);
		URL xsdUrl = XsdValidator.class.getClassLoader().getResource(xsdPath);
		if (xsdUrl == null) {
			throw new IllegalArgumentException("XSD resource not found: " + xsdPath);
		}
		return factory.newSchema(xsdUrl);
	}

	private static List<String> validateAgainstSchema(String xml, Schema schema) {
		List<String> errors = new ArrayList<>();
		Validator validator = schema.newValidator();

		validator.setErrorHandler(new ErrorHandler() {
			@Override
			public void warning(SAXParseException e) {
				errors.add(formatError("WARNING", e));
			}

			@Override
			public void error(SAXParseException e) {
				errors.add(formatError("ERROR", e));
			}

			@Override
			public void fatalError(SAXParseException e) {
				errors.add(formatError("FATAL", e));
			}
		});

		try {
			validator.validate(new StreamSource(new StringReader(xml)));
		} catch (Exception e) {
			errors.add("Validation exception: " + e.getMessage());
		}

		return errors;
	}

	private static String formatError(String level, SAXParseException e) {
		return String.format("[%s] Line %d, Col %d: %s", level, e.getLineNumber(), e.getColumnNumber(), e.getMessage());
	}

//	private static <T> String marshalToXml(T dto, Class<T> dtoClass) throws JAXBException {
//		ObjectFactory factory = new ObjectFactory();
//		JAXBElement<BewerbungDTO> element = factory.createBewerbung((BewerbungDTO) dto);
//
//		JAXBContext context = JAXBContext.newInstance(dtoClass);
//		Marshaller marshaller = context.createMarshaller();
//		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
//		StringWriter writer = new StringWriter();
//		writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
//		marshaller.marshal(dto, writer);
//
//		return writer.toString();
//	}


//	public static <T> boolean validateDto(T dto, Class<T> dtoClass, String xsdPath) {
//		try {
//			String xml = convertToXml(dto, dtoClass);
//			return validateXml(xml, xsdPath);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//
//	private static boolean validateXml(String xml, String xsdPath) throws IOException, SAXException {
//		SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
//		Schema schema = factory.newSchema(new File(xsdPath));
//		javax.xml.validation.Validator validator = schema.newValidator();
//		try {
//			validator.validate(new StreamSource(new StringReader(xml)));
//			return true;
//		} catch (SAXException e) {
//			return false;
//		}
//	}
	
//	public static <T> List<String> validateDtoWithErrors(T dto, Class<T> dtoClass, String xsdPath) {
//		List<String> errors = new ArrayList<>();
//
//		try {
//			String xml = convertToXml(dto, dtoClass);
//
//			SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
//			Schema schema = factory.newSchema(new File(xsdPath));
//			javax.xml.validation.Validator validator = schema.newValidator();
//
//			// Eigener ErrorHandler
//			validator.setErrorHandler(new ErrorHandler() {
//				@Override
//				public void warning(SAXParseException exception) {
//					// Optional: Fehler trotzdem aufnehmen
//					errors.add("WARNUNG: " + exception.getMessage());
//				}
//
//				@Override
//				public void error(SAXParseException exception) {
//					errors.add(String.format("FEHLER (Zeile %d, Spalte %d): %s", exception.getLineNumber(),
//							exception.getColumnNumber(), exception.getMessage()));
//				}
//
//				@Override
//				public void fatalError(SAXParseException exception) {
//					errors.add("SCHWERER FEHLER: " + exception.getMessage());
//				}
//			});
//
//			validator.validate(new StreamSource(new StringReader(xml)));
//
//		} catch (SAXException | JAXBException | RuntimeException e) {
//			errors.add("Interner Fehler beim Validieren: " + e.getMessage());
//		} catch (Exception e) {
//			errors.add("Fehler beim Validieren: " + e.getMessage());
//		}
//
//		return errors;
//	}

	private static <T> String marshalToXml(T dto, Class<T> dtoClass) throws JAXBException {
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

	public static class ValidationResult {
		private final boolean valid;
		private final List<String> errors;

		ValidationResult(boolean valid, List<String> errors) {
			this.valid = valid;
			this.errors = Collections.unmodifiableList(errors);
		}

		public static ValidationResult error(String message) {
			return new ValidationResult(false, List.of(message));
		}

		public boolean isValid() {
			return valid;
		}

		public List<String> getErrors() {
			return errors;
		}
	}
}

