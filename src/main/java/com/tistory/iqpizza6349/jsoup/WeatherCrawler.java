package com.tistory.iqpizza6349.jsoup;

import com.tistory.iqpizza6349.jsoup.module.CrawlerModule;
import com.tistory.iqpizza6349.jsoup.module.Crawlers;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;

public class WeatherCrawler implements Crawlers {

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
            Elements location = document.select("div.title_area._area_panel");
            String loc = location.select("h2.title").text();
            strings.add(loc);

            Elements text = document.select("div.temperature_text");
            Element temperature = text.select("strong").get(0);
            String currentTemperature = temperature.text().replace("현재 온도", "");
            strings.add(currentTemperature);

            Elements states = document.select("div.weather_main");
            Element state = states.select("span.blind").get(0);
            String currentState = state.text();
            strings.add(currentState);

            Elements infos = document.select("div.temperature_info");
            Elements info = infos.select("dd.desc");
            String precipitation = info.get(0).text();
            String humid = info.get(1).text();
            strings.add(precipitation);
            strings.add(humid);

            Elements dustInfos = document.select("div.report_card_wrap");
            Elements dust = dustInfos.select("li.item_today.level1");
            String currentDust = dust.select("span.txt").get(0).text();
            strings.add(currentDust);
            String currentFineDust = dust.select("span.txt").get(1).text();
            strings.add(currentFineDust);

            Elements UV_rays = dustInfos.select("li.item_today.level2");
            String currentUV_rays = UV_rays.select("span.txt").get(0).text();
            strings.add(currentUV_rays);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("없는 위치입니다!");
        }

        return strings.iterator();
    }
}
