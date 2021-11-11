package com.tistory.iqpizza6349.command.commands;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import com.tistory.iqpizza6349.Config;
import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

public class WebhookCommand implements ICommand {

    private final WebhookClient client;

    public WebhookCommand() {
        WebhookClientBuilder builder = new WebhookClientBuilder(Config.WEBHOOK_URL);
        builder.setThreadFactory((job) -> {
            Thread thread = new Thread(job);
            thread.setName("Webhook-Thread");
            thread.setDaemon(true);
            return thread;
        });

        this.client = builder.build();
    }

    @Override
    public void handle(CommandContext ctx) {
        final List<String> strings = ctx.getStrings();
        final TextChannel channel = ctx.getChannel();

        if (strings.isEmpty()) {
            channel.sendMessage("Missing Arguments!").queue();
            return;
        }

        final User user = ctx.getAuthor();

        WebhookMessageBuilder messageBuilder = new WebhookMessageBuilder()
                .setUsername(user.getName())
                .setAvatarUrl(user.getEffectiveAvatarUrl().replaceFirst("gif", "png") + "?size=512")
                .setContent(String.join(" ", strings));

        client.send(messageBuilder.build());
    }

    @Override
    public String getName() {
        return "webhook";
    }

    @Override
    public String getHelp() {
        return "Send a webhook messages as your name\n" +
                "Usage: `" + Config.PREFIX + "webhook [message]`";
    }
}
