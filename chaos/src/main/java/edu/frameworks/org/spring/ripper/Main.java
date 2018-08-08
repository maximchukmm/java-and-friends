package edu.frameworks.org.spring.ripper;

import edu.frameworks.org.spring.ripper.bean.BookOfQuoters;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(RipperConfiguration.class);
        context.getBean(BookOfQuoters.class).sayQuotes();
    }
}