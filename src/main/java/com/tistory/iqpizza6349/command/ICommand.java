package com.tistory.iqpizza6349.command;

import org.json.simple.parser.ParseException;

import java.util.List;

public interface ICommand {

    void handle(CommandContext ctx);

    String getName();

    String getHelp();

    default List<String> getAliases() {
        return List.of();
    }
}
