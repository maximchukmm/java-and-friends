package edu.other;

import java.util.ArrayList;
import java.util.List;

public class InheritanceDemo {
    public static void main(String[] args) {
        List<Foo> foos = new ArrayList<>();
        foos.add(new Foo());
        foos.add(new Boo());

        foos.forEach(Foo::println);
    }

    private static class Foo {
        protected String v;

        public Foo() {
            this.v = "foo";
            this.println();
        }

        public void println() {
            System.out.println("foo " + this.v);
        }
    }

    private static class Boo extends Foo {
        public Boo() {
            this.v = "boo";
            this.println();
            super.println();
        }

        @Override
        public void println() {
            System.out.println("boo " + this.v);
        }
    }
}

