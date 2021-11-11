package com.tistory.iqpizza6349.command.commands.game;

import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import com.tistory.iqpizza6349.database.MySQLDatabase;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class sellcommand implements ICommand {


    public HashMap<String, Integer> sword = new HashMap<>();

    public sellcommand(){
        sword.put("기본검", 100);
        sword.put("단도", 100);
        sword.put("장미칼", 200);
        sword.put("서바이벌 나이프", 1000);
        sword.put("광선검", 1700);
        sword.put("용검", 5000);
        sword.put("마법검", 8000);
        sword.put("톱날단검",15000);
        sword.put("열정의검", 20000);
        sword.put("몰락한왕의검", 30000);
        sword.put("사시미칼", 43000);
        sword.put("롱기누스의 창", 50000);
        sword.put("포세이돈의 삼지창", 130000);
        sword.put("제우스의 번개", 200000);
        sword.put("가이아의 심장", 1000000);
        sword.put("빅뱅", 100000000);

    }
    @Override
    public void handle(CommandContext ctx) {
        User member = ctx.getMessage().getAuthor();
        final TextChannel channel = ctx.getChannel();
        int money = getUserMoney(member.getIdLong());
        String swordType = getSwordType(member.getIdLong());
        int cost = sword.get(swordType);
        if(swordType.equals("기본검")){
            channel.sendMessage(member.getName() + "님의 검은 기본검이므로 판매가 불가능 합니다.").queue();
        }else {
            UpdateUserMoney(member.getIdLong(), cost, money, "기본검");
            money = getUserMoney(member.getIdLong());
            channel.sendMessage(member.getName() + "님이 " + swordType + "을 팔으셨습니다.\n" +"판매가격 : "+ cost + "\n소지금 : " + money).queue();
            }
    }

    private String getSwordType(long userId) {
        try (final PreparedStatement preparedStatement = MySQLDatabase
                .getConnection()
                .prepareStatement("SELECT sword FROM user_info WHERE user_id = ?")) {
            preparedStatement.setString(1, String.valueOf(userId));

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("sword");
                }
            }

            try (final PreparedStatement statement = MySQLDatabase
                    .getConnection()
                    .prepareStatement("INSERT INTO user_info(user_id) VALUES(?)")) {
                statement.setString(1, String.valueOf(userId));

                statement.executeUpdate();
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "기본검";
    }

    private int getUserMoney(long userId) {
        try (final PreparedStatement preparedStatement = MySQLDatabase
                .getConnection()
                .prepareStatement("SELECT money FROM user_info WHERE user_id = ?")) {
            preparedStatement.setString(1, String.valueOf(userId));

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("money");
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
    private void UpdateUserMoney(long userId,int cost,int money,String sword){
        try (final PreparedStatement preparedStatement = MySQLDatabase
                .getConnection()
                .prepareStatement("UPDATE user_info SET sword = ?,money = ? WHERE user_id = ?")) {
            preparedStatement.setString(1, String.valueOf(sword));
            preparedStatement.setString(2, String.valueOf(money + cost));
            preparedStatement.setString(3, String.valueOf(userId));

            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "판매";
    }

    @Override
    public String getHelp() {
        return "강화한 검들을 팔 수 있습니다.";
    }
}
