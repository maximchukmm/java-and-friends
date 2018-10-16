package spring.spel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class ValueAnnotationDemoBean {
    @Value("#{1 + 1 + 1}")
    private int three;

    ValueAnnotationDemoBean() {
    }

    public int getThree() {
        return three;
    }

    public void setThree(int three) {
        this.three = three;
    }

    @Override
    public String toString() {
        return "ValueAnnotationDemo{" +
            "three=" + three +
            '}';
    }
}