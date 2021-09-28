package com.tistory.iqpizza6349.command.commands;

import com.tistory.iqpizza6349.Config;
import com.tistory.iqpizza6349.CustomPrefix;
import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.CommandManager;
import com.tistory.iqpizza6349.command.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Arrays;
import java.util.List;

public class HelpCommand implements ICommand {

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    CommandManager manager;

    @Override
    public void handle(CommandContext ctx) {
        List<String> strings = ctx.getStrings();
        TextChannel channel = ctx.getChannel();

        if (strings.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            String prefix = CustomPrefix.PREFIXES.get(ctx.getGuild().getIdLong());

            builder.append("List of commands\n");

            manager.getCommandList().stream().map(ICommand::getName).forEach(
                    (it) -> builder.append('`').append(prefix).append(it).append("`\n")
            );
            channel.sendMessage(builder.toString()).queue();
            return;
        }

        String search = strings.get(0);
        ICommand command = manager.getCommand(search);

        if (command == null) {
            channel.sendMessage("Nothing found for `" + search + "`").queue();
            return;
        }

        channel.sendMessage(command.getHelp()).queue();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getHelp() {
        return "Shows the list with commands in the bot\n" +
                "Usage: `" + Config.PREFIX + " [command]`";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("commands", "cmds", "commandlist");
    }
}
