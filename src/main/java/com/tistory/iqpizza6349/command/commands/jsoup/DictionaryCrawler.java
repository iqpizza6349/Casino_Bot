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
        try{
            Element element = document.select("ul.lst_krdic").get(0);
            Element e = element.select("li").get(0);
            String a = e.text();
            if(a.contains("중요")){
                a = a.replace("중요","");
            }
            if(a.contains("play")){
                a = a.replace("play","");
            }
            strings.add(a);
        } catch (Exception e){
            System.out.println(e);
            System.out.println("Error");
            return null;
        }
        return strings.iterator();
    }
}
