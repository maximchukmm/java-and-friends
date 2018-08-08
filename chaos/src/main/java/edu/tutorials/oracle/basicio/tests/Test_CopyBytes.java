package edu.tutorials.oracle.basicio.tests;

import java.io.FileInputStream;
import java.io.IOException;

public class Test_CopyBytes {
    public static void main(String[] args) throws IOException {

        FileInputStream in = null;
        CharacterEntriesCounter counter = null;

        try {
            in = new FileInputStream("resources\\xanadu.txt");
            int c;
            counter = new CharacterEntriesCounter();

            while ((c = in.read()) != -1) {
                counter.add((char) c);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (in != null) {
                in.close();
            }
            if (counter != null) {
                counter.printThemAll();
            }
        }
    }
}

class CharacterEntriesCounter {
    private int capacity = 40;
    private int size = 0;
    private char[] chars = new char[capacity];
    private int[] entries = new int[capacity];

    private int findCharacterEntry(char ch) {
        for (int i = 0; i < size; i++) {
            if (chars[i] == ch) {
                return i;
            }
        }
        return -1;
    }

    private void ensureCapacity(int newCapacity) {
        if (newCapacity >= capacity) {
            capacity *= 2;
            char[] tempChars = new char[capacity];
            int[] tempEntries = new int[capacity];
            for (int i = 0; i < size; i++) {
                tempChars[i] = chars[i];
                tempEntries[i] = entries[i];
            }
            chars = tempChars;
            entries = tempEntries;
        }
    }

    public void add(char ch) {
        int ind = findCharacterEntry(ch);
        if (ind != -1) {
            entries[ind]++;
        } else {
            ensureCapacity(size + 1);
            chars[size] = ch;
            entries[size] = 1;
            size++;
        }
    }

    public void printThemAll() {
        for (int i = 0; i < size; i++) {
            if (chars[i] == (char) 13) {
                System.out.println("13" + " = " + ((int) chars[i]) + " : " + entries[i]);
            } else if (chars[i] == (char) 10) {
                System.out.println("10" + " = " + ((int) chars[i]) + " : " + entries[i]);
            } else {
                System.out.println(chars[i] + " = " + ((int) chars[i]) + " : " + entries[i]);
            }
        }
    }
}
