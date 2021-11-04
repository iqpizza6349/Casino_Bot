package com.tistory.iqpizza6349.command.commands.Crawlercommands;

import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import com.tistory.iqpizza6349.command.commands.jsoup.module.CrawlerModule;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Iterator;
import java.util.List;

public class DictionaryCommand implements ICommand {
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

        Iterator<String> results = crawlerModule.getCrawler("dictionary")
                .handle(crawlerModule, "https://dict.naver.com/search.dict?dicQuery=" + urlKeyWord, null, "dictionary");


        if (results == null) {
            channel.sendMessage("no information, " + "https://dict.naver.com/search.dict?dicQuery=" + urlKeyWord).queue();
            return;
        }

        StringBuilder sb = new StringBuilder();        while (results.hasNext()) {
            sb.append(results.next());
        }
        try {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(keyWord, "https://dict.naver.com/search.dict?dicQuery=" + keyWord);
            embedBuilder.addField("", sb.toString(), false);

            channel.sendMessageEmbeds(embedBuilder.build()).queue();
        } catch (IllegalArgumentException e) {
            channel.sendMessage("too much information " + "https://dict.naver.com/search.dict?dicQuery=" + urlKeyWord).queue();
        }
    }

    @Override
    public String getName() {
        return "dic";
    }

    @Override
    public String getHelp() {
        return "Can use naver Dictionary";
    }
}
