package edu.tutorials.oracle.lambda;

import edu.util.Person;

import java.util.List;

class AgeCalculations {
    public static void main(String[] args) {
        List<Person> people = Person.getPeople();

        int totalAge = people
            .stream()
            .mapToInt(Person::getAge)
            .sum();
        System.out.println(totalAge);

        int totalAgeWithoutDoes = people
            .stream()
            .filter(p -> !p.getSurName().equals("Doe"))
            .mapToInt(Person::getAge)
            .sum();
        System.out.println(totalAgeWithoutDoes);

        int totalAgeWithoutJohnDoe = people
            .stream()
            .filter(p -> !(p.getSurName().equals("Doe") && p.getGivenName().equals("John")))
            .mapToInt(Person::getAge)
            .sum();
        System.out.println(totalAgeWithoutJohnDoe);
    }
}
