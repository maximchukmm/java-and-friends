package spring.taskexecutingandscheduling.contextdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtils {
    private static ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    public static <BEAN> BEAN getBean(Class<BEAN> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }
}
