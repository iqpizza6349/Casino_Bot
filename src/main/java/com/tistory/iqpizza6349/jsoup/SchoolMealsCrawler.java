package com.tistory.iqpizza6349.jsoup;

import com.tistory.iqpizza6349.jsoup.module.CrawlerModule;
import com.tistory.iqpizza6349.jsoup.module.Crawlers;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.*;

public class SchoolMealsCrawler implements Crawlers {

    @Override
    public Iterator<String> handle(CrawlerModule module, String url, Document document, String topic) {
        module.setUrl(url);
        module.setDocument(document);
        module.setTopic(topic);

        return module.run();
    }

    @Override
    public Iterator<String> crawling(Document document, String topic) {
        Calendar calendar = Calendar.getInstance(Locale.KOREA);
        int dayOfWeekInMonth = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
        int dayOfWeekValue = calendar.get(Calendar.DAY_OF_WEEK);

        Element rows = document.select("tr").get(dayOfWeekInMonth);
        Element col = rows.select("td").get(dayOfWeekValue-1);
        String editCol1 = col.text().replace("[조식]", "!");
        String editCol2 = editCol1.replace("[중식]", "@");
        String editCol3 = editCol2.replace("[석식]", "#");
        String editCol4 = editCol3.replaceAll("[*.1234567890]", "");

        String[] resultArray = editCol4.split(" ");

        return Arrays.stream(resultArray).iterator();
    }

}
