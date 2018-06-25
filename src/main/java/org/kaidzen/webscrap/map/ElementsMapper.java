package org.kaidzen.webscrap.map;

import org.jsoup.nodes.Element;

public interface ElementsMapper<T> {

    T mapFromElements(Element element);
}
