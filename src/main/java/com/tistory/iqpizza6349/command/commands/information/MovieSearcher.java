package com.tistory.iqpizza6349.command.commands.information;

import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import com.tistory.iqpizza6349.jsoup.module.CrawlerModule;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.jsoup.nodes.Element;

import java.awt.*;
import java.util.Iterator;

public class MovieSearcher implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        CrawlerModule crawlerModule = new CrawlerModule();

        Iterator<String> results = crawlerModule.getCrawler("movie")
                .handle(crawlerModule, "http://www.cgv.co.kr/movies/", null, "movie");

        TextChannel channel = ctx.getChannel();
        int i = 1;

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("CGV 탑 7 영화 목록");

        while (results.hasNext()) {
            builder.addField("No#" + String.valueOf(i++), results.next(), false);
        }
        builder.setColor(Color.ORANGE);
        channel.sendMessageEmbeds(builder.build()).queue();
    }

    @Override
    public String getName() {
        return "영화";
    }

    @Override
    public String getHelp() {
        return "현재 상영 중인 CGV 영화 7편을 검색합니다.";
    }
}
