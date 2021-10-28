package com.tistory.iqpizza6349.command.commands.game;

import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class FlipCoin implements ICommand {

    public static final String head = "1️⃣";
    public static final String tail = "2️⃣";
    public static HashMap<Long, HashMap<Long, Boolean>> bettingUserMap = new HashMap<>();

    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();

        if (bettingUserMap.containsKey(channel.getGuild().getIdLong())) {
            channel.sendMessage("Already playing game!").queue();
            return;
        }

        channel.sendMessage("Please choose which side you want to bet on for 20 seconds before starting to roll the coin.\n")
                .append("Bet to Head: ")
                .append(head)
                .append("\n")
                .append("Bet to Tail: ")
                .append(tail)
                .queue((msg) -> {
                    msg.addReaction(head).queue();
                    msg.addReaction(tail).queue();
                    msg.delete().queueAfter(20L, TimeUnit.SECONDS);
                    String coinResult = Math.random() > 0.5 ? "head" : "tail";
                    channel.sendMessageFormat("%s", coinResult).queueAfter(20L, TimeUnit.SECONDS);
                    boolean coin = coinResult.equals("head");

                    try {
                        TimeUnit.SECONDS.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    for (long user : bettingUserMap.get(channel.getGuild().getIdLong()).keySet()) {
                        if (bettingUserMap.get(channel.getGuild().getIdLong()).get(user) == coin) {
                            channel.sendMessageFormat("<@%s>, you earn 500 Cash", user).queue();
                        }
                        else {
                            channel.sendMessageFormat("<@%s>, you lose 500 Cash", user).queue();
                        }
                    }
                    bettingUserMap.put(channel.getGuild().getIdLong(), new HashMap<>());

                });
        bettingUserMap.put(channel.getGuild().getIdLong(), new HashMap<>());

    }

    @Override
    public String getName() {
        return "flipcoin";
    }

    @Override
    public String getHelp() {
        return "Start a coin flip game.\n" +
                "You can place bets by tapping the emotion.\n" +
                "(The bet is fixed at 500 cash.)";
    }
}
