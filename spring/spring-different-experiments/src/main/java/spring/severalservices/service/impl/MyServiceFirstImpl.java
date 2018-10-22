package spring.severalservices.service.impl;

import org.springframework.stereotype.Component;
import spring.severalservices.service.iface.MyService;

import static spring.severalservices.service.impl.ServiceType.FIRST;

@Component
public class MyServiceFirstImpl implements MyService {
    @Override
    public boolean isSupport(ServiceType serviceType) {
        return serviceType == FIRST;
    }

    @Override
    public void whoAmI() {
        System.out.println("I am MyServiceFirstImpl.");
    }
}
