package edu.frameworks.org.jxls;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

public class ObjectWithArrayDemo {
    public static void main(String[] args) {
        try (InputStream is = GettingStartedJxlsDemo.class.getResourceAsStream("/xlsdemo/object-with-array.xls")) {
            try (OutputStream os = new FileOutputStream("target/object-with-array-output.xls")) {
                Context context = new Context();
                context.putVar("data", generateData());
                JxlsHelper.getInstance().processTemplate(is, os, context);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ObjectWithArray generateData() {
        ObjectWithArray data = new ObjectWithArray(LocalDateTime.now().toString());
        IntStream.range(1, 10).forEach(data::addNumber);
        System.out.println(data);
        return data;
    }
}

