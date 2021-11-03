package com.tistory.iqpizza6349.jsoup.module;

import com.tistory.iqpizza6349.jsoup.*;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class CrawlerModule {

    private String url;
    private Document document;
    private String topic;

    private final HashMap<String, Crawlers> crawlersHashMap = new HashMap<>();

    public CrawlerModule() {
        addCrawler("movie", new MovieCrawler());
        addCrawler("schoolmeals", new SchoolMealsCrawler());
        addCrawler("weather", new WeatherCrawler());
        addCrawler("exchangerate", new ExchangeRateCrawler());
        addCrawler("namuwiki", new NamuWikiCrawler());
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    private void addCrawler(String name, Crawlers crawler) {
        boolean nameFound = this.crawlersHashMap.containsKey(name);

        if (nameFound) {
            throw new IllegalArgumentException("A Crawler with this name is already present");
        }

        crawlersHashMap.put(name, crawler);
    }

    public HashMap<String, Crawlers> getCrawlersHashMap() {
        return crawlersHashMap;
    }

    public Crawlers getCrawler(String search) {
        String searchLower = search.toLowerCase();

        for (String name : this.crawlersHashMap.keySet()) {
            if (name.equals(searchLower)) {
                return crawlersHashMap.get(name);
            }
        }

        return null;
    }

    public Iterator<String> run() {
        try {
            this.document = Jsoup.connect(this.url)
                    .userAgent("Mozilla/5.0 MenuDocs BOT_TEST#8047 / IQPIZZA6349#8983")
                    .get();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

        return crawling(this.document, this.topic);
    }

    public Iterator<String> crawling(Document document, String topic) {

        if (getCrawler(topic) != null) {
            return getCrawler(topic).crawling(document, topic);
        }

        return null;
    }

}
