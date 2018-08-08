package edu.tutorials.oracle.exceptions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipFileContents {
    public static void main(String[] args) {
        writeToFileZipFileContents("./res/zipfile.zip", "./res/zipfilecontent.txt");
        String zipFileContent = readFileWithZipFileContents("./res/zipfilecontent.txt");
        System.out.println(zipFileContent);
    }

    public static void writeToFileZipFileContents(String zipFileName, String outputFileName) {
        Charset charset = StandardCharsets.UTF_8;
        Path outputFilePath = Paths.get(outputFileName);

        try (ZipFile zf = new ZipFile(zipFileName);
             BufferedWriter writer = Files.newBufferedWriter(outputFilePath, charset)
        ) {
            for (Enumeration entries = zf.entries(); entries.hasMoreElements(); ) {
                String newLine = System.getProperty("line.separator");
                String zipEntryName = ((ZipEntry) entries.nextElement()).getName() + newLine;
                writer.write(zipEntryName);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String readFileWithZipFileContents(String inputFileName) {
        Charset charset = StandardCharsets.UTF_8;
        StringBuilder zipFileContents = new StringBuilder();

        try (BufferedReader reader
                 = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName), charset))
        ) {
            String line = reader.readLine();
            String newLine = System.getProperty("line.separator");
            while (line != null) {
                zipFileContents.append(line + newLine);
                line = reader.readLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return zipFileContents.toString();
    }
}
