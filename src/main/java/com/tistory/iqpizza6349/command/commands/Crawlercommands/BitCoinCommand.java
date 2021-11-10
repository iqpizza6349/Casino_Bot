package com.tistory.iqpizza6349.command.commands.Crawlercommands;

import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import com.tistory.iqpizza6349.command.commands.jsoup.module.CrawlerModule;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Iterator;
import java.util.List;

public class BitCoinCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        CrawlerModule crawlerModule = new CrawlerModule();

        TextChannel channel = ctx.getChannel();

        StringBuilder builder = new StringBuilder();
        StringBuilder urlBuilder = new StringBuilder();


        String keyWord = "비트코인";
        String urlKeyWord = "Bitcoin";

        Iterator<String> results = crawlerModule.getCrawler("bitcoin")
                .handle(crawlerModule, "https://coinone.co.kr/exchange/trade/btc/krw?__cf_chl_jschl_tk__=pmd_A105.Vu3p2gSZz.E_ViRf7.ss_qoVVSAk7QOJvqkyZ4-1635910318-0-gqNtZGzNAhCjcnBszQi9"
                        , null, "bitcoin");


        if (results == null) {
            channel.sendMessage("UnKnownError").queue();
            return;
        }

        StringBuilder sb = new StringBuilder();

        while (results.hasNext()) {
            sb.append(results.next());
        }
        try {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(keyWord);


            channel.sendMessageEmbeds(embedBuilder.build()).queue();
        } catch (IllegalArgumentException e) {
            channel.sendMessage("too much information " + "https://www.google.com/search?q=" + urlKeyWord).queue();
        }
    }

    @Override
    public String getName() {
        return "bitcoin";
    }

    @Override
    public String getHelp() {
        return "Can see now 1 BitCoin price";
    }
}
