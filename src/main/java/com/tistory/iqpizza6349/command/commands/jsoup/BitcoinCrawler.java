package com.tistory.iqpizza6349.command.commands.jsoup;

import com.tistory.iqpizza6349.command.commands.jsoup.module.CrawlerModule;
import com.tistory.iqpizza6349.command.commands.jsoup.module.Crawlers;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class BitcoinCrawler implements Crawlers {

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
        try{

            Elements element = document.select("div._ngcontent-yre-c28");
            System.out.println("1");
            Elements elements = element.select("span._ngcontent-yre-c28");
            System.out.println("2");
            String[] text = elements.text().split(",");
            System.out.println(text);
            List<String> s = Arrays.asList(text);
            strings.addAll(s);
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
        return strings.iterator();
    }
}
