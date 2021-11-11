package com.tistory.iqpizza6349.command.commands.utility;

import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import com.tistory.iqpizza6349.translate.PapagoTranslate;
import net.dv8tion.jda.api.entities.TextChannel;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.List;

public class Translate implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();
        List<String> messages = ctx.getStrings();

        if (messages.isEmpty()) {
            channel.sendMessage("인자값이 없습니다.").queue();
            return;
        }

        StringBuilder builder = new StringBuilder();
        for (String s : messages) {
            builder.append(s).append(" ");
        }

        String wholeMessages = builder.toString();

        PapagoTranslate translate = new PapagoTranslate();
        String text = translate.setText(wholeMessages);

        try {
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(text);
            JSONObject parse_message = (JSONObject) object.get("message");
            JSONObject parse_result = (JSONObject) parse_message.get("result");

            String s = (String) parse_result.get("translatedText");
            channel.sendMessage(s).queue();
        } catch (Exception e) {
            channel.sendMessage("[오류]").queue();
        }
    }

    @Override
    public String getName() {
        return "번역";
    }

    @Override
    public String getHelp() {
        return "한국어에서 영어로만 번역 가능합니다.";
    }
}