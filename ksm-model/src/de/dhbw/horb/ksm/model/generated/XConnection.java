//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.04.27 at 01:09:52 PM MESZ 
//


package de.dhbw.horb.ksm.model.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CONNECTION_TYPE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CONNECTION_TYPE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="properties" type="{http://www.ba-horb.de/~ksm/xml/ksm-1}PROPERTIES_TYPE"/>
 *       &lt;/sequence>
 *       &lt;attribute name="to" type="{http://www.ba-horb.de/~ksm/xml/ksm-1}NODE_ID_TYPE" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CONNECTION_TYPE", propOrder = {
    "properties"
})
public class XConnection {

    @XmlElement(required = true)
    protected XProperties properties;
    @XmlAttribute(name = "to")
    protected String to;

    /**
     * Gets the value of the properties property.
     * 
     * @return
     *     possible object is
     *     {@link XProperties }
     *     
     */
    public XProperties getProperties() {
        return properties;
    }

    /**
     * Sets the value of the properties property.
     * 
     * @param value
     *     allowed object is
     *     {@link XProperties }
     *     
     */
    public void setProperties(XProperties value) {
        this.properties = value;
    }

    /**
     * Gets the value of the to property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTo() {
        return to;
    }

    /**
     * Sets the value of the to property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTo(String value) {
        this.to = value;
    }

}
