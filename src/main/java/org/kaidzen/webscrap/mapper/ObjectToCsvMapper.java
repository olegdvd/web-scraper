package org.kaidzen.webscrap.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ObjectToCsvMapper<T> implements Function<Collection<T>, List<String>> {

    private static final Logger LOG = LoggerFactory.getLogger(ObjectToCsvMapper.class);
    private final CsvMapper mapper;
    private CsvSchema schema;

    public ObjectToCsvMapper() {
        mapper = new CsvMapper();
    }

    @Override
    public List<String> apply(Collection<T> objectsList) {
        if (objectsList.isEmpty()) return Collections.emptyList();
        schema = mapper.schemaFor(getPojoType(objectsList));
        return objectsList.stream()
                .map(this::convertToString)
                .collect(Collectors.toList());
    }

    private String convertToString(T object) {
        try {
            char delimiter = ',';
            char quote = '"';
            String lineSeparator = "\n";
            return mapper.writer(schema
                    .withColumnSeparator(delimiter)
                    .withQuoteChar(quote)
                    .withLineSeparator(lineSeparator))
                    .writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOG.warn("Failed to convert {} to CSV string: {}", object, e.getMessage());
            return "empty";
        }
    }

    private Class<? extends T> getPojoType(Collection<T> objectsList) {
        return objectsList.stream()
                .limit(1)
                .flatMap(t -> t.getClass());
    }
}
