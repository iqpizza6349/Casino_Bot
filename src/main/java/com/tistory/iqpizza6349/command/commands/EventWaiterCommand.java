package com.tistory.iqpizza6349.command.commands;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

import java.util.concurrent.TimeUnit;

public class EventWaiterCommand implements ICommand {

    private static final String EMOTE = "❤";
    private final EventWaiter waiter;

    public EventWaiterCommand(EventWaiter waiter) {
        this.waiter = waiter;
    }

    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();

        channel.sendMessage("React with ")
                .append(EMOTE)
                .queue((msg) -> {
                    msg.addReaction(EMOTE).queue();

                    this.waiter.waitForEvent(
                            GuildMessageReactionAddEvent.class,
                            (event) -> event.getMessageIdLong() == msg.getIdLong() && !event.getUser().isBot(),
                            (event) -> {
                                channel.sendMessageFormat("%#s was the first to react", event.getUser()).queue();
                            }, 5L, TimeUnit.SECONDS,
                            () -> channel.sendMessage("You waited too long").queue()
                    );
                });
    }

    @Override
    public String getName() {
        return "eventwaiter";
    }

    @Override
    public String getHelp() {
        return "Just an event waiter example";
    }
}
