package edu.frameworks.org.jxls;

import edu.util.Person;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Первый взгляд на Jxls библиотеку с помощью статьи http://jxls.sourceforge.net/getting_started.html
 */
public class GettingStartedJxlsDemo {
    public static void main(String[] args) {
        List<Person> persons = Person.getPeople();
        try (InputStream is = GettingStartedJxlsDemo.class.getResourceAsStream("/xlsdemo/persons-template.xls")) {
            try (OutputStream os = new FileOutputStream("target/persons-output.xls")) {
                Context context = new Context();
                context.putVar("persons", persons);
                JxlsHelper.getInstance().processTemplate(is, os, context);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
