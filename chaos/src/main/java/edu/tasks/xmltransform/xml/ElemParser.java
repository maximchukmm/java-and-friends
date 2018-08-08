package edu.tasks.xmltransform.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class ElemParser extends DefaultHandler {
    private static final String FILENAME = "./res/transform/transformed.xml";
    private static final String elem = "entry";
    private boolean isElem;

    public static void main(String[] args) {
        DefaultHandler handler = new ElemParser();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(new File(FILENAME), handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Элемент: " + elem);
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("Конец содержимого файла " + FILENAME);
    }

    @Override
    public void startElement(String uri, String sName, String qName, Attributes attrs) throws SAXException {
        if (qName.equals(elem)) {
            if (attrs.getLength() > 0) {
                for (int i = 0; i < attrs.getLength(); i++) {
                    System.out.println(attrs.getValue(i));
                }
            }
        }
    }

    // @Override
    // public void endElement(String uri, String sName, String qName) throws SAXException {
    //     if (qName.equals(elem)) {
    //         isElem = false;
    //     }
    // }
    //
    // @Override
    // public void characters(char buf[], int offset, int len) throws SAXException {
    //     if (isElem) {
    //         System.out.println(new String(buf, offset, len));
    //     }
    // }
}
