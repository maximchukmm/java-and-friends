package edu.other;

public class TryWithResourcesDemo {
    public static void main(String[] args) {
        try (A a = new A()) {
            a.foo();
        } catch (Exception e) {
            System.out.println("Main: " + e.getMessage());

            Throwable[] suppressed = e.getSuppressed();
            for (Throwable t : suppressed) {
                printSuppressed(t);
            }
        }
    }

    private static void printSuppressed(Throwable throwable) {
        System.out.println("Suppressed: " + throwable.getMessage());

        Throwable[] suppressed = throwable.getSuppressed();
        for (Throwable t : suppressed) {
            printSuppressed(t);
        }
    }

    private static class A implements AutoCloseable {
        private final B b = new B();

        public void foo() {
            throw new RuntimeException("A.foo()");
        }

        @Override
        public void close() {
            if (true) {
                throw new RuntimeException("A.close()");
            }
            b.close();
        }
    }

    private static class B implements AutoCloseable {
        @Override
        public void close() {
            throw new RuntimeException("B.close()");
        }
    }
}
