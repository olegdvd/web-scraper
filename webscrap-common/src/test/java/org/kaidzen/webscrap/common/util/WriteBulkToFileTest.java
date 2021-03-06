package org.kaidzen.webscrap.common.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WriteBulkToFileTest {

    private static final String FILE_NAME = "test.txt";
    private static final List<String> LIST = Arrays.asList("Filter", "Zetta| Filter", "Tetta| Filter", "Gamma| Filter");
    private WriteBulkToFile bulkToFile;
    private Path result;

    @Before
    public void setUp() {
        bulkToFile = new WriteBulkToFile();
    }

    @Test
    public void writeToFile() throws IOException {
        result = bulkToFile.writeToFile(FILE_NAME, LIST);

        List<String> content = Files.<List<String>>readAllLines(result);
        assertEquals(LIST.get(0), content.get(0));
    }

    @After
    public void finish() throws IOException {
        File file = new File(result.toString());
        if (file.exists()) Files.delete(result);
    }
}