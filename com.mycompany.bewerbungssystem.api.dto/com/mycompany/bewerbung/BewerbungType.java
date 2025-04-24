//
// Diese Datei wurde mit der Eclipse Implementation of JAXB, v3.0.0 generiert 
// Siehe https://eclipse-ee4j.github.io/jaxb-ri 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2025.04.23 um 11:57:45 AM CEST 
//


package com.mycompany.bewerbung;

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für BewerbungType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="BewerbungType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="bewerberId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="jobId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="anschreiben" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="eingereichtAm" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="status" type="{http://www.mycompany.com/bewerbung}StatusType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BewerbungType", propOrder = {
    "id",
    "bewerberId",
    "jobId",
    "anschreiben",
    "eingereichtAm",
    "status"
})
public class BewerbungType {

    protected Long id;
    protected long bewerberId;
    protected long jobId;
    @XmlElement(required = true)
    protected String anschreiben;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar eingereichtAm;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected StatusType status;

    /**
     * Ruft den Wert der id-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Legt den Wert der id-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Ruft den Wert der bewerberId-Eigenschaft ab.
     * 
     */
    public long getBewerberId() {
        return bewerberId;
    }

    /**
     * Legt den Wert der bewerberId-Eigenschaft fest.
     * 
     */
    public void setBewerberId(long value) {
        this.bewerberId = value;
    }

    /**
     * Ruft den Wert der jobId-Eigenschaft ab.
     * 
     */
    public long getJobId() {
        return jobId;
    }

    /**
     * Legt den Wert der jobId-Eigenschaft fest.
     * 
     */
    public void setJobId(long value) {
        this.jobId = value;
    }

    /**
     * Ruft den Wert der anschreiben-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnschreiben() {
        return anschreiben;
    }

    /**
     * Legt den Wert der anschreiben-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnschreiben(String value) {
        this.anschreiben = value;
    }

    /**
     * Ruft den Wert der eingereichtAm-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEingereichtAm() {
        return eingereichtAm;
    }

    /**
     * Legt den Wert der eingereichtAm-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEingereichtAm(XMLGregorianCalendar value) {
        this.eingereichtAm = value;
    }

    /**
     * Ruft den Wert der status-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link StatusType }
     *     
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * Legt den Wert der status-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusType }
     *     
     */
    public void setStatus(StatusType value) {
        this.status = value;
    }

}
