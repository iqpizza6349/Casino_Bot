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

        if (bettingUserMap.containsKey(channel.getGuild().getIdLong())) {
            channel.sendMessage("이미 게임을 진행 중입니다.").queue();
            return;
        }

        channel.sendMessage("20초가 지나기 전에 홀수 혹은 짝수에 배팅하여주세요.\n")
                .append("홀수에 배팅하실려면 눌러주세요: ")
                .append(odd)
                .append("\n")
                .append("짝수에 배팅하실려면 눌러주세요: ")
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
                            channel.sendMessageFormat("<@%s>, 축하드립니다. 맞추셨습니다.", user).queue();
                        }
                        else {
                            channel.sendMessageFormat("<@%s>, 안타깝습니다. 틀리셨습니다.", user).queue();
                        }
                    }
                    bettingUserMap.remove(channel.getGuild().getIdLong());

                });
        bettingUserMap.put(channel.getGuild().getIdLong(), new HashMap<>());
    }

    @Override
    public String getName() {
        return "홀짝";
    }

    @Override
    public String getHelp() {
        return "홀짝 게임을 합니다\n" +
                "이모티콘을 눌러 홀수 혹은 짝수를 선택하실 수 있으십니다.";
    }
}
