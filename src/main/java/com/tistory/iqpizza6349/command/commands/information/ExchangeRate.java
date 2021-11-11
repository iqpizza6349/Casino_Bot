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
            channel.sendMessage("인자값이 없습니다.").queue();
            return;
        }

        if (messages.size() < 2) {
            channel.sendMessage("인자값이 부족합니다.").queue();
            return;
        }

        if (messages.size() > 3) {
            channel.sendMessage("인자값이 너무 많습니다.").queue();
            return;
        }

        String currentMoneyType = messages.get(1);

        Iterator<String> results = crawlerModule.getCrawler("exchangerate")
                .handle(crawlerModule, "https://www.google.com/search?q=" + currentMoneyType + "%20to%20won", null, "exchangerate");

        ArrayList<String> strings = new ArrayList<>();
        if (results.hasNext()) {
            strings.add(results.next());
        }

        if (strings.isEmpty()) {
            channel.sendMessage(currentMoneyType + "를 찾지 못하였습니다.").queue();
            return;
        }

        double amount;

        try {
            amount = Double.parseDouble(ctx.getStrings().get(0));
        } catch (NumberFormatException e) {
            channel.sendMessage("인자값에 숫자가 포함되어있지 않습니다.").queue();
            return;
        }

        if (amount < 1) {
            channel.sendMessage("0이하의 숫자는 환율에 적용되지 않는 수입니다.").queue();
            return;
        }

        if (amount > 10000000) {
            channel.sendMessage("환율 계산에 너무나도 큰 수 입니다.").queue();
            return;
        }

        double exchangeRateResult = Double.parseDouble(strings.get(0).replaceAll("[,]", ""));

        channel.sendMessage(amount + currentMoneyType + "는 " + amount * exchangeRateResult + "원으로 환산 가능합니다.").queue();
    }

    @Override
    public String getName() {
        return "환율";
    }

    @Override
    public String getHelp() {
        return "외화를 대한민국 원으로 환산할 수 있습니다.\n" +
                "Usage: (amount) (foreign currency name)\n" +
                "Example) 1 dollar";
    }
}
