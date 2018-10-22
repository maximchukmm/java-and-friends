package spring.severalservices.service;

import spring.severalservices.service.iface.MyService;
import spring.severalservices.service.impl.ServiceType;

public interface ServiceHolder {
    MyService getByType(ServiceType serviceType);
}
