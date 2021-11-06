package com.tistory.iqpizza6349.jsoup;

import com.tistory.iqpizza6349.jsoup.module.CrawlerModule;
import com.tistory.iqpizza6349.jsoup.module.Crawlers;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Iterator;

public class DictionaryCrawler implements Crawlers {

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
            Element element = document.select("ul.lst_krdic").get(0);

            Element e = element.select("li").get(0);
            String text = e.text();
            if (text.contains("play")) {
                text = text.replace("play", "");
            }
            if (text.contains("중요")) {
                text = text.replace("중요", "");
            }
            strings.add(text);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return strings.iterator();
    }
}
