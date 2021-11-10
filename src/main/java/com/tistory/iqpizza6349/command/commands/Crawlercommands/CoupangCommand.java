package com.tistory.iqpizza6349.command.commands.Crawlercommands;

import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import com.tistory.iqpizza6349.command.commands.jsoup.module.CrawlerModule;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CoupangCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        CrawlerModule crawlerModule = new CrawlerModule();

        List<String> messages = ctx.getStrings();
        TextChannel channel = ctx.getChannel();

        if (messages.isEmpty()) {
            channel.sendMessage("no argument value").queue();
            return;
        }

        StringBuilder builder = new StringBuilder();
        StringBuilder urlBuilder = new StringBuilder();
        for (String keyWord : messages) {
            builder.append(keyWord).append(" ");
            urlBuilder.append(keyWord).append("%20");
        }
        String keyWord = builder.toString();
        String urlKeyWord = urlBuilder.toString();

        Iterator<String> results = crawlerModule.getCrawler("coupang")
                .handle(crawlerModule, "https://www.coupang.com/np/search?component=&q=" + urlKeyWord, null, "coupang");


        if (results == null) {
            channel.sendMessage("no information, " + "https://www.coupang.com/np/search?component=&q=" + urlKeyWord).queue();
            return;
        }

        ArrayList<String> s = new ArrayList<>();
        while (results.hasNext()) {
            s.add(results.next());
        }
        String st = s.get(2);


        try {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(keyWord, "https://www.coupang.com/np/search?component=&q=" + keyWord);
            embedBuilder.addField("", s.get(0), false);
            embedBuilder.addField("", s.get(1) + "원", false);
            embedBuilder.setThumbnail(st);
            channel.sendMessageEmbeds(embedBuilder.build()).queue();
        } catch (IllegalArgumentException e) {
            channel.sendMessage("too much information " + "https://www.coupang.com/np/search?component=&q=" + urlKeyWord).queue();
        }
    }

    @Override
    public String getName() {
        return "cou";
    }

    @Override
    public String getHelp() {
        return "serch at coupang site if you want Items price";
    }
}
