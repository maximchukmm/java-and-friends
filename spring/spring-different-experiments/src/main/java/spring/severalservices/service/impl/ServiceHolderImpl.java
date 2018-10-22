package spring.severalservices.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.severalservices.service.iface.ServiceHolder;
import spring.severalservices.service.iface.MyService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class ServiceHolderImpl implements ServiceHolder {
    private Map<ServiceType, MyService> myServiceMap = new HashMap<>();

    @Autowired
    public ServiceHolderImpl(Collection<MyService> myServices) {
        ServiceType[] serviceTypes = ServiceType.values();
        for (int i = 0; i < serviceTypes.length; i++) {
            ServiceType serviceType = serviceTypes[i];
            MyService myService = findByType(myServices, serviceType);
            myServiceMap.put(serviceType, myService);
        }
    }

    private MyService findByType(Collection<MyService> myServices, ServiceType serviceType) {
        for (MyService myService : myServices) {
            if (myService.isSupport(serviceType)) return myService;
        }

        return null;
    }

    @Override
    public MyService getByType(ServiceType serviceType) {
        return myServiceMap.get(serviceType);
    }
}
