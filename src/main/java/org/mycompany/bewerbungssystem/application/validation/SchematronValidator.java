package org.mycompany.bewerbungssystem.application.validation;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.sf.saxon.TransformerFactoryImpl;

public final class SchematronValidator {

    private SchematronValidator() {
    }

	public static <T> ValidationResult validate(T dto, String schematronPath) {

        try {
            List<String> errors = validateAgainstSchematron(XmlUtil.marshalToXml(dto), schematronPath);
            return errors.isEmpty()
                    ? ValidationResult.success()
                    : ValidationResult.failed(errors);
        } catch (Exception e) {
            return ValidationResult.error("Schematron validation failed: " + e.getMessage());
        }
    }

    private static List<String> validateAgainstSchematron(String xml, String schematronPath) {
        List<String> errors = new ArrayList<>();

        try {
            // 1. Compile Schematron to XSLT
            InputStream compiledXslt = compileSchematronToXslt(schematronPath);
            if (compiledXslt == null) {
                errors.add("Failed to compile Schematron to XSLT");
                return errors;
            }

            // 2. Prepare transformation with Saxon
            TransformerFactory factory = new TransformerFactoryImpl();
            Source xsltSource = new StreamSource(compiledXslt);
            // Set system ID to resolve relative paths
            xsltSource.setSystemId(SchematronValidator.class.getResource(schematronPath).toString());
            
            Transformer transformer = factory.newTransformer(xsltSource);

            // 3. Set error listener
            transformer.setErrorListener(new javax.xml.transform.ErrorListener() {
                @Override
                public void warning(TransformerException e) {
                    errors.add(formatError("WARNING", e));
                }

                @Override
                public void error(TransformerException e) {
                    errors.add(formatError("ERROR", e));
                }

                @Override
                public void fatalError(TransformerException e) {
                    errors.add(formatError("FATAL", e));
                }
            });

            // 4. Run validation
            StringWriter output = new StringWriter();
            transformer.transform(
                new StreamSource(new StringReader(xml)),
                new StreamResult(output)
            );

            // 5. Parse SVRL output
            parseSvrlForErrors(output.toString(), errors);

        } catch (Exception e) {
            errors.add("Schematron processing error: " + e.getMessage());
            e.printStackTrace();
        }

        return errors;
    }

    private static InputStream compileSchematronToXslt(String schematronPath) {
        try {
			// Verwende die kompilierte XSLT aus dem Zielverzeichnis
			InputStream xsltStream = new FileInputStream("target/generated-sources/schematron/bewerber.xsl");
			return xsltStream;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void parseSvrlForErrors(String svrlOutput, List<String> errors) {
        // Implement proper SVRL parsing here
        if (svrlOutput.contains("failed-assert")) {
            // Simple error extraction - improve with proper XML parsing
            String[] parts = svrlOutput.split("<svrl:text>");
            for (int i = 1; i < parts.length; i++) {
                String error = parts[i].split("</svrl:text>")[0];
                errors.add(error.trim());
            }
        }
    }

    private static String formatError(String level, TransformerException e) {
        return String.format("[%s] %s", level, e.getMessage());
    }
}