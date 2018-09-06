package org.kaidzen.webscrap.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.kaidzen.webscrap.model.FormFilterData;
import org.kaidzen.webscrap.model.PermitDocument;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import static java.util.stream.Collectors.toList;

public class ElementsPermitDocument extends ElementScraper<PermitDocument> {

    private final BiFunction<Element, FormFilterData, Optional<PermitDocument>> elementsMapper;


    public ElementsPermitDocument(BiFunction<Element, FormFilterData, Optional<PermitDocument>> elementsMapper) {
        this.elementsMapper = elementsMapper;
    }

    @Override
    public int pagesToScrap(String baseUrl) {
        Document document = getPagetoDocument(baseUrl, 1);
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

    public List<PermitDocument> takeFilteredElements(String selection, Document document, FormFilterData filterData){
        return document.select(selection).stream()
                .map(element -> elementsMapper.apply(element, filterData))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());
    }


}
