package spring.severalservices.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.severalservices.service.iface.MyService;
import spring.severalservices.service.iface.ServiceHolder;
import spring.severalservices.service.impl.ServiceType;

import java.util.Map;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

        System.out.println();

        ServiceHolder serviceHolder = context.getBean(ServiceHolder.class);

        ServiceType[] serviceTypes = ServiceType.values();

        for (ServiceType serviceType : serviceTypes) {
            MyService myService = serviceHolder.getByType(serviceType);
            myService.whoAmI();
        }

        System.out.println();

        Map<Integer, String> mySuperMap = (Map<Integer, String>) context.getBean("mySuperMap");
        mySuperMap.forEach((i, s) -> System.out.println("(" + i + ", " + s + ")"));
    }
}
