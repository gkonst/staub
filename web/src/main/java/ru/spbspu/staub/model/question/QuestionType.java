//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.3-b01-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.01.31 at 05:57:22 PM MSK 
//


package ru.spbspu.staub.model.question;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for question-type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="question-type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;choice>
 *           &lt;element name="single-choice" type="{http://staub.spbspu.ru/Question}choice-type"/>
 *           &lt;element name="multiple-choice" type="{http://staub.spbspu.ru/Question}choice-type"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "question-type", propOrder = {
    "description",
    "singleChoice",
    "multipleChoice"
})
public class QuestionType {

    @XmlElement(required = true)
    protected String description;
    @XmlElement(name = "single-choice")
    protected ChoiceType singleChoice;
    @XmlElement(name = "multiple-choice")
    protected ChoiceType multipleChoice;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the singleChoice property.
     * 
     * @return
     *     possible object is
     *     {@link ChoiceType }
     *     
     */
    public ChoiceType getSingleChoice() {
        return singleChoice;
    }

    /**
     * Sets the value of the singleChoice property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChoiceType }
     *     
     */
    public void setSingleChoice(ChoiceType value) {
        this.singleChoice = value;
    }

    /**
     * Gets the value of the multipleChoice property.
     * 
     * @return
     *     possible object is
     *     {@link ChoiceType }
     *     
     */
    public ChoiceType getMultipleChoice() {
        return multipleChoice;
    }

    /**
     * Sets the value of the multipleChoice property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChoiceType }
     *     
     */
    public void setMultipleChoice(ChoiceType value) {
        this.multipleChoice = value;
    }

}
