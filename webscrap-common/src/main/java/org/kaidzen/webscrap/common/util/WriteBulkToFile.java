package org.kaidzen.webscrap.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WriteBulkToFile {

    private static final Logger LOG = LoggerFactory.getLogger(WriteBulkToFile.class);

    public Path writeToFile(String fileName, List<String> stringsList) {
        return writeToFile(fileName, stringsList, "");
    }

    public Path writeToFile(String fileName, List<String> stringsList, String presentPageFilter) {
        Path res;
        try {
            Path filePath = Paths.get(fileName);
            File file = new File(filePath.toString());
            //TODO if exist - create new file, to save old one
            if (!file.exists()) {
                Path resultPath = Files.createFile(filePath);
                LOG.info("New file created: {}", resultPath);
            }
            List<String> result = getMarkedStrings(stringsList, presentPageFilter);
            res = Files.write(filePath, result, Charset.forName("UTF-8"), StandardOpenOption.APPEND, StandardOpenOption.WRITE);

            LOG.info("Added to file: {}, {} lines", res, stringsList.size());
            return res;
        } catch (IOException e) {
            LOG.warn("Can not get path to file: {}", fileName, e);
            return Paths.get("");
        }
    }

    private List<String> getMarkedStrings(List<String> stringsList, String presentPageFilter) {
        List<String> stringsToFile = new ArrayList<>();
        List<String> list = stringsList.stream()
                .map(string -> string.concat("| " + "Filter" + presentPageFilter))
                .collect(Collectors.toList());
        stringsToFile.add("Filter" + presentPageFilter);
        stringsToFile.addAll(list);
        return stringsToFile;
    }

}
