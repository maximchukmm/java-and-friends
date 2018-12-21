package edu.collections;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class IterableInterfaceDemo {
    public static void main(String[] args) {
        String[] data = {"Paul", "John", "George", "Ringo"};

        Names names = new Names(data);

        for (Name name : names) {
            System.out.println(name);
        }
    }

    static class Names implements Iterable<Name> {
        List<Name> names;

        Names(String[] names) {
            this.names = Arrays
                .stream(names)
                .map(Name::new)
                .collect(Collectors.toList());
        }

        @Override
        public Iterator<Name> iterator() {
            return names.iterator();
        }
    }

    static class Name {
        String name;

        Name(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "name: " + name;
        }
    }
}
