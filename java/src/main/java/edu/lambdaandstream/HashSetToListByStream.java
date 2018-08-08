package edu.lambdaandstream;

import edu.util.Person;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class HashSetToListByStream {
    public static void main(String[] args) {
        List<Person> people = Person.getPeople();

        HashSet<Person> set = new HashSet<>(people);

        List<String> surnames = set.stream().map(p -> p.getSurName()).collect(Collectors.toList());

        System.out.println(people);
        System.out.println(surnames);
    }
}
