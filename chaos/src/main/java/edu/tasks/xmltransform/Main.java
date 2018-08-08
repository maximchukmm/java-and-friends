package edu.tasks.xmltransform;

import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Main {

    public static void main(String[] args) {
        System.out.println("Starting program...");

        File xmlFile = new File("./res/1.xml");
        final int N = 12;
        int step = 10_000;
        Connection connection = null;

        try {
            System.out.println("Connecting to database...");

            connection = DriverManager.getConnection("jdbc:sqlite:./res/magnit.db");
            connection.setAutoCommit(true);
            Statement vacuum = connection.createStatement();
            System.out.println("Shrinking size of database by vacuum command...");
            vacuum.executeUpdate("vacuum;");
            vacuum.close();
            Statement checkIfTableTestExists = connection.createStatement();
            ResultSet isTableTestExists = checkIfTableTestExists.executeQuery(
                "select 'TABLE_TEST_EXISTS' " +
                    "where exists ( " +
                    "select name " +
                    "from sqlite_master " +
                    "where name = 'TEST' " +
                    ");");
            if (!isTableTestExists.next()) {
                System.out.println("Table TEST doesn't exist.");
                System.out.println("Creating table TEST (FIELD integer)...");
                Statement createTableTest = connection.createStatement();
                createTableTest.executeUpdate("create table TEST (FIELD integer);");
                createTableTest.close();
            } else {
                if (isTableTestExists.getString(1).equals("TABLE_TEST_EXISTS")) {
                    System.out.println("Table TEST exists.");
                    System.out.println("Deleting table TEST records...");
                    Statement deleteTableTestRecords = connection.createStatement();
                    deleteTableTestRecords.executeUpdate("delete from TEST;");
                    deleteTableTestRecords.close();
                }
            }
            isTableTestExists.close();
            checkIfTableTestExists.close();

            System.out.println("Filling table TEST with " + N + " numbers...");

            Statement insertValues = connection.createStatement();
            StringBuilder insertQuery = new StringBuilder();
            for (int i = 1; i <= N; i += step) {
                insertQuery.append("insert into TEST (FIELD) values ");
                for (int j = i; (j < i + step) && (j <= N); j++) {
                    insertQuery.append("(" + j + "),");
                }
                insertQuery.delete(insertQuery.length() - 1, insertQuery.length());
                insertQuery.append(";");
                insertValues.executeUpdate(insertQuery.toString());
                insertQuery.setLength(0);
            }
            insertValues.close();
        } catch (SQLException sqle) {
            System.out.println("\t" + "Cannot finish filling database test.db properly.");
            System.out.println("\t" + sqle.getMessage());
        }

        System.out.println("Checking existance of file " + xmlFile.getName() + "...");

        if (!xmlFile.exists()) {
            try {
                xmlFile.createNewFile();
                System.out.println("File " + xmlFile.getName() + " created.");
            } catch (IOException ioe) {
                System.out.println("\t" + "Cannot create file " + xmlFile.getName() + ".");
            }
        } else {
            System.out.println("File " + xmlFile.getName() + " already exists.");
            System.out.println("Delete data from file " + xmlFile.getName());
            try {
                if (!xmlFile.delete()) {
                    throw new IOException("Cannot delete file " + xmlFile.getName());
                }
                if (xmlFile.createNewFile()) {
                    System.out.println("Recreate file " + xmlFile.getName() + ".");
                }
            } catch (IOException ioe) {
                System.out.println("Cannot recreate file " + xmlFile.getName() + ".");
            }
        }

        try (BufferedWriter writer = new BufferedWriter(
            new OutputStreamWriter(new FileOutputStream(xmlFile, true), "UTF-8"), 8192 * 2)) {
            try {
                int n = N;
                if (n != 0) {
                    System.out.println("Saving data from table TEST to " + xmlFile.getName() + "...");

                    StringBuilder xmlContent = new StringBuilder();
                    String nl = System.getProperty("line.separator");
                    int index = 0;
                    PreparedStatement getValues = connection.prepareStatement("select FIELD from TEST limit ?, ?;");
                    ResultSet values = null;
                    writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + nl);
                    writer.write("<entries>" + nl);
                    String openEntryTag = "<entry>" + nl;
                    String closeEntryTag = "</entry>" + nl;
                    String openFieldTag = "<field>";
                    String closeFieldTag = "</field>" + nl;
                    while (index < n) {
                        getValues.setInt(1, index);
                        getValues.setInt(2, step);
                        index += step;
                        values = getValues.executeQuery();
                        while (values.next()) {
                            xmlContent.append(openEntryTag);
                            xmlContent.append(openFieldTag + values.getInt(1) + closeFieldTag);
                            xmlContent.append(closeEntryTag);
                        }
                        writer.write(xmlContent.toString());
                        xmlContent.setLength(0);
                    }
                    writer.write("</entries>" + nl);
                    values.close();
                    getValues.close();
                }
            } catch (SQLException sqle) {
                System.out.println("Cannot read values from database.");
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                        System.out.println("Closing connection to database...");
                    } catch (SQLException sqle) {
                        System.out.println("Cannot close connection to database.");
                    }
                }
            }
        } catch (IOException ioe) {
            System.out.println("\t" + "Cannot save xml-document.");
        }

        System.out.println("Transforming xml-document " + xmlFile.getName() + "...");

        Document document;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //factory.setNamespaceAware(true);
        //factory.setValidating(true);
        try {
            String stylesheetFilePath = "./res/stylesheet.xsl";
            String dataFilePath = "./res/1.xml";
            File stylesheet = new File(stylesheetFilePath);
            File datafile = new File(dataFilePath);

            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(datafile);

            // Use a Transformer for output
            TransformerFactory tFactory = TransformerFactory.newInstance();
            StreamSource stylesource = new StreamSource(stylesheet);
            Transformer transformer = tFactory.newTransformer(stylesource);

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new BufferedWriter(
                new OutputStreamWriter(
                    new FileOutputStream("./res/2.xml"),
                    "UTF-8")));
            transformer.transform(source, result);
        } catch (TransformerConfigurationException tce) {
            // Error generated by the parser
            System.out.println("\n** Transformer Factory error");
            System.out.println("   " + tce.getMessage());

            // Use the contained exception, if any
            Throwable x = tce;

            if (tce.getException() != null) {
                x = tce.getException();
            }

            x.printStackTrace();
        } catch (TransformerException te) {
            // Error generated by the parser
            System.out.println("\n** Transformation error");
            System.out.println("   " + te.getMessage());

            // Use the contained exception, if any
            Throwable x = te;

            if (te.getException() != null) {
                x = te.getException();
            }

            x.printStackTrace();
        } catch (SAXException sxe) {
            // Error generated by this application
            // (or a parser-initialization error)
            Exception x = sxe;

            if (sxe.getException() != null) {
                x = sxe.getException();
            }

            x.printStackTrace();
        } catch (ParserConfigurationException pce) {
            // Parser with specified options can't be built
            pce.printStackTrace();
        } catch (IOException ioe) {
            // I/O error
            ioe.printStackTrace();
        }

        System.out.println("Parsing file " + "2.xml...");
        System.out.println("Calculating arithmetic sum...");
        DefaultHandler handler = new ElemParser();
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            saxParser.parse(new File("./res/2.xml"), handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Arithmetic sum from 1 to " + N + " is equal to " + ElemParser.sum);

        System.out.println("Exiting program...");
    }
}

class ElemParser extends DefaultHandler {
    private static final String FILENAME = "./res/2.xml";
    private static final String elem = "entry";
    static long sum = 0;

    @Override
    public void startElement(String uri, String sName, String qName, Attributes attrs) throws SAXException {
        if (qName.equals(elem)) {
            if (attrs.getLength() > 0) {
                for (int i = 0; i < attrs.getLength(); i++) {
                    sum += Long.parseLong(attrs.getValue(i));
                }
            }
        }
    }
}
