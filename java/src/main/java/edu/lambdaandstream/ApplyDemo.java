package edu.lambdaandstream;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

class ApplyDemo {
    public static void main(String[] args) {
        PeopleDemo people = PeopleDemo.createPeople();
        System.out.println("name");
        people.printField(PersonApplyDemo::getName);

        System.out.println();

        System.out.println("age");
        people.printField(PersonApplyDemo::getAge);

        System.out.println();

        System.out.println("sex");
        people.printField(PersonApplyDemo::getSex);

        System.out.println();
    }
}

interface FieldExtractor extends Function<PersonApplyDemo, Object> {
}

class PersonApplyDemo {
    private String name;
    private int age;
    private boolean sex;

    PersonApplyDemo(String name, int age, boolean sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    int getAge() {
        return age;
    }

    void setAge(int age) {
        this.age = age;
    }

    boolean getSex() {
        return sex;
    }

    void setSex(boolean sex) {
        this.sex = sex;
    }
}

class PeopleDemo {
    private List<PersonApplyDemo> people;

    static PeopleDemo createPeople() {
        List<PersonApplyDemo> list = new ArrayList<>();
        list.add(new PersonApplyDemo("One", 1, false));
        list.add(new PersonApplyDemo("Two", 2, true));
        list.add(new PersonApplyDemo("Three", 3, false));
        return new PeopleDemo(list);
    }

    PeopleDemo(List<PersonApplyDemo> people) {
        this.people = people;
    }

    List<PersonApplyDemo> getPeople() {
        return people;
    }

    void setPeople(List<PersonApplyDemo> people) {
        this.people = people;
    }

    void printField(FieldExtractor fieldExtractor) {
        for (int i = 0; i < getPeople().size(); i++) {
            System.out.println(fieldExtractor.apply(getPeople().get(i)));
        }
    }
}
