package org.kaidzen.webscrap.document.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.kaidzen.webscrap.document.model.FormFilterData;
import org.kaidzen.webscrap.document.model.PermitDocument;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class ElementsPermitDocument extends ElementScraper<PermitDocument> {

    private final Function<Element, Optional<String>> elementsToStringMapper;
    private final BiFunction<Element, FormFilterData, Optional<PermitDocument>> elementsToDocumentMapper;


    public ElementsPermitDocument(Function<Element, Optional<String>> elementsToStringMapper,
                                  BiFunction<Element, FormFilterData, Optional<PermitDocument>> elementsToDocumentMapper) {
        this.elementsToStringMapper = elementsToStringMapper;
        this.elementsToDocumentMapper = elementsToDocumentMapper;
    }

    @Override
    public int pagesToScrap(String baseUrl) {
        Document document = getPagetoDocument(baseUrl, 1, null);
        List elements = document.select("tr");
        int lastElement = elements.size() - 1;
        Element lastRow = (Element) elements.get(lastElement);
        Elements res = lastRow.select("#pages a");
        String pages = res.last().text();
        System.out.println(lastRow);
        System.out.println("------------------>" + pages);
        return 1;
    }

    @Override
    public List<PermitDocument> takeElements(String selection, Document document, FormFilterData filterData) {
        return document.select(selection).stream()
                .map(element -> elementsToDocumentMapper.apply(element, filterData))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());
    }

    public List<String> takeFilteredElements(String selection, Document document) {
        return document.select(selection).stream()
                .map(elementsToStringMapper)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());
    }


}
