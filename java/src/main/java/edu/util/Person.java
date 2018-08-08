package edu.util;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class Person implements Comparable<Person> {

    private String givenName;
    private String surName;
    private int age;
    private String email;
    private String phone;
    private String address;

    public static class Builder {

        private String givenName;
        private String surName;
        private int age;
        private String eMail;
        private String phone;
        private String address;

        public Builder surName(String value) {
            surName = value;
            return this;
        }

        public Builder givenName(String value) {
            givenName = value;
            return this;
        }

        public Builder age(int value) {
            age = value;
            return this;
        }

        public Builder email(String value) {
            eMail = value;
            return this;
        }

        public Builder phone(String value) {
            phone = value;
            return this;
        }

        public Builder address(String value) {
            address = value;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }

    private Person(Builder builder) {
        this.givenName = builder.givenName;
        this.surName = builder.surName;
        this.age = builder.age;
        this.email = builder.eMail;
        this.phone = builder.phone;
        this.address = builder.address;
    }

    public static List<Person> getPeople() {
        List<Person> people = new ArrayList<>();

        people.add(
                new Person.Builder()
                        .givenName("Bob")
                        .surName("Baker")
                        .age(45)
                        .email("bob.baker@example.com")
                        .phone("201-121-4678")
                        .address("44 4th St, Smallville, KS 12333")
                        .build()
        );

        people.add(
                new Person.Builder()
                        .givenName("Jane")
                        .surName("Doe")
                        .age(25)
                        .email("jane.doe@example.com")
                        .phone("202-123-4678")
                        .address("33 3rd St, Smallville, KS 12333")
                        .build()
        );

        people.add(
                new Person.Builder()
                        .givenName("John")
                        .surName("Doe")
                        .age(20)
                        .email("john.doe@example.com")
                        .phone("202-123-4678")
                        .address("22 3rd St, Smallville, KS 12333")
                        .build()
        );

        people.add(
                new Person.Builder()
                        .givenName("Ivan")
                        .surName("Ivanov")
                        .age(28)
                        .email("ivan@ivan.ivan")
                        .phone("111")
                        .address("Russia")
                        .build()
        );

        people.add(
                new Person.Builder()
                        .givenName("Ivan")
                        .surName("Ivanov")
                        .age(28)
                        .email("ivan@ivan.ivan")
                        .phone("111")
                        .address("Russia")
                        .build()
        );

        return people;
    }

    public void printSurName() {
        System.out.println(getSurName());
    }

    public void printFullName() {
        System.out.println(getSurName() + " " + getGivenName());
    }

    @Override
    public String toString() {
        return getSurName() + " " + getGivenName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                Objects.equals(givenName, person.givenName) &&
                Objects.equals(surName, person.surName) &&
                Objects.equals(email, person.email) &&
                Objects.equals(phone, person.phone) &&
                Objects.equals(address, person.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(givenName, surName, age, email, phone, address);
    }

    @Override
    public int compareTo(Person person) {
        return toString().compareTo(person.toString());
    }
}
