package com.tistory.iqpizza6349.command.commands.utility;

import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import com.tistory.iqpizza6349.translate.PapagoTranslate;
import net.dv8tion.jda.api.entities.TextChannel;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.List;

public class Translate implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();
        List<String> messages = ctx.getStrings();

        if (messages.isEmpty()) {
            channel.sendMessage("no arguments").queue();
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
            channel.sendMessage("[ERROR]").queue();
        }
    }

    @Override
    public String getName() {
        return "translate";
    }

    @Override
    public String getHelp() {
        return "Translate only kr to en";
    }
}
