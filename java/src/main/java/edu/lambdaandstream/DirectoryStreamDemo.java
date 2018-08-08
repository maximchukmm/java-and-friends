package edu.lambdaandstream;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class DirectoryStreamDemo {

    private static int dirStreamCounter = 0;

    private static String getTabs(int numberOfTabs) {
        StringBuilder tabs = new StringBuilder();
        for (int i = 1; i < numberOfTabs; i++)
            tabs.append("\t");
        return tabs.toString();
    }

    static void printDirs(Path path, int numberOfTabs) {
        DirectoryStream<Path> directoryStream = null;
        try {
            dirStreamCounter++;
            directoryStream = Files.newDirectoryStream(path);
            for (Path entry : directoryStream) {
                System.out.println(getTabs(numberOfTabs) + entry);
                if (Files.isDirectory(entry))
                    printDirs(entry, numberOfTabs + 1);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (directoryStream != null) {
                try {
                    directoryStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Path currentPath = Paths.get("./");
        printDirs(currentPath, 0);

        System.out.printf("free memory: %d%nmax memory: %d%ntotal memory: %d%n",
            Runtime.getRuntime().freeMemory() / 1024,
            Runtime.getRuntime().maxMemory() / 1024,
            Runtime.getRuntime().totalMemory() / 1024);
        System.out.println("dirStreamCounter: " + dirStreamCounter);
    }
}
