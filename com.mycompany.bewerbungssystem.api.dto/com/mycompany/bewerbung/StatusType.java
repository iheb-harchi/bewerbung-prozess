//
// Diese Datei wurde mit der Eclipse Implementation of JAXB, v3.0.0 generiert 
// Siehe https://eclipse-ee4j.github.io/jaxb-ri 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2025.04.23 um 11:57:45 AM CEST 
//


package com.mycompany.bewerbung;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für StatusType.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <pre>
 * &lt;simpleType name="StatusType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="EINGEREICHT"/&gt;
 *     &lt;enumeration value="GEPRUEFT"/&gt;
 *     &lt;enumeration value="ANGENOMMEN"/&gt;
 *     &lt;enumeration value="ABGELEHNT"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "StatusType")
@XmlEnum
public enum StatusType {

    EINGEREICHT,
    GEPRUEFT,
    ANGENOMMEN,
    ABGELEHNT;

    public String value() {
        return name();
    }

    public static StatusType fromValue(String v) {
        return valueOf(v);
    }

}
