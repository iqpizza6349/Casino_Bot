package com.tistory.iqpizza6349.command.commands.jsoup;

import com.tistory.iqpizza6349.command.commands.jsoup.module.CrawlerModule;
import com.tistory.iqpizza6349.command.commands.jsoup.module.Crawlers;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;

public class MovieCrawler implements Crawlers {

    @Override
    public Iterator<String> handle(CrawlerModule module, String url, Document document, String topic) {
        module.setUrl(url);
        module.setDocument(document);
        module.setTopic(topic);

        return module.run();
    }

    @Override
    public Iterator<String> crawling(Document document, String topic) {
        Elements element = document.select("div.sect-movie-chart");

        ArrayList<String> strings = new ArrayList<>();
        for (Element value : element.select("strong.title")) {
            strings.add(value.text());
        }

        return strings.iterator();
    }
}
