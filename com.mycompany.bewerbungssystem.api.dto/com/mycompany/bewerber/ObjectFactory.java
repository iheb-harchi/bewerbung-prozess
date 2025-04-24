//
// Diese Datei wurde mit der Eclipse Implementation of JAXB, v3.0.0 generiert 
// Siehe https://eclipse-ee4j.github.io/jaxb-ri 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2025.04.23 um 11:57:45 AM CEST 
//


package com.mycompany.bewerber;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.mycompany.bewerber package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Bewerber_QNAME = new QName("http://www.mycompany.com/bewerber", "bewerber");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.mycompany.bewerber
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BewerberType }
     * 
     */
    public BewerberType createBewerberType() {
        return new BewerberType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BewerberType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BewerberType }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.mycompany.com/bewerber", name = "bewerber")
    public JAXBElement<BewerberType> createBewerber(BewerberType value) {
        return new JAXBElement<BewerberType>(_Bewerber_QNAME, BewerberType.class, null, value);
    }

}
