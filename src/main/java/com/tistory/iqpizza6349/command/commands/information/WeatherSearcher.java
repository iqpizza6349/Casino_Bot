package com.tistory.iqpizza6349.command.commands.information;

import com.tistory.iqpizza6349.Config;
import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import com.tistory.iqpizza6349.jsoup.module.CrawlerModule;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WeatherSearcher implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        CrawlerModule crawlerModule = new CrawlerModule();

        List<String> messages = ctx.getStrings();
        TextChannel channel = ctx.getChannel();

        if (messages.isEmpty()) {
            channel.sendMessage("인자값이 없습니다.").queue();
            return;
        }

        if (messages.size() > 2) {
            channel.sendMessage("인자값이 너무 많습니다.").queue();
            return;
        }

        String location = messages.get(0);

        Iterator<String> results = crawlerModule.getCrawler("weather")
                .handle(crawlerModule, "https://search.naver.com/search.naver?sm=top_hty&fbm=1&ie=utf8&query=" + location + "%20날씨", null, "weather");

        ArrayList<String> result = new ArrayList<>();
        EmbedBuilder builder = new EmbedBuilder();

        while (results.hasNext()) {
            result.add(results.next());
        }
        if (result.isEmpty() || result.size() <= 7) {
            channel.sendMessage("해당 위치의 날씨를 불러올 수 없습니다.").queue();
            return;
        }

        builder.setTitle("[" + result.get(0) + "] 의 현재 날씨");
        builder.addField("[현재 온도]", result.get(1), false);
        builder.addField("[현재 날씨]", result.get(2), false);
        builder.addField("[현재 강수 확률]", result.get(3), false);
        builder.addField("[현재 습도]", result.get(4), false);
        builder.addField("[현재 미세먼지]", result.get(5), false);
        builder.addField("[현재 초미세먼지]", result.get(6), false);
        builder.addField("[현재 자외선]", result.get(7), false);

        builder.setColor(Color.ORANGE);
        channel.sendMessageEmbeds(builder.build()).queue();
    }

    @Override
    public String getName() {
        return "날씨";
    }

    @Override
    public String getHelp() {
        return "해당 위치의 날씨 정보를 검색합니다. (한국만 가능하고, 특정 정보가 부족하여 불러올 수 없을 수 도 있습니다.)\n" +
                "Usage: " + Config.PREFIX + "weather [location]";
    }
}
