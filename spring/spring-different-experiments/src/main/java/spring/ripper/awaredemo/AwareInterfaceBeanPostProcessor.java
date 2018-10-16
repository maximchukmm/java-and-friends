package spring.ripper.awaredemo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class AwareInterfaceBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Aware) {
            if (bean instanceof AwareInterfaceDemo) {
                ((AwareInterfaceDemo) bean).printHello();
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Aware) {
            if (bean instanceof AwareInterfaceDemo) {
                ((AwareInterfaceDemo) bean).printGoodbye();
            }
        }
        return bean;
    }
}
