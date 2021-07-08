package edu.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author mmaximchuk
 * @since 08.07.2021
 */
public class CreditProducerDemo {
    private static final List<String> BANK_NAMES = Arrays.asList("Bank1", "Bank2", "Bank3", "Bank4");
    private static final List<String> BANK_NUMBERS = Arrays.asList("Num1", "Num2", "Num3");
    private static final List<String> CURRENCIES = Arrays.asList("EUR", "USD", "MXN", "AUD", "CAD");

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
    private static final Random RANDOM = new Random(System.currentTimeMillis());

    private static JAXBContext jaxbContext;

    static {
        try {
            jaxbContext = JAXBContext.newInstance(CreditMessage.class);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("The first argument must be a host name");
        System.out.println("The second argument must be a queue name");
        System.out.println("Program arguments: " + Arrays.toString(args));
        System.out.println("Ready to produce messages...\n");

        String hostName = args[0];
        String queueName = args[1];

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(hostName);

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(queueName, false, false, false, null);

        Thread thread = new Thread(() -> {
            while (!Thread.interrupted()) {
                String creditMessageXml = randomCreditMessage();

                try {
                    channel.basicPublish("", queueName, null, creditMessageXml.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println(" [x] Sent '" + creditMessageXml + "'");

                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        System.out.println("Press enter to stop producing messages...");
        new Scanner(System.in).nextLine();

        thread.interrupt();
        channel.close();
        connection.close();
    }

    private static String randomCreditMessage() {
        CreditMessage message = new CreditMessage();

        String now = formattedNow();

        message.deltas = new ArrayList<>();

        message.deltas.add(randomDelta(now));
        message.deltas.add(randomDelta(now));
        message.deltas.add(randomDelta(now));

        return messageToString(message);
    }

    private static Delta randomDelta(String date) {
        Delta delta = new Delta();

        delta.bankName = randomBankName();
        delta.bankNumber = randomBankNumber();
        delta.currencySymbol = randomCurrency();
        delta.delta = randomDeltaValue();
        delta.usdEquivalent = randomUsdEquivalent();
        delta.date = date;

        return delta;
    }

    private static String randomBankName() {
        return randomElement(BANK_NAMES);
    }

    private static String randomBankNumber() {
        return randomElement(BANK_NUMBERS);
    }

    private static String randomCurrency() {
        return randomElement(CURRENCIES);
    }

    private static String randomDeltaValue() {
        double value = RANDOM.nextDouble();

        return String.format("%f.2", value);
    }

    private static String randomUsdEquivalent() {
        double value = RANDOM.nextDouble();

        return String.format("%f.2", value);
    }

    private static <T> T randomElement(List<T> list) {
        int randomIndex = RANDOM.nextInt(list.size());

        return list.get(randomIndex);
    }

    private static String formattedNow() {
        LocalDateTime nowUtc = LocalDateTime.now(ZoneId.of("UTC"));

        //2021-07-02 04:48:09.257000
        return nowUtc.format(DATE_TIME_FORMATTER);
    }

    private static String messageToString(CreditMessage message) {
        try {
            Marshaller m = jaxbContext.createMarshaller();

            StringWriter writer = new StringWriter();
            m.marshal(message, writer);

            return writer.toString();
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to marshall a message: " + message);
        }
    }
}
