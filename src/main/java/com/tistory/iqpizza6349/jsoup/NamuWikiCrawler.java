package com.tistory.iqpizza6349.jsoup;

import com.tistory.iqpizza6349.jsoup.module.CrawlerModule;
import com.tistory.iqpizza6349.jsoup.module.Crawlers;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class NamuWikiCrawler implements Crawlers {

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
            Element element = document.select("div.wiki-heading-content").get(0);
            Elements articles = element.select("div.wiki-paragraph");
            String[] a = articles.text().split("\\.");
            List<String> list = Arrays.asList(a);
            strings.addAll(list);

        } catch (Exception e) {
            System.out.println("no found article with using keywords");
            return null;
        }

        return strings.iterator();
    }
}
