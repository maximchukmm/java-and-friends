package edu.concurrency;

import java.util.Random;

public class Deadlock {

    private static Random random = new Random();
    private static Account account1 = new Account(1, 100L);
    private static Account account2 = new Account(2, 200L);

    public static void main(String[] args) {
        new Thread(new Worker()).start();
        new Thread(new Worker()).start();
    }

    public static void transfer(final Account source, final Account target, long amount) {
        final Account a1;
        final Account a2;
        a1 = source.compareTo(target) > 0 ? source : target;
        a2 = source.compareTo(target) < 0 ? source : target;
        // if (source.compareTo(target) >= 1) {
        //     a1 = source;
        //     a2 = target;
        // } else {
        //     a1 = target;
        //     a2 = source;
        // }
        synchronized (a1) {
            synchronized (a2) {
                if (source.getBalance() >= amount) {
                    source.withdraw(amount);
                    target.put(amount);
                    System.out.println("Transfered: " + amount);
                } else {
                    System.out.println("Not enough money");
                }
            }
        }
    }

    public static class Worker implements Runnable {

        @Override
        public void run() {
            while (true) {
                if (random.nextBoolean()) {
                    transfer(account1, account2, random.nextInt(10));
                } else {
                    transfer(account2, account1, random.nextInt(10));
                }
            }
        }
    }

    public static class Account implements Comparable<Account> {

        private long balance;
        private int id;

        public Account(int id, long balance) {

            this.balance = balance;
            this.id = id;
        }

        public long getBalance() {
            return balance;
        }

        public void put(long amount) {
            balance += amount;
        }

        public void withdraw(long amount) {
            balance -= amount;
        }

        @Override
        public int compareTo(Account o) {
            return id - o.id;
        }
    }
}
