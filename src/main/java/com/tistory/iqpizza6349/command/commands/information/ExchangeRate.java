package com.tistory.iqpizza6349.command.commands.information;

import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import com.tistory.iqpizza6349.jsoup.module.CrawlerModule;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExchangeRate implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        CrawlerModule crawlerModule = new CrawlerModule();

        List<String> messages = ctx.getStrings();
        TextChannel channel = ctx.getChannel();

        if (messages.isEmpty()) {
            channel.sendMessage("no argument value").queue();
            return;
        }

        if (messages.size() < 2) {
            channel.sendMessage("Insufficient argument values.").queue();
            return;
        }

        if (messages.size() > 3) {
            channel.sendMessage("too many arguments!").queue();
            return;
        }

        String currentMoneyType = messages.get(1);

        Iterator<String> results = crawlerModule.getCrawler("exchangerate")
                .handle(crawlerModule, "https://www.google.com/search?q=" + currentMoneyType + "+to+won", null, "exchangerate");

        ArrayList<String> strings = new ArrayList<>();
        if (results.hasNext()) {
            strings.add(results.next());
        }

        if (strings.isEmpty()) {
            channel.sendMessage("Can't found kind of " + currentMoneyType).queue();
            return;
        }

        double amount;

        try {
            amount = Double.parseDouble(ctx.getStrings().get(0));
        } catch (NumberFormatException e) {
            channel.sendMessage("arguments have no digits!").queue();
            return;
        }

        if (amount < 1) {
            channel.sendMessage("zero or minus is not available to exchange").queue();
            return;
        }

        if (amount > 10000000) {
            channel.sendMessage("It's too big amount to exchange calculate").queue();
            return;
        }

        double exchangeRateResult = Double.parseDouble(strings.get(0).replaceAll("[,]", ""));

        channel.sendMessage(amount + currentMoneyType + " is available to exchange to " + amount * exchangeRateResult + "won").queue();
    }

    @Override
    public String getName() {
        return "exchangerate";
    }

    @Override
    public String getHelp() {
        return "Show the exchange rate of the currency is converted into 'won'.\n" +
                "Usage: (amount) (foreign currency name)\n" +
                "Example) 1 dollar";
    }
}
