package org.kaidzen.webscrap.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class WriteBulkToFile {

    private static final Logger LOG = LoggerFactory.getLogger(WriteBulkToFile.class);

    public Path writeToFile(String fileName, List<String> stringsList) {

        Path res;
        try {
            Path filePath = Paths.get(fileName);
            File file = new File(filePath.toString());
            //TODO if exist - create new file, to save old one
            if (!file.exists()) {
            Path resultPath = Files.createFile(filePath);
                LOG.info("New file created: {}", resultPath);}
            res = Files.write(filePath, stringsList, Charset.forName("UTF-8"), StandardOpenOption.APPEND, StandardOpenOption.WRITE);

            LOG.info("Added to file: {}, {} lines", res, stringsList.size());
            return res;
        } catch (IOException e) {
            LOG.warn("Can not get path to file: {}", fileName, e);
            return Paths.get("");
        }
    }

}
