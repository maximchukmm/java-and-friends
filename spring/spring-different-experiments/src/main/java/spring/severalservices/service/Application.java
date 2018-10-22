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

        ServiceHolder serviceHolder = context.getBean(ServiceHolder.class);

        ServiceType[] serviceTypes = ServiceType.values();

        for (int i = 0; i < serviceTypes.length; i++) {
            MyService myService = serviceHolder.getByType(serviceTypes[i]);

            myService.whoAmI();
        }

        Map<Integer, String> mySuperMap = (Map<Integer, String>) context.getBean("mySuperMap");
        mySuperMap.forEach((i, s) -> System.out.println("(" + i + ", " + s + ")"));
    }
}
