package edu.tutorials.oracle.lambda;

import edu.util.Person;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class ComparatorAnonymAndLambda {
    public static void main(String[] args) {

        List<Person> shortList = Person.getPeople();

        // Sort with inner class
        Collections.sort(shortList, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getSurName().compareTo(o2.getSurName());
            }
        });

        System.out.println("=========ASC SORT SURNAME BY INNER CLASS========");
        shortList.forEach(Person::printFullName);

        // Use lambdaAndStream instead
        shortList = Person.getPeople();
        Collections.sort(shortList, (Person p1, Person p2) -> p1.getSurName().compareTo(p2.getSurName()));
        System.out.println("=========ASC SORT SURNAME BY LAMBDA========");
        shortList.stream().forEach(person -> person.printFullName());

        // revert sort
        shortList = Person.getPeople();
        Collections.sort(shortList, (p1, p2) -> p2.getSurName().compareTo(p1.getSurName()));
        System.out.println("=========DESC SORT SURNAME BY LAMBDA========");
        for (Person p : shortList) {
            p.printFullName();
        }

        System.out.println();

        shortList = Person.getPeople();
        shortList.sort((p1, p2) -> p1.getSurName().compareTo(p2.getSurName()));
        System.out.println(shortList);

        System.out.println();

        shortList = Person.getPeople();
        shortList.sort(Comparator.comparing(Person::getSurName));
        shortList.forEach(Person::printFullName);

        System.out.println();

        shortList.sort(Comparator.naturalOrder());

        System.out.println();
    }
}
