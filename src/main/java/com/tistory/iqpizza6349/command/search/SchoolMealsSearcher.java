package com.tistory.iqpizza6349.command.search;

import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import com.tistory.iqpizza6349.jsoup.module.CrawlerModule;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SchoolMealsSearcher implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        CrawlerModule module = new CrawlerModule();

        Iterator<String> result = module.getCrawler("schoolmeals")
                .handle(module,
                        "https://stu.dge.go.kr/sts_sci_md00_001.do?schulCode=D100000282&schulCrseScCode=2&schulKndScCode=02",
                        null, "schoolmeals");

        int type = -1;
        TextChannel channel = ctx.getChannel();

        HashMap<Integer, ArrayList<String>> map = new HashMap<>();
                
        while (result.hasNext()) {
            String nextString = result.next();
            if (nextString.equals("!")) {
                type = 0;
                map.put(0, new ArrayList<>());
            }
            else if (nextString.equals("@")) {
                type = 1;
                map.put(1, new ArrayList<>());
            }
            else if (nextString.equals("#")) {
                type = 2;
                map.put(2, new ArrayList<>());
            }
            else {
                if (type == 0) {
                    map.get(0).add(nextString);
                }
                else if (type == 1) {
                    map.get(1).add(nextString);
                }
                else if (type == 2) {
                    map.get(2).add(nextString);
                }
            }
        }
        if (!map.containsKey(0) && !map.containsKey(1) && !map.containsKey(2)) {
            channel.sendMessage("오늘은 급식이 없습니다!").queue();
            return;
        }
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("[대소고] 오늘 급식");

        if (map.containsKey(0)) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String s : map.get(0)) {
                stringBuilder.append(s).append("\n");
            }
            builder.addField("조식", stringBuilder.toString(), false);
        }
        else {
            builder.addField("조식", "조식이 없습니다.", false);
        }

        if (map.containsKey(1)) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String s : map.get(1)) {
                stringBuilder.append(s).append("\n");
            }
            builder.addField("중식", stringBuilder.toString(), false);
        }
        else {
            builder.addField("중식", "중식이 없습니다.", false);
        }

        if (map.containsKey(2)) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String s : map.get(2)) {
                stringBuilder.append(s).append("\n");
            }
            builder.addField("석식", stringBuilder.toString(), false);
        }
        else {
            builder.addField("석식", "석식이 없습니다.", false);
        }
        builder.setColor(Color.ORANGE);
        channel.sendMessageEmbeds(builder.build()).queue();
    }

    @Override
    public String getName() {
        return "schoolmeals";
    }

    @Override
    public String getHelp() {
        return "Search 'daegu-software' High School meals today";
    }
}
