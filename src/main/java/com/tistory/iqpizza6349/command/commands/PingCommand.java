package com.tistory.iqpizza6349.command.commands;

import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class PingCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        JDA jda = ctx.getJDA();

        jda.getRestPing().queue(
                (ping) -> ctx.getChannel()
                        .sendMessageFormat("[ 리셋 핑 ]: %sms\n[ 웹 소켓 핑 ]: %sms", ping, jda.getGatewayPing()).queue()
        );
        final User self = ctx.getMessage().getAuthor();
        final TextChannel channel = ctx.getChannel();
        channel.sendMessageFormat("<@%s>의 핑입니다.", self.getId()).queue();
    }

    @Override
    public String getName() {
        return "핑";
    }

    @Override
    public String getHelp() {
        return "봇이 디스코드 서버까지 도달하는 데 소요하는 핑을 알려줍니다.";
    }
}
