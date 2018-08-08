package edu.tutorials.plural.patternsforpragmaticunittesting.bankmanager;

import java.io.IOException;

public interface Logger {
    void writeLine(String message) throws IOException;
}
