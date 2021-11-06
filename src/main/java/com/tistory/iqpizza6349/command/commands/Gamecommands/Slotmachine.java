package com.tistory.iqpizza6349.command.commands.Gamecommands;

import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import com.tistory.iqpizza6349.database.MySQLDatabase;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Slotmachine implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        User user = ctx.getMessage().getAuthor();
        TextChannel channel = ctx.getChannel();
        long id = ctx.getGuild().getIdLong();


        int slotmoeny = getserverslotmoney(id);
        List<String> messages = ctx.getStrings();
        char tmp;

        if (messages.isEmpty()) {
            channel.sendMessage("돈을 입력 해 주세요.").queue();
            return;
        }

        for (int i = 0; i < messages.get(0).length(); i++) {
            tmp = messages.get(0).charAt(i);
            if (!Character.isDigit(tmp)) {
                channel.sendMessage("형식에 맞지 않는 입력 방식입니다.").queue();
                return;
            }
        }
        int usermoney = Integer.parseInt(messages.get(0));
        if (getUserMoney(user.getIdLong()) < usermoney) {
            channel.sendMessage("돈이 부족합니다.").queue();
            return;
        }


        Random rand1 = new Random();
        Random rand2 = new Random();
        Random rand3 = new Random();

        int r1 = rand1.nextInt(10);
        int r2 = rand2.nextInt(10);
        int r3 = rand3.nextInt(10);


        channel.sendMessageFormat(" { [%d] | [%d] | [%d] } ", r1, r2, r3).queue((msg) -> {
            msg.editMessageFormat(" { [%d] | [%d] | [%d] } ",
                    1 + rand1.nextInt(10),
                    1 + rand2.nextInt(10),
                    1 + rand3.nextInt(10)
                ).queueAfter(200L, TimeUnit.MILLISECONDS, (msg1) ->
                    msg1.editMessageFormat(" { [%d] | [%d] | [%d] } ",
                            1 + rand1.nextInt(10),
                            1 + rand2.nextInt(10),
                            1 + rand3.nextInt(10)
                    ).queueAfter(200L, TimeUnit.MILLISECONDS, (msg2) ->
                            msg2.editMessageFormat(" { [%d] | [%d] | [%d] } ",
                                    1 + rand1.nextInt(10),
                                    1 + rand2.nextInt(10),
                                    1 + rand3.nextInt(10)
                            ).queueAfter(200L, TimeUnit.MILLISECONDS, (msg3) ->
                                    msg3.editMessageFormat(" { [%d] | [%d] | [%d] } ",
                                            1 + rand1.nextInt(10),
                                            1 + rand2.nextInt(10),
                                            1 + rand3.nextInt(10)
                                    ).queueAfter(200L, TimeUnit.MILLISECONDS, (msg4) ->
                                            msg4.editMessageFormat(" { [%d] | [%d] | [%d] } ",
                                                    1 + rand1.nextInt(10),
                                                    1 + rand2.nextInt(10),
                                                    1 + rand3.nextInt(10)
                                            ).queueAfter(200L, TimeUnit.MILLISECONDS, (msg5) ->
                                                    msg5.editMessageFormat(" { [%d] | [%d] | [%d] } ",
                                                            1 + rand1.nextInt(10),
                                                            1 + rand2.nextInt(10),
                                                            1 + rand3.nextInt(10)
                                                    ).queueAfter(200L, TimeUnit.MILLISECONDS, (msg6) ->
                                                            msg6.editMessageFormat(" { [%d] | [%d] | [%d] } ",
                                                                    1 + rand1.nextInt(10),
                                                                    1 + rand2.nextInt(10),
                                                                    1 + rand3.nextInt(10)
                                                            ).queueAfter(200L, TimeUnit.MILLISECONDS, (msg7) ->
                                                                    msg7.editMessageFormat(" { [%d] | [%d] | [%d] } ",
                                                                            1 + rand1.nextInt(10),
                                                                            1 + rand2.nextInt(10),
                                                                            1 + rand3.nextInt(10)
                                                                    ).queueAfter(200L, TimeUnit.MILLISECONDS, (msg8) ->
                                                                            msg8.editMessageFormat(" { [%d] | [%d] | [%d] } ",
                                                                                    1 + rand1.nextInt(10),
                                                                                    1 + rand2.nextInt(10),
                                                                                    1 + rand3.nextInt(10)
                                                                            ).queueAfter(200L, TimeUnit.MILLISECONDS, (msg9) ->
                                                                                    msg9.editMessageFormat(" { [%d] | [%d] | [%d] } ",
                                                                                            1 + rand1.nextInt(10),
                                                                                            1 + rand2.nextInt(10),
                                                                                            1 + rand3.nextInt(10)
                                                                                    ).queueAfter(200L, TimeUnit.MILLISECONDS, (msg10) ->
                                                                                            msg10.editMessageFormat(" { [%d] | [%d] | [%d] } ",
                                                                                            1 + rand1.nextInt(10),
                                                                                            1 + rand2.nextInt(10),
                                                                                            1 + rand3.nextInt(10)
                                                                                    ).queueAfter(200L, TimeUnit.MILLISECONDS)))))))))));
            });



        if(r1 == r2 && r2 == r3){
            updausermoney(slotmoeny + usermoney,user.getIdLong());
            channel.sendMessage("ㅇㅇ").queue();
            reset(id);
        }else{
            String[] s = new String[3];
            s[0] = String.valueOf(r1);
            s[1] = String.valueOf(r2);
            s[2] = String.valueOf(r3);
            channel.sendMessage("ㄴㄴ").queue();

            updausermoney(getUserMoney(user.getIdLong()) - usermoney,user.getIdLong());
            slotmoeny += usermoney;
            setserverslotmoney(id,slotmoeny);
        }


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

    private void updausermoney(int money, long userId) {
        try (final PreparedStatement preparedStatement = MySQLDatabase
                .getConnection()
                .prepareStatement("UPDATE user_info SET money = ? WHERE user_id = ?")) {
            preparedStatement.setString(1, String.valueOf(money));
            preparedStatement.setString(2, String.valueOf(userId));

            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void setserverslotmoney(long serverId, int slotmoney){
        try (final PreparedStatement preparedStatement = MySQLDatabase
                .getConnection()
                .prepareStatement("UPDATE guild_settings SET slotmoney = ? WHERE guild_id = ?")) {
            preparedStatement.setString(1,String.valueOf(slotmoney));
            preparedStatement.setString(2, String.valueOf(serverId));

            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void reset(long guild_id){
        try(final PreparedStatement p =MySQLDatabase
                .getConnection()
                .prepareStatement("UPDATE guild_settings SET slotmoney = 6000 WHERE  guild_id = ?")){
            p.setString(1,String.valueOf(guild_id));

            p.executeUpdate();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }


    private int getserverslotmoney(long serverId){
        try (final PreparedStatement preparedStatement = MySQLDatabase
                .getConnection()
                .prepareStatement("SELECT slotmoney FROM guild_settings WHERE guild_id = ?")) {
            preparedStatement.setString(1, String.valueOf(serverId));

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("slotmoney");
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String getName() {
        return "slot";
    }

    @Override
    public String getHelp() {
        return "You can get money from this machine";
    }
}
