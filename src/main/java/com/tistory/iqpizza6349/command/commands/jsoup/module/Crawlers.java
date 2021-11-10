package com.tistory.iqpizza6349.command.commands.jsoup.module;

import org.jsoup.nodes.Document;

import java.util.Iterator;

public interface Crawlers {

    Iterator<String> handle(CrawlerModule module,
                  String url, Document document, String topic);

    Iterator<String> crawling(Document document, String topic);

}
