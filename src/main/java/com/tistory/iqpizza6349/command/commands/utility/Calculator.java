package com.tistory.iqpizza6349.command.commands.utility;

import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;

public class Calculator implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();

        if (ctx.getStrings().size() < 3) {
            channel.sendMessage("Insufficient argument values.").queue();
            return;
        }

        if (ctx.getStrings().size() > 3) {
            channel.sendMessage("too many arguments!").queue();
            return;
        }
        double number1;
        double number2;

        try {
            number1 = Double.parseDouble(ctx.getStrings().get(0));
            number2 = Double.parseDouble(ctx.getStrings().get(2));
        } catch (NumberFormatException e) {
            channel.sendMessage("arguments have no digits!").queue();
            return;
        }

        int plus = ctx.getStrings().indexOf("+");
        int minus = ctx.getStrings().indexOf("-");
        int multi = ctx.getStrings().indexOf("*");
        int divide = ctx.getStrings().indexOf("/");

        boolean hasOperator = plus == -1 && minus == -1 && multi == -1 && divide == -1;
        if (hasOperator) {
            channel.sendMessage("arguments have no operator!").queue();
            return;
        }

        if (plus != -1) {
            channel.sendMessage("> [Calculated] " + number1 + " + " + number2 + " = " + (number1 + number2)).queue();
        }
        else if (minus != -1) {
            channel.sendMessage("> [Calculated] " + number1 + " - " + number2 + " = " + (number1 - number2)).queue();
        }
        else if (multi != -1){
            channel.sendMessage("> [Calculated] " + number1 + " * " + number2 + " = " + (number1 * number2)).queue();
        }
        else {
            try {
                channel.sendMessage("> [Calculated] " + number1 + " / " + number2 + " = " + (number1 / number2)).queue();
            } catch (ArithmeticException e) {
                channel.sendMessage("Can't calculate `/` by zero").queue();
            }
        }

    }

    @Override
    public String getName() {
        return "calculate";
    }

    @Override
    public String getHelp() {
        return "Calculate two numbers with operator (only +, -, *, /)\n" +
                "Usage: (number1) (operator) (number2)\n" +
                "Example: 1 + 3";
    }
}
