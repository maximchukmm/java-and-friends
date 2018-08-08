package edu.tutorials.plural.patternsforpragmaticunittesting.bankmanager;

import java.io.IOException;

class Logging {
    private static Logger logger = null;

    static Logger getInstance() throws IOException {
        if (logger == null)
            logger = new FileSystemLogger("./bank.log");
        return logger;
    }

    static void setLogger(Logger logger) {
        Logging.logger = logger;
    }

    static void writeLine(String message) throws IOException {
        getInstance().writeLine(message);
    }
}
