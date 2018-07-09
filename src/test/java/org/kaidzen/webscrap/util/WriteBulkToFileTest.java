package org.kaidzen.webscrap.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class WriteBulkToFileTest {

    private static final String FILE_NAME = "test.txt";
    private static final List<String> list = Arrays.asList("Zetta", "Tetta", "Gamma");
    private WriteBulkToFile bulkToFile;

    @Test
    public void writeToFile() {
        bulkToFile = new WriteBulkToFile();
        bulkToFile.writeToFile(FILE_NAME, list);
    }
}