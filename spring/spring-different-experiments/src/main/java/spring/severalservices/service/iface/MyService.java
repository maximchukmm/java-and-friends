package spring.severalservices.service.iface;

import spring.severalservices.service.impl.ServiceType;

public interface MyService {
    boolean isSupport(ServiceType serviceType);

    void whoAmI();
}
