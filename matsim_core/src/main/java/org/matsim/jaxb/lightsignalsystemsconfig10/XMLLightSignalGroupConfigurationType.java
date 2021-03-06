//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.09.20 at 07:20:42 PM MESZ 
//


package org.matsim.jaxb.lightsignalsystemsconfig10;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.matsim.jaxb.Adapter1;


/**
 * <p>Java class for lightSignalGroupConfigurationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="lightSignalGroupConfigurationType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.matsim.org/files/dtd}idRefType">
 *       &lt;sequence>
 *         &lt;element name="roughcast">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="sec" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="dropping">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="sec" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="interimTimeRoughcast" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="sec" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" default="0" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="interimTimeDropping" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="sec" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" default="0" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "lightSignalGroupConfigurationType", propOrder = {
    "roughcast",
    "dropping",
    "interimTimeRoughcast",
    "interimTimeDropping"
})
public class XMLLightSignalGroupConfigurationType
    extends XMLIdRefType
{

    @XmlElement(required = true)
    protected XMLLightSignalGroupConfigurationType.XMLRoughcast roughcast;
    @XmlElement(required = true)
    protected XMLLightSignalGroupConfigurationType.XMLDropping dropping;
    protected XMLLightSignalGroupConfigurationType.XMLInterimTimeRoughcast interimTimeRoughcast;
    protected XMLLightSignalGroupConfigurationType.XMLInterimTimeDropping interimTimeDropping;

    /**
     * Gets the value of the roughcast property.
     * 
     * @return
     *     possible object is
     *     {@link XMLLightSignalGroupConfigurationType.XMLRoughcast }
     *     
     */
    public XMLLightSignalGroupConfigurationType.XMLRoughcast getRoughcast() {
        return roughcast;
    }

    /**
     * Sets the value of the roughcast property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLLightSignalGroupConfigurationType.XMLRoughcast }
     *     
     */
    public void setRoughcast(XMLLightSignalGroupConfigurationType.XMLRoughcast value) {
        this.roughcast = value;
    }

    /**
     * Gets the value of the dropping property.
     * 
     * @return
     *     possible object is
     *     {@link XMLLightSignalGroupConfigurationType.XMLDropping }
     *     
     */
    public XMLLightSignalGroupConfigurationType.XMLDropping getDropping() {
        return dropping;
    }

    /**
     * Sets the value of the dropping property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLLightSignalGroupConfigurationType.XMLDropping }
     *     
     */
    public void setDropping(XMLLightSignalGroupConfigurationType.XMLDropping value) {
        this.dropping = value;
    }

    /**
     * Gets the value of the interimTimeRoughcast property.
     * 
     * @return
     *     possible object is
     *     {@link XMLLightSignalGroupConfigurationType.XMLInterimTimeRoughcast }
     *     
     */
    public XMLLightSignalGroupConfigurationType.XMLInterimTimeRoughcast getInterimTimeRoughcast() {
        return interimTimeRoughcast;
    }

    /**
     * Sets the value of the interimTimeRoughcast property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLLightSignalGroupConfigurationType.XMLInterimTimeRoughcast }
     *     
     */
    public void setInterimTimeRoughcast(XMLLightSignalGroupConfigurationType.XMLInterimTimeRoughcast value) {
        this.interimTimeRoughcast = value;
    }

    /**
     * Gets the value of the interimTimeDropping property.
     * 
     * @return
     *     possible object is
     *     {@link XMLLightSignalGroupConfigurationType.XMLInterimTimeDropping }
     *     
     */
    public XMLLightSignalGroupConfigurationType.XMLInterimTimeDropping getInterimTimeDropping() {
        return interimTimeDropping;
    }

    /**
     * Sets the value of the interimTimeDropping property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLLightSignalGroupConfigurationType.XMLInterimTimeDropping }
     *     
     */
    public void setInterimTimeDropping(XMLLightSignalGroupConfigurationType.XMLInterimTimeDropping value) {
        this.interimTimeDropping = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="sec" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class XMLDropping {

        @XmlAttribute(required = true)
        @XmlJavaTypeAdapter(Adapter1 .class)
        @XmlSchemaType(name = "nonNegativeInteger")
        protected Integer sec;

        /**
         * Gets the value of the sec property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public Integer getSec() {
            return sec;
        }

        /**
         * Sets the value of the sec property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSec(Integer value) {
            this.sec = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="sec" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" default="0" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class XMLInterimTimeDropping {

        @XmlAttribute
        @XmlJavaTypeAdapter(Adapter1 .class)
        @XmlSchemaType(name = "nonNegativeInteger")
        protected Integer sec;

        /**
         * Gets the value of the sec property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public int getSec() {
            if (sec == null) {
                return new Adapter1().unmarshal("0");
            } else {
                return sec;
            }
        }

        /**
         * Sets the value of the sec property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSec(Integer value) {
            this.sec = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="sec" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" default="0" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class XMLInterimTimeRoughcast {

        @XmlAttribute
        @XmlJavaTypeAdapter(Adapter1 .class)
        @XmlSchemaType(name = "nonNegativeInteger")
        protected Integer sec;

        /**
         * Gets the value of the sec property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public int getSec() {
            if (sec == null) {
                return new Adapter1().unmarshal("0");
            } else {
                return sec;
            }
        }

        /**
         * Sets the value of the sec property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSec(Integer value) {
            this.sec = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="sec" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class XMLRoughcast {

        @XmlAttribute(required = true)
        @XmlJavaTypeAdapter(Adapter1 .class)
        @XmlSchemaType(name = "nonNegativeInteger")
        protected Integer sec;

        /**
         * Gets the value of the sec property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public Integer getSec() {
            return sec;
        }

        /**
         * Sets the value of the sec property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSec(Integer value) {
            this.sec = value;
        }

    }

}
