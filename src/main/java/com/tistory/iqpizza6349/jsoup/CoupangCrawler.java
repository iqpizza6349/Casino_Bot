package com.tistory.iqpizza6349.jsoup;
import com.tistory.iqpizza6349.jsoup.module.CrawlerModule;
import com.tistory.iqpizza6349.jsoup.module.Crawlers;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;

public class CoupangCrawler implements Crawlers {
    @Override
    public Iterator<String> handle(CrawlerModule module, String url, Document document, String topic) {
        module.setTopic(topic);
        module.setDocument(document);
        module.setUrl(url);
        return module.run();
    }

    @Override
    public Iterator<String> crawling(Document document, String topic) {
        ArrayList<String> s = new ArrayList<>();
        try{
            Elements elements = document.select("div.descriptions-inner");

            Element element = elements.select("div.name").get(0);
            Element element2 = elements.select("strong.price-value").get(0);

            String text1 = element.text();
            String text2 = element2.text();

            Elements e = document.select("dl.search-product-wrap");
            Elements es = e.select("dt.image");
            Element a = es.select("img.search-product-wrap-img").first();
            assert a != null;
            String image = a.absUrl("src");

            s.add(text1);
            s.add(text2);
            s.add(image);
        }catch (Exception e){
            e.printStackTrace();
        }
        return s.iterator();
    }
}