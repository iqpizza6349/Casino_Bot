package com.tistory.iqpizza6349.command.commands.utility;

import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;

public class Calculator implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();

        if (ctx.getStrings().size() < 3) {
            channel.sendMessage("인자값이 부족합니다. [인자값이 총 3개 필요합니다.]").queue();
            return;
        }

        if (ctx.getStrings().size() > 3) {
            channel.sendMessage("인자값이 너무 많습니다. [인자값이 총 3개 필요합니다]").queue();
            return;
        }
        double number1;
        double number2;

        try {
            number1 = Double.parseDouble(ctx.getStrings().get(0));
            number2 = Double.parseDouble(ctx.getStrings().get(2));
        } catch (NumberFormatException e) {
            channel.sendMessage("계산하려는 인자값 중 하나 이상이 숫자가 아닙니다.").queue();
            return;
        }

        int plus = ctx.getStrings().indexOf("+");
        int minus = ctx.getStrings().indexOf("-");
        int multi = ctx.getStrings().indexOf("*");
        int divide = ctx.getStrings().indexOf("/");

        boolean hasOperator = plus == -1 && minus == -1 && multi == -1 && divide == -1;
        if (hasOperator) {
            channel.sendMessage("계산하기 위한 연산자가 없습니다.").queue();
            return;
        }

        if (plus != -1) {
            channel.sendMessage("> [계산결과] " + number1 + " + " + number2 + " = " + (number1 + number2)).queue();
        }
        else if (minus != -1) {
            channel.sendMessage("> [계산결과] " + number1 + " - " + number2 + " = " + (number1 - number2)).queue();
        }
        else if (multi != -1){
            channel.sendMessage("> [계산결과] " + number1 + " * " + number2 + " = " + (number1 * number2)).queue();
        }
        else {
            try {
                channel.sendMessage("> [계산결과] " + number1 + " / " + number2 + " = " + (number1 / number2)).queue();
            } catch (ArithmeticException e) {
                channel.sendMessage("0을 나눌 순 없습니다.").queue();
            }
        }

    }

    @Override
    public String getName() {
        return "계산";
    }

    @Override
    public String getHelp() {
        return "두 숫자를 연산자를 활용하여 계산합니다. (+, -, *, / 만 사용 가능합니다.)\n" +
                "Usage: (number1) (operator) (number2)\n" +
                "Example: 1 + 3";
    }
}
