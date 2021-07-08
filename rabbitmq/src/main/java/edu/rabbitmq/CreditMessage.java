package edu.rabbitmq;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author mmaximchuk
 * @since 08.07.2021
 */
@XmlRootElement(name = "deltas")
public class CreditMessage {
    @XmlElement(name = "delta", required = true)
    public List<Delta> deltas;

    @Override
    public String toString() {
        return "CreditMessage{" +
            "deltas=" + deltas +
            '}';
    }
}
