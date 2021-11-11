package com.tistory.iqpizza6349.command.commands;

import com.fasterxml.jackson.databind.JsonNode;
import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class JokeCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();
        WebUtils.ins.getJSONObject("https://apis.duncte123.me/meme").async((json) -> {
            if (!json.get("success").asBoolean()) {
                channel.sendMessage("오류가 발생했습니다. 나중에 다시 시도해주세요").queue();
                System.out.println(json);
                return;
            }

            final JsonNode data = json.get("data");
            final String title = data.get("title").asText();
            final String url = data.get("url").asText();
            final String image = data.get("image").asText();
            final String body = data.get("body").asText();

            final EmbedBuilder embed = EmbedUtils.getDefaultEmbed()
                            .setTitle(title, url)
                                    .setDescription(body);

            channel.sendMessageEmbeds(embed.build()).queue();
        });
    }

    @Override
    public String getName() {
        return "농담";
    }

    @Override
    public String getHelp() {
        return "랜덤 농담을 reddit 에서 가져옵니다.";
    }
}
