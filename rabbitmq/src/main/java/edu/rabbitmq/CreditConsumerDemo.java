package edu.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;

/**
 * @author mmaximchuk
 * @since 08.07.2021
 */
public class CreditConsumerDemo {
    private static JAXBContext jaxbContext;

    static {
        try {
            jaxbContext = JAXBContext.newInstance(CreditMessage.class);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("The first argument must be a host name");
        System.out.println("The second argument must be a queue name");
        System.out.println("Program arguments: " + Arrays.toString(args));
        System.out.println("Ready to consume messages...\n");

        String hostName = args[0];
        String queueName = args[1];

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(hostName);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String xml = new String(delivery.getBody(), StandardCharsets.UTF_8);

            CreditMessage creditMessage = unmarshall(xml);

            System.out.println(" [x] Received '" + creditMessage + "'" + " at " + ZonedDateTime.now(ZoneId.of("UTC")));
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }

    private static CreditMessage unmarshall(String xml) {
        try {
            Unmarshaller u = jaxbContext.createUnmarshaller();
            return (CreditMessage) u.unmarshal(new StringReader(xml));
        } catch (Exception e) {
            throw new RuntimeException("Failed to unmarshall xml: " + xml);
        }
    }
}