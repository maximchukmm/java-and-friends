package spring.ripper.awaredemo;

import org.springframework.beans.factory.Aware;

public interface AwareInterfaceDemo extends Aware {
    void printHello();

    void printGoodbye();
}
