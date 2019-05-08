package experiments.builder.setter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassWithOwnSetter {
    private String name;
    private int age;

    public static class ClassWithOwnSetterBuilder {
        private String name;

        public ClassWithOwnSetterBuilder name(String name) {
            this.name = name;
            System.out.println("Set name = " + this.name);
            return this;
        }
    }
}
