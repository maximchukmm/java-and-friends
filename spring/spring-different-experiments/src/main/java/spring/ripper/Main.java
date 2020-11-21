package spring.ripper;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.ripper.bean.BookOfQuoters;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(RipperConfiguration.class);
        context.getBean(BookOfQuoters.class).sayQuotes();
    }
}