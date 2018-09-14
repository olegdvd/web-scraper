package org.kaidzen.webscrap.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.kaidzen.webscrap.model.PermitDocument;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class ElementsPermitDocument extends ElementScraper<PermitDocument> {

    private final Function<Element, Optional<String>> elementsMapper;


    public ElementsPermitDocument(Function<Element, Optional<String>> elementsMapper) {
        this.elementsMapper = elementsMapper;
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
    public List<PermitDocument> takeElements(String selection, Document document) {
        return null;
    }

    public List<String> takeFilteredElements(String selection, Document document) {
        return document.select(selection).stream()
                .map(elementsMapper)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());
    }


}
