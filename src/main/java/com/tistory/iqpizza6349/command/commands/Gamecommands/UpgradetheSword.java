package com.tistory.iqpizza6349.command.commands.Gamecommands;

import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import com.tistory.iqpizza6349.database.MySQLDatabase;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class UpgradetheSword implements ICommand {

    public HashMap<String, Integer> sword = new HashMap<>();
    public HashMap<String, String> SType = new HashMap<>();

    public UpgradetheSword() {
        sword.put("기본검", 100);
        sword.put("단도", 300);
        sword.put("장미칼", 500);
        sword.put("서바이벌 나이프", 800);
        sword.put("광선검", 1000);
        sword.put("용검", 1500);
        sword.put("마법검", 2000);
        sword.put("톱날단검", 3000);
        sword.put("열정의검", 3500);
        sword.put("몰락한왕의검", 5000);
        sword.put("사시미칼", 6000);
        sword.put("롱기누스의 창", 6500);
        sword.put("포세이돈의 삼지창", 10000);
        sword.put("제우스의 번개", 12000);
        sword.put("가이아의 심장", 20000);
        sword.put("빅뱅", 100000000);

        SType.put("기본검", "단도");
        SType.put("단도", "장미칼");
        SType.put("장미칼", "서바이벌 나이프");
        SType.put("서바이벌 나이프", "광선검");
        SType.put("광선검", "용검");
        SType.put("용검", "마법검");
        SType.put("마법검", "톱날단검");
        SType.put("톱날단검", "열정의검");
        SType.put("열정의검", "몰락한왕의검");
        SType.put("몰락한왕의검", "사시미칼");
        SType.put("사시미칼", "롱기누스의 창");
        SType.put("롱기누스의 창", "포세이돈의 삼지창");
        SType.put("포세이돈의 삼지창", "제우스의 번개");
        SType.put("제우스의 번개", "가이아의 심장");
        SType.put("가이아의 심장", "빅뱅");

    }

    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final User member = ctx.getMessage().getAuthor();
        Random r = new Random();

        String swordType = getSwordType(member.getIdLong());
        String Type = SType.get(swordType);
        int swordCost = sword.get(swordType);
        int money = getUserMoney(member.getIdLong());

        if (swordType.equals("기본검")) {
            if (money >= 100) {
                channel.sendMessage(member.getName() + "님께서 기본검 에서 " + Type + "(으)로 업그레이드 하셨습니다." + "\n"
                        + "다음 강화 성공 확률 : 95%" + "\n" + "다음 강화 비용 : 300원\n" + "판매가격 : 100원"
                ).queue();
                updateSword(Type, swordCost, member.getIdLong());
            } else if (money < 100) {
                channel.sendMessage(member.getName() + "님의 소지금이 부족합니다." + "\n" + "현재 소지금 : " + money).queue();
            } else {
                channel.sendMessage(member.getName() + "님의 검이 파괴되었습니다.").queue();
                resetSword("기본검", member.getIdLong());
            }
        } else if (swordType.equals("단도")) {
            if (money >= 300 && r.nextInt(100) <= 95) {
                channel.sendMessage(member.getName() + "님께서 " + swordType + " 에서 " + Type + "(으)로 업그레이드 하셨습니다." + "\n"
                        + "다음 강화 성공 확률 : 93%" + "\n" + "다음 강화 비용 : 500원\n" + "판매가격 : 200원"
                ).queue();
                updateSword(Type, swordCost, member.getIdLong());
            } else if (money < 300) {
                channel.sendMessage(member.getName() + "님의 소지금이 부족합니다." + "\n" + "현재 소지금 : " + money).queue();
            } else {
                channel.sendMessage(member.getName() + "님의 검이 파괴되었습니다.").queue();
                resetSword("기본검", member.getIdLong());
            }
        } else if (swordType.equals("장미칼")) {
            if (money >= 500 && r.nextInt(100) <= 93) {
                channel.sendMessage(member.getName() + "님께서 " + swordType + " 에서 " + Type + "(으)로 업그레이드 하셨습니다." + "\n"
                        + "다음 강화 성공 확률 : 90%" + "\n" + "다음 강화 비용 : 800원\n" + "판매가격 : 1000원"
                ).queue();
                updateSword(Type, swordCost, member.getIdLong());
            } else if (money < 500) {
                channel.sendMessage(member.getName() + "님의 소지금이 부족합니다." + "\n" + "현재 소지금 : " + money).queue();
            } else {
                channel.sendMessage((member.getName() + "님의 검이 파괴되었습니다.")).queue();
                resetSword("기본검", member.getIdLong());
            }
        } else if (swordType.equals("서바이벌 나이프")) {
            if (money >= 800 && r.nextInt(100) <= 90) {
                channel.sendMessage(member.getName() + "님께서 " + swordType + " 에서 " + Type + "(으)로 업그레이드 하셨습니다." + "\n"
                        + "다음 강화 성공 확률 : 85%" + "\n" + "다음 강화 비용 : 1000원\n" + "판매가격 : 1700원"
                ).queue();
                updateSword(Type, swordCost, member.getIdLong());
            } else if (money < 800) {
                channel.sendMessage(member.getName() + "님의 소지금이 부족합니다." + "\n" + "현재 소지금 : " + money).queue();
            } else {
                channel.sendMessage(member.getName() + "님의 검이 파괴되었습니다.").queue();
                resetSword("기본검", member.getIdLong());
            }
        } else if (swordType.equals("광선검")) {
            if (money >= 1000 && r.nextInt(100) <= 85) {
                channel.sendMessage(member.getName() + "님께서 " + swordType + " 에서 " + Type + "(으)로 업그레이드 하셨습니다." + "\n"
                        + "다음 강화 성공 확률 : 80%" + "\n" + "다음 강화 비용 : 1500원\n" + "판매가격 : 5000원"
                ).queue();
                updateSword(Type, swordCost, member.getIdLong());
            } else if (money < 1000) {
                channel.sendMessage(member.getName() + "님의 소지금이 부족합니다." + "\n" + "현재 소지금 : " + money).queue();
            } else {
                channel.sendMessage(member.getName() + "님의 검이 파괴되었습니다.").queue();
                resetSword("기본검", member.getIdLong());
            }
        } else if (swordType.equals("용검")) {
            if (money >= 1500 && r.nextInt(100) <= 80) {
                channel.sendMessage(member.getName() + "님께서 " + swordType + " 에서 " + Type + "(으)로 업그레이드 하셨습니다." + "\n"
                        + "다음 강화 성공 확률 : 76%" + "\n" + "다음 강화 비용 : 2000원\n" + "판매가격 : 8000원"
                ).queue();
                updateSword(Type, swordCost, member.getIdLong());
            }else if (money < 1500) {
                channel.sendMessage(member.getName() + "님의 소지금이 부족합니다." + "\n" + "현재 소지금 : " + money).queue();
            } else {
                channel.sendMessage(member.getName() + "님의 검이 파괴되었습니다.").queue();
                resetSword("기본검", member.getIdLong());
            }
        } else if (swordType.equals("마법검")) {
            if (money >= 2000 && r.nextInt(100) <= 76) {
                channel.sendMessage(member.getName() + "님께서 " + swordType + " 에서 " + Type + "(으)로 업그레이드 하셨습니다." + "\n"
                        + "다음 강화 성공 확률 : 70%" + "\n" + "다음 강화 비용 : 3000원\n" + "판매가격 : 15000원"
                ).queue();
                updateSword(Type, swordCost, member.getIdLong());
            } else if (money < 2000) {
                channel.sendMessage(member.getName() + "님의 소지금이 부족합니다." + "\n" + "현재 소지금 : " + money).queue();
            } else {
                channel.sendMessage(member.getName() + "님의 검이 파괴되었습니다.").queue();
                resetSword("기본검", member.getIdLong());
            }
        } else if (swordType.equals("톱날단검")) {
            if (money >= 3000 && r.nextInt(100) <= 70) {
                channel.sendMessage(member.getName() + "님께서 " + swordType + " 에서 " + Type + "(으)로 업그레이드 하셨습니다." + "\n"
                        + "다음 강화 성공 확률 : 65%" + "\n" + "다음 강화 비용 : 3500원\n" + "판매가격 : 20000원"
                ).queue();
                updateSword(Type, swordCost, member.getIdLong());
            } else if (money < 3000) {
                channel.sendMessage(member.getName() + "님의 소지금이 부족합니다." + "\n" + "현재 소지금 : " + money).queue();
            } else {
                channel.sendMessage(member.getName() + "님의 검이 파괴되었습니다.").queue();
                resetSword("기본검", member.getIdLong());
            }
        } else if (swordType.equals("열정의검")) {
            if (money >= 3500 && r.nextInt(100) <= 65) {
                channel.sendMessage(member.getName() + "님께서 " + swordType + " 에서 " + Type + "(으)로 업그레이드 하셨습니다." + "\n"
                        + "다음 강화 성공 확률 : 50%" + "\n" + "다음 강화 비용 : 5000원\n" + "판매가격 : 30000원"
                ).queue();
                updateSword(Type, swordCost, member.getIdLong());
            } else if (money < 3500) {
                channel.sendMessage(member.getName() + "님의 소지금이 부족합니다." + "\n" + "현재 소지금 : " + money).queue();
            } else {
                channel.sendMessage(member.getName() + "님의 검이 파괴되었습니다.").queue();
                resetSword("기본검", member.getIdLong());
            }
        } else if (swordType.equals("몰락한왕의검")) {
            if (money >= 5000 && r.nextInt(100) <= 50) {
                channel.sendMessage(member.getName() + "님께서 " + swordType + " 에서 " + Type + "(으)로 업그레이드 하셨습니다." + "\n"
                        + "다음 강화 성공 확률 : 40%" + "\n"  + "다음 강화 비용 : 6000원\n"+ "판매가격 : 43000원"
                ).queue();
                updateSword(Type, swordCost, member.getIdLong());
            } else if (money < 5000) {
                channel.sendMessage(member.getName() + "님의 소지금이 부족합니다." + "\n" + "현재 소지금 : " + money).queue();
            } else {
                channel.sendMessage(member.getName() + "님의 검이 파괴되었습니다.").queue();
                resetSword("기본검", member.getIdLong());
            }
        } else if (swordType.equals("사시미칼")) {
            if (money >= 6000 && r.nextInt(100) <= 40) {
                channel.sendMessage(member.getName() + "님께서 " + swordType + " 에서 " + Type + "(으)로 업그레이드 하셨습니다." + "\n"
                        + "다음 강화 성공 확률 : 30%" + "\n" + "다음 강화 비용 : 6500원\n" + "판매가격 : 50000원"
                ).queue();
                updateSword(Type, swordCost, member.getIdLong());
            } else if (money < 6000) {
                channel.sendMessage(member.getName() + "님의 소지금이 부족합니다." + "\n" + "현재 소지금 : " + money).queue();
            } else {
                channel.sendMessage(member.getName() + "님의 검이 파괴되었습니다.").queue();
                resetSword("기본검", member.getIdLong());
            }
        } else if (swordType.equals("롱기누스의 창")) {
            if (money >= 6500 && r.nextInt(100) <= 30) {
                channel.sendMessage(member.getName() + "님께서 " + swordType + " 에서 " + Type + "(으)로 업그레이드 하셨습니다." + "\n"
                        + "다음 강화 성공 확률 : 10%" + "\n" + "다음 강화 비용 : 10000원\n" + "판매가격 : 130000원"
                ).queue();
                updateSword(Type, swordCost, member.getIdLong());
            } else if (money < 6500) {
                channel.sendMessage(member.getName() + "님의 소지금이 부족합니다." + "\n" + "현재 소지금 : " + money).queue();
            } else {
                channel.sendMessage(member.getName() + "님의 검이 파괴되었습니다.").queue();
                resetSword("기본검", member.getIdLong());
            }
        } else if (swordType.equals("포세이돈의 창")) {
            if (money >= 10000 && r.nextInt(100) <= 10) {
                channel.sendMessage(member.getName() + "님께서 " + swordType + " 에서 " + Type + "(으)로 업그레이드 하셨습니다." + "\n"
                        + "다음 강화 성공 확률 : 5%" + "\n" + "다음 강화 비용 : 12000원\n" + "판매가격 : 200000원"
                ).queue();
                updateSword(Type, swordCost, member.getIdLong());
            } else if (money < 10000) {
                channel.sendMessage(member.getName() + "님의 소지금이 부족합니다." + "\n" + "현재 소지금 : " + money).queue();
            } else {
                channel.sendMessage(member.getName() + "님의 검이 파괴되었습니다.").queue();
                resetSword("기본검", member.getIdLong());
            }
        } else if (swordType.equals("제우스의 번개")) {
            if (money >= 12000 && r.nextInt(100) <= 5) {
                channel.sendMessage(member.getName() + "님께서 " + swordType + " 에서 " + Type + "(으)로 업그레이드 하셨습니다." + "\n"
                        + "다음 강화 성공 확률 : 1%" + "\n" + "다음 강화 비용 : 20000원\n" + "판매가격 : 1000000원"
                ).queue();
                updateSword(Type, swordCost, member.getIdLong());
            } else if (money < 12000) {
                channel.sendMessage(member.getName() + "님의 소지금이 부족합니다." + "\n" + "현재 소지금 : " + money).queue();
            } else {
                channel.sendMessage(member.getName() + "님의 검이 파괴되었습니다.").queue();
                resetSword("기본검", member.getIdLong());
            }
        } else if (swordType.equals("가이아의 심장")) {
            if (money >= 20000 && r.nextInt(100) <= 1) {
                channel.sendMessage(member.getName() + "님께서 " + swordType + " 에서 " + Type + "(으)로 업그레이드 하셨습니다." + "\n"
                        + "다음 강화가 없습니다." + "\n" + "판매가격 : 100000000원"
                ).queue();
                updateSword(Type, swordCost, member.getIdLong());
            }

        }else if(swordType.equals("빅뱅")){
            channel.sendMessage(member.getName() + "님의 검이 이미 최대치입니다.").queue();
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

    private void updateSword(String swordType, int money, long userId) {
        try (final PreparedStatement preparedStatement = MySQLDatabase
                .getConnection()
                .prepareStatement("UPDATE user_info SET sword = ?, money = ? WHERE user_id = ?")) {
            preparedStatement.setString(1, swordType);
            preparedStatement.setString(2, String.valueOf(getUserMoney(userId) - money));
            preparedStatement.setString(3, String.valueOf(userId));

            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void resetSword(String sword,long userId){
        try(final PreparedStatement preparedStatement = MySQLDatabase
                .getConnection()
                .prepareStatement("UPDATE user_info SET sword = ? WHERE user_id = ?")) {
            preparedStatement.setString(1,sword);
            preparedStatement.setString(2,String.valueOf(userId));

            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getName() {
        return "upgrade";
    }

    @Override
    public String getHelp() {
        return "you can upgrade your sword and you can get money";
    }
}
