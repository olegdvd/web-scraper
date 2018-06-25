package org.kaidzen.webscrap.scraper;

import org.kaidzen.webscrap.model.IssuedLicense;

import javax.xml.bind.Element;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class ElementsIssueLicenses extends ElementScraper<IssuedLicense> {

    private final Function<Element, IssuedLicense> elementsMapper;

    public ElementsIssueLicenses(Function<Element, IssuedLicense> elementsMapper) {
        this.elementsMapper = elementsMapper;
    }

    @Override
    public int pagesToScrap(String baseUrl) {
        List elements = requestForElements(baseUrl, 1);
        return 1;
    }

    @Override
    public List<IssuedLicense> scrapElements(String pagedUrl, int pageIndex) {
        return requestForElements(pagedUrl, pageIndex).stream()
                .map(elementsMapper::apply)
                .collect(toList());;
    }
}