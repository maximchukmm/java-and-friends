package edu.rabbitmq;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author mmaximchuk
 * @since 08.07.2021
 */
public class Delta {
    @XmlAttribute(name = "Date")
    public String date;

    @XmlAttribute(name = "ClientBankName")
    public String bankName;

    @XmlAttribute(name = "ClientBankNum")
    public String bankNumber;

    @XmlAttribute(name = "Ccy")
    public String currencySymbol;

    @XmlAttribute(name = "Delta")
    public String delta;

    @XmlAttribute(name = "Equiv")
    public String usdEquivalent;

    @Override
    public String toString() {
        return "Delta{" +
            "date='" + date + '\'' +
            ", bankName='" + bankName + '\'' +
            ", bankNumber='" + bankNumber + '\'' +
            ", currencySymbol='" + currencySymbol + '\'' +
            ", delta='" + delta + '\'' +
            ", usdEquivalent='" + usdEquivalent + '\'' +
            '}';
    }
}
