package com.tistory.iqpizza6349.command.commands.game;

import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class OddAndEven implements ICommand {

    public static final String odd = "1️⃣";
    public static final String even = "2️⃣";
    public static HashMap<Long, HashMap<Long, Boolean>> bettingUserMap = new HashMap<>();

    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();

        if (FlipCoin.bettingUserMap.containsKey(channel.getGuild().getIdLong())) {
            channel.sendMessage("Already playing game!").queue();
            return;
        }

        channel.sendMessage("Please choose which side you want to bet on for 20 seconds before starting to show the number.\n")
                .append("Bet to Odd: ")
                .append(odd)
                .append("\n")
                .append("Bet to Even: ")
                .append(even)
                .queue((msg) -> {
                    msg.addReaction(odd).queue();
                    msg.addReaction(even).queue();
                    msg.delete().queueAfter(20L, TimeUnit.SECONDS);
                        String result = Math.random() > 0.5 ? "odd" : "even";
                    channel.sendMessageFormat("%s", result).queueAfter(20L, TimeUnit.SECONDS);
                    boolean number = result.equals("odd");

                    try {
                        TimeUnit.SECONDS.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    for (long user : bettingUserMap.get(channel.getGuild().getIdLong()).keySet()) {
                        if (bettingUserMap.get(channel.getGuild().getIdLong()).get(user) == number) {
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
        return "oddandeven";
    }

    @Override
    public String getHelp() {
        return "Play game 'odd and even'\n" +
                "You can place bets by tapping the emotion\n" +
                "(The bet is fixed at 500 cash.)";
    }
}
