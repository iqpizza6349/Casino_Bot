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
            channel.sendMessage("이미 게임을 진행 중입니다.").queue();
            return;
        }

        channel.sendMessage("20초가 지나기 전에 앞면 혹은 뒷면에 배팅하여주세요.\n")
                .append("앞면에 배팅하실려면 눌러주세요: ")
                .append(head)
                .append("\n")
                .append("뒷면에 배팅하실려면 눌러주세요: ")
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
        return "동전";
    }

    @Override
    public String getHelp() {
        return "동전을 굴립니다.\n" +
                "이모티콘을 눌러 앞면 혹은 뒷면을 선택하실 수 있으십니다.";
    }
}
