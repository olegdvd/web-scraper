package org.kaidzen.webscrap.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class WriteBulkToFile {

    private static final Logger LOG = LoggerFactory.getLogger(WriteBulkToFile.class);

    public void writeToFile(String fileName, List<String> stringsList){
        try {
            ClassPathResource resource = new ClassPathResource(fileName);
            Path filePath = Paths.get(resource.getPath());
            Path path = Paths.get(new ClassPathResource(fileName).getURI());
            System.out.println(path);
            Path res = Files.write(filePath, stringsList, Charset.forName("UTF-8"), StandardOpenOption.APPEND, StandardOpenOption.WRITE);
            LOG.info("Added to file: {}, {} lines", res, stringsList.size());
        } catch (IOException e) {
            LOG.warn("Can not get path to file: {}", fileName, e);
        }
    }

}
