package com.tistory.iqpizza6349.command.commands.game;

import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Random;

public class Dice implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();
        Random random = new Random();
        channel.sendMessageFormat("주사위의 눈은 [%d]입니다.", 1 + random.nextInt(6)).queue();
    }

    @Override
    public String getName() {
        return "주사위";
    }

    @Override
    public String getHelp() {
        return "(1~6) 숫자 중 하나를 랜덤으로 출력합니다.";
    }
}
