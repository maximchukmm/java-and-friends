package experiments.builder.setter;

public class BuilderAndOwnSetterDemo {
    public static void main(String[] args) {
        ClassWithOwnSetter paul = ClassWithOwnSetter
            .builder()
            .name("Paul")
            .age(10)
            .build();

        System.out.println(paul);
    }
}
