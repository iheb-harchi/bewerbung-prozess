package org.mycompany.bewerbungssystem.application.validation;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public final class XsdValidator {

	private static final String SCHEMA_LANG = "http://www.w3.org/2001/XMLSchema";

	private XsdValidator() {
	} // Utility class

	public static <T> ValidationResult validate(T dto, String xsdPath) {
		try {
			Schema schema = loadSchema(xsdPath);
			List<String> errors = validateAgainstSchema(XmlUtil.marshalToXml(dto), schema);
			return errors.isEmpty() ? ValidationResult.success() : ValidationResult.failed(errors);
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
}

