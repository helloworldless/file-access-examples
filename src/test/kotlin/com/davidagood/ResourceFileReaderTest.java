package com.davidagood;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

public class ResourceFileReaderTest {

    @Test
    public void readResourcesFileLines() {
        ResourceFileReader resourceFileReader = new ResourceFileReader();
        List<String> resourceRelativeFilePaths = Arrays.asList("testResource.txt", "/does_not_exist.txt");
        List<ReadResourceFileLinesResult> readResourceFileLinesResults = resourceRelativeFilePaths
                .stream()
                .map(resourceFileReader::readLines)
                .collect(toList());
        assertEquals(resourceRelativeFilePaths.size(), readResourceFileLinesResults.size());
        readResourceFileLinesResults.forEach(this::println);
    }

    private void println(Object obj) {
        System.out.println(obj);
    }

}