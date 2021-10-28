package com.tistory.iqpizza6349.command.commands.information;

import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import com.tistory.iqpizza6349.jsoup.module.CrawlerModule;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Iterator;
import java.util.List;

public class NamuWiki implements ICommand {

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

        Iterator<String> results = crawlerModule.getCrawler("namuwiki")
                .handle(crawlerModule, "https://namu.wiki/w/" + urlKeyWord, null, "namuwiki");

        if (results == null) {
            channel.sendMessage("no information, " + "https://namu.wiki/w/" + urlKeyWord).queue();
            return;
        }

        StringBuilder sb = new StringBuilder();        while (results.hasNext()) {
            sb.append(results.next());
        }
        try {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(keyWord, "https://namu.wiki/w/" + urlKeyWord);
            embedBuilder.addField("개요", sb.toString(), false);
            embedBuilder.setThumbnail("https://w.namu.la/s/76f3cd317712c830ca32c3574db36c64e1e5ecaa7cc034113f98bec89e4a25149a8528b25fd556354c6e594c750889b3971e729596247278234391b5a6c69f4820659c9490c4d6d2e9ca9ab2815bf3ffd8c403de79405d5be2fcd9d849d9e77e");

            channel.sendMessageEmbeds(embedBuilder.build()).queue();
        } catch (IllegalArgumentException e) {
            channel.sendMessage("too much information " + "https://namu.wiki/w/" + urlKeyWord).queue();
        }
    }

    @Override
    public String getName() {
        return "namuwiki";
    }

    @Override
    public String getHelp() {
        return "Shows articles with keywords in NamuWiki\n" +
                "Usage: namuwiki (keyword)";
    }
}
