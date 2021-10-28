package com.tistory.iqpizza6349.command.commands.Gamecommands;

import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import net.dv8tion.jda.api.entities.Emoji;

public class sniffling implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        Emoji e;

        ctx.getChannel().sendMessage("Test").queue();

    }

    @Override
    public String getName() {
        return "sniffling";
    }

    @Override
    public String getHelp() {
        return "You just have to guess whether the number of letters is odd or even.";
    }
}
