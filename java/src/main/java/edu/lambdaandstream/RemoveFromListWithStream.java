package edu.lambdaandstream;

import edu.util.Person;

import java.util.List;
import java.util.stream.Collectors;

class RemoveFromListWithStream {
    public static void main(String[] args) {
        List<Person> people = Person.getPeople();
        people.add(new Person.Builder()
            .givenName("123")
            .surName("456")
            .age(21)
            .email("bob.baker@example.com")
            .phone("201-121-4678")
            .address("44 4th St, Smallville, KS 12333")
            .build());

        System.out.println(people);
        people = removePersonFromCollection(people);
        System.out.println(people);
    }

    static List<Person> removePersonFromCollection(List<Person> people) {
        return people.stream().filter(person -> !person.getGivenName().equals("123")).collect(Collectors.toList());
    }
}
