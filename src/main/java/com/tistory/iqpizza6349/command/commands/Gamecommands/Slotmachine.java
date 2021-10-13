package com.tistory.iqpizza6349.command.commands.Gamecommands;

import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import net.dv8tion.jda.api.entities.User;

public class Slotmachine implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        User user = ctx.getMessage().getAuthor();

    }

    @Override
    public String getName() {
        return "Slot";
    }

    @Override
    public String getHelp() {
        return "You can get money from this machine";
    }
}
