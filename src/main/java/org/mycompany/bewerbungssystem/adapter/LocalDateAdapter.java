package org.mycompany.bewerbungssystem.adapter;

import java.time.LocalDate;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateAdapter extends XmlAdapter<Object, LocalDate> {

    @Override
	public LocalDate unmarshal(Object v) throws Exception {
		if (v == null) {
			return null;
		}

		if (v instanceof XMLGregorianCalendar) {
			XMLGregorianCalendar xmlCal = (XMLGregorianCalendar) v;
			return LocalDate.of(xmlCal.getYear(), xmlCal.getMonth(), xmlCal.getDay());
		} else if (v instanceof String) {
			return LocalDate.parse((String) v);
		} else {
			throw new IllegalArgumentException("Unsupported type: " + v.getClass());
		}
    }

    @Override
    public XMLGregorianCalendar marshal(LocalDate v) throws Exception {
		if (v == null)
			return null;
        DatatypeFactory df = DatatypeFactory.newInstance();
		return df.newXMLGregorianCalendarDate(v.getYear(), v.getMonthValue(), v.getDayOfMonth(),
				DatatypeConstants.FIELD_UNDEFINED);
    }
}