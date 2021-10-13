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
                        .sendMessageFormat("RESET ping: %sms\nWS ping: %sms", ping, jda.getGatewayPing()).queue()
        );
        final User self = ctx.getMessage().getAuthor();
        final TextChannel channel = ctx.getChannel();
        channel.sendMessageFormat("<@%s>'s ping", self.getId()).queue();
    }

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getHelp() {
        return "shows the current ping from the bot to the discord server";
    }
}
