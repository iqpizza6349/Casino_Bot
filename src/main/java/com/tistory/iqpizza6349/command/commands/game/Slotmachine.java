package com.tistory.iqpizza6349.command.commands.game;

import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import com.tistory.iqpizza6349.database.MySQLDatabase;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Slotmachine implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        User user = ctx.getMessage().getAuthor();
        TextChannel channel = ctx.getChannel();



    }



    private void setserverslotmoney(long serverId, int playerlosemoney){
        try (final PreparedStatement preparedStatement = MySQLDatabase
                .getConnection()
                .prepareStatement("UPDATE guild_settings SET slotmoney = ? WHERE guild_id = ?")) {
            preparedStatement.setString(1,String.valueOf(playerlosemoney));
            preparedStatement.setString(2, String.valueOf(serverId));

            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getserverslotmoney(long serverId){
        try (final PreparedStatement preparedStatement = MySQLDatabase
                .getConnection()
                .prepareStatement("SELECT slotmoney FROM guild_settings WHERE guild_id")) {
            preparedStatement.setString(1, String.valueOf(serverId));

            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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
