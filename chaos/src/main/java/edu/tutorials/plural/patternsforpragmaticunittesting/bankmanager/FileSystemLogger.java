package edu.tutorials.plural.patternsforpragmaticunittesting.bankmanager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;

class FileSystemLogger implements Logger {
    private Path path;

    FileSystemLogger(String logPath) throws IOException {
        path = Paths.get(logPath);
        if (!Files.exists(path))
            Files.createFile(path);
    }

    @Override
    public void writeLine(String message) throws IOException {
        Files.write(path, Collections.singletonList(message), StandardOpenOption.APPEND);
    }
}
