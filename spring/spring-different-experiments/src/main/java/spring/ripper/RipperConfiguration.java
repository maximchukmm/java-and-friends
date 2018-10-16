package spring.ripper;

import spring.ripper.awaredemo.AwareInterfaceBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("spring.ripper")
public class RipperConfiguration {
    @Bean(name = "springQuoter")
    Quoter springQuoter() {
        SpringQuoter springQuoter = new SpringQuoter();
        springQuoter.setMessage("Spring is big.");
        return springQuoter;
    }

    @Bean(name = "terminatorQuoter")
    Quoter terminatorQuoter() {
        TerminatorQuoter terminatorQuoter = new TerminatorQuoter();
        terminatorQuoter.setMessage("I'll be back!");
        return terminatorQuoter;
    }

    @Bean
    InjectRandomIntAnnotationBeanPostProcessor injectRandomIntAnnotationBeanPostProcessor() {
        return new InjectRandomIntAnnotationBeanPostProcessor();
    }

    @Bean
    AwareInterfaceBeanPostProcessor awareInterfaceBeanPostProcessor() {
        return new AwareInterfaceBeanPostProcessor();
    }
}
