package org.mycompany.jakarta.hello;

import java.time.LocalDate;

import org.mycompany.bewerbungssystem.application.validation.SchematronValidator;
import org.mycompany.bewerbungssystem.application.validation.ValidationResult;
import org.mycompany.bewerbungssystem.application.validation.XmlUtil;
import org.mycompany.bewerbungssystem.application.validation.XsdValidator;

import com.mycompany.bewerber.BewerberDTO;
import com.mycompany.bewerber.LandTyp;

import jakarta.xml.bind.JAXBException;

public class Main {

	public static void main(String[] args) throws JAXBException {
		BewerberDTO dto = new BewerberDTO();
		dto.setId(1L);
		dto.setVorname("Max");
		dto.setNachname("Mustermann");
		dto.setGeburtsdatum(LocalDate.of(1990, 1, 1));
		dto.setEmail("max.mustermann@example.com");
//		dto.setStrasse("Beispielstraße");
//		dto.setHausNr((short) 1);
//		dto.setPLZ("12345");
		dto.setLand(LandTyp.DEUTSCHLAND);
		dto.setTelefon("0123456789");

		// 2. Validierung durchführen
//		ValidationResult result = SchematronValidator.validate1(dto, BewerberDTO.class, "schematron/bewerber.sch"
//		);

//		ValidationResult result = SchematronValidator.validate1(dto, "schematron/bewerber.sch");
		String xml = XmlUtil.marshalToXml(dto); // Marshalling zu XML-String
		ValidationResult result = SchematronValidator.validate(dto, "/schematron/bewerber.sch");
		ValidationResult result1 = XsdValidator.validate(dto, "xsd/bewerber.xsd");
//		if (result1.isValid()) {
//			System.out.println("✅ XsdValidator: Validierung erfolgreich!");
//		} else {
//			System.out.println("❌XsdValidator: Validierungsfehler gefunden:");
//			result1.getErrors().forEach(System.err::println);
//		}
		if (result.isValid()) {
			System.out.println("✅ SchematronValidator: Validierung erfolgreich!");
		} else {
			System.out.println("❌ SchematronValidator: Validierungsfehler gefunden:");
			result.getErrors().forEach(System.err::println);
		}

	}

}
