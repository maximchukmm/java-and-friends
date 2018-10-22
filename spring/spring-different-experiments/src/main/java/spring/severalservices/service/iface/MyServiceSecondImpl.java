package spring.severalservices.service.iface;

import org.springframework.stereotype.Component;
import spring.severalservices.service.impl.ServiceType;

import static spring.severalservices.service.impl.ServiceType.SECOND;

@Component
public class MyServiceSecondImpl implements MyService {
    @Override
    public boolean isSupport(ServiceType serviceType) {
        return serviceType == SECOND;
    }

    @Override
    public void whoAmI() {
        System.out.println("I am MyServiceSecondImpl.");
    }
}
