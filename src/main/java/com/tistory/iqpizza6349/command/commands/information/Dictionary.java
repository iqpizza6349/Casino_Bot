package com.tistory.iqpizza6349.command.commands.information;

import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import com.tistory.iqpizza6349.jsoup.module.CrawlerModule;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Iterator;
import java.util.List;

public class Dictionary implements ICommand {

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
        for (String keyWord : messages) {
            builder.append(keyWord).append(" ");
        }
        String keyWord = builder.toString();

        Iterator<String> results = crawlerModule.getCrawler("dictionary")
                .handle(crawlerModule, "https://dict.naver.com/search.dict?dicQuery=" + keyWord, null, "dictionary");

        if (results == null) {
            channel.sendMessage("no information, " + "https://dict.naver.com/search.dict?dicQuery=" + keyWord).queue();
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        while (results.hasNext()) {
            stringBuilder.append(results.next());
        }

        try {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(keyWord, "https://dict.naver.com/search.dict?dicQuery=" + keyWord);
            embedBuilder.addField("", stringBuilder.toString(), false);
            embedBuilder.setThumbnail("https://play-lh.googleusercontent.com/ytdLHA3Td0wOlW-e1exeUNVhrqyWXpbsb0MJVt8IjncgyTSwITJ6tsWUGsd2ulGLS1g");

            channel.sendMessageEmbeds(embedBuilder.build()).queue();
        } catch (IllegalArgumentException e) {
            channel.sendMessage("too much information " + "https://dict.naver.com/search.dict?dicQuery=" + keyWord).queue();
        }



    }

    @Override
    public String getName() {
        return "dict";
    }

    @Override
    public String getHelp() {
        return "Dict";
    }
}
