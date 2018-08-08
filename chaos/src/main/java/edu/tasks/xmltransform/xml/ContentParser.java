package edu.tasks.xmltransform.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class ContentParser extends DefaultHandler {
    private static final String FILENAME = "./res/notebook.xml";

    public static void main(String[] args) {
        DefaultHandler handler = new ContentParser();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(new File(FILENAME), handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startDocument() throws SAXException {
        System.out.println("Содержимое файла " + FILENAME + ":");
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("Конец содержимого файла " + FILENAME);
    }

    public void startElement(String uri, String sName, String qName, Attributes attrs) throws SAXException {
        if (attrs != null) {
            for (int i = 0; i < attrs.getLength(); i++) {
                System.out.println(attrs.getValue(i) + " ");
            }
        }
    }

    public void characters(char buf[], int offset, int len) throws SAXException {
        String string = new String(buf, offset, len);
        string = string.replaceAll("[\n\t]", "");
        if (!string.equals("")) {
            System.out.println(string);
        }
    }
}
