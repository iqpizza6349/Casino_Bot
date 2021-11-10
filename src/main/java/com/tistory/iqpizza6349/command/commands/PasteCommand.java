package com.tistory.iqpizza6349.command.commands;

import com.tistory.iqpizza6349.Config;
import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.menudocs.paste.PasteClient;
import org.menudocs.paste.PasteClientBuilder;

import java.util.List;

public class PasteCommand implements ICommand {

    private final PasteClient client = new PasteClientBuilder()
            .setUserAgent("MenuDocs Tutorial Bot")
            .setDefaultExpiry("10m")
            .build();

    @Override
    public void handle(CommandContext ctx) {
        final List<String> strings = ctx.getStrings();
        final TextChannel channel = ctx.getChannel();

        if (strings.size() < 2) {
            channel.sendMessage("Missing arguments").queue();
            return;
        }

        final String language = strings.get(0);
        final String contentRaw = ctx.getMessage().getContentRaw();
        final int index =contentRaw.indexOf(language) + language.length();
        final String body = contentRaw.substring(index).trim();

        if (Config.debug) {
            System.out.println(language);
            System.out.println(body);
        }

        client.createPaste(language, body).async(
                (id) -> client.getPaste(id).async((paste) -> {
                    EmbedBuilder builder = new EmbedBuilder()
                            .setTitle("Paste " + id, paste.getPasteUrl())
                            .setDescription("```")
                            .appendDescription(paste.getLanguage().getId())
                            .appendDescription("\n")
                            .appendDescription(paste.getBody())
                            .appendDescription("```");

                    channel.sendMessageEmbeds(builder.build()).queue();
                })
        );
    }

    @Override
    public String getName() {
        return "paste";
    }

    @Override
    public String getHelp() {
        return "Creates a paste on the menuDocs paste\n" +
                "Usage: `" + Config.PREFIX + " [language] [text]`";
    }
}
