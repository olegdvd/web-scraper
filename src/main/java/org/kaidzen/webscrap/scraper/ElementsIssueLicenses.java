package org.kaidzen.webscrap.scraper;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.kaidzen.webscrap.model.IssuedLicense;

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
        List elements = documentForPage(baseUrl, 1);
        int lastElement = elements.size() - 1;
        Elements lastRow = (Elements) elements.get(lastElement);
        Elements res = lastRow.select("#pages a");
        String pages = res.last().text();
        System.out.println(lastRow);
        System.out.println("------------------>" +pages);
        return 1;
    }

    @Override
    public List<IssuedLicense> scrapElements(String pagedUrl, int pageIndex) {
        return documentForPage(pagedUrl, pageIndex).stream()
                .map(elementsMapper)
                .collect(toList());
    }
}
