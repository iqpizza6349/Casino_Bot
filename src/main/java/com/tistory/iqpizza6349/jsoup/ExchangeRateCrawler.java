package com.tistory.iqpizza6349.jsoup;

import com.tistory.iqpizza6349.jsoup.module.CrawlerModule;
import com.tistory.iqpizza6349.jsoup.module.Crawlers;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Iterator;

public class ExchangeRateCrawler implements Crawlers {

    @Override
    public Iterator<String> handle(CrawlerModule module, String url, Document document, String topic) {
        module.setUrl(url);
        module.setDocument(document);
        module.setTopic(topic);

        return module.run();
    }

    @Override
    public Iterator<String> crawling(Document document, String topic) {
        ArrayList<String> strings = new ArrayList<>();
        try {
            Element text = document.select("span.DFlfde.SwHCTb").get(0);
            strings.add(text.text());
        } catch (Exception e) {
            System.out.println("no found that current money");
        }
        return strings.iterator();
    }
}
