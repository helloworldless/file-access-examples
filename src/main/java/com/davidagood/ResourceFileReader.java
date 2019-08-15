package com.davidagood;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ResourceFileReader {

    private static final String RESOURCES_ROOT = "/";

    private List<String> readLinesNio(Path path) throws IOException {
        try {
            System.out.println("Started - Reading lines from file at: " + path);
            List<String> lines = Files.readAllLines(path);
            System.out.println("Completed - Reading lines from file at: " + path);
            return lines;
        } catch (NoSuchFileException e) {
            tryPrintResourceFileDiagnostics();
            System.out.printf("Failed to read file [%s]%n", path);
            throw e;
        }

    }

    private Path getResourcePath(String resourceRelativePath) {
        String prefixedResourceRelativePath = RESOURCES_ROOT + resourceRelativePath;
        try {
            return Paths.get(this.getClass().getResource(prefixedResourceRelativePath).toURI());
        } catch (Exception e) {
            String message = String.format("Failed to find [%s] in [%s]; Error: %s",
                    resourceRelativePath,
                    this.getClass().getResource(RESOURCES_ROOT),
                    e);
            System.out.println(message);
            if (resourceRelativePath.startsWith("/")) {
                String unnecessaryForwardSlashMessage = String.format("Provided resource relative path [%s] begins with a \"/\", but the prefix  \"/\" is automatically added; " +
                        "Try removing the leading \"/\"", resourceRelativePath);
                message = message + "; " + unnecessaryForwardSlashMessage;
                System.out.println(unnecessaryForwardSlashMessage);
            }
            tryPrintResourceFileDiagnostics();
            throw new RuntimeException(message);
        }
    }

    private void tryPrintResourceFileDiagnostics() {
        try {
            Path resourcesPath = Paths.get(this.getClass().getResource(RESOURCES_ROOT).toURI());
            String files = Files.walk(resourcesPath, 1, FileVisitOption.FOLLOW_LINKS)
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.joining(", "));
            System.out.printf("Found these files/folders at [%s]: %s%n", resourcesPath, files);
        } catch (URISyntaxException | IOException e2) {
            System.out.printf("Tried to get resource from this directory: %s%n", this.getClass().getResource(RESOURCES_ROOT));

        }
    }

    public ReadResourceFileLinesResult readLines(String resourceRelativeFilePath) {
        try {
            List<String> lines = readLinesNio(getResourcePath(resourceRelativeFilePath));
            System.out.printf("File contents for resourceRelativeFilePath [%s]: %s%n", resourceRelativeFilePath, lines);
            return new ReadResourceFileLinesResult(resourceRelativeFilePath, true, null, lines);

        } catch (Exception e) {
            return new ReadResourceFileLinesResult(resourceRelativeFilePath, false, e.getMessage(), null);
        }
    }

}
