package com.tistory.iqpizza6349.command.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.tistory.iqpizza6349.Config;
import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import com.tistory.iqpizza6349.database.MySQLDatabase;
import com.tistory.iqpizza6349.lavaplayer.GuildMusicManager;
import com.tistory.iqpizza6349.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RepeatCommand implements ICommand {

    // Class of name is 'RepeatCommand' but, actually the user use command 'loop'
    // because the 'loop' is more simply than 'repeat'

    @Override
    public void handle(CommandContext ctx) {
        final TextChannel textChannel = ctx.getChannel();
        final Member selfMember = ctx.getSelfMember();
        final GuildVoiceState selfVoiceState = selfMember.getVoiceState();

        assert selfVoiceState != null;
        if (!selfVoiceState.inVoiceChannel()) {
            textChannel.sendMessage("I need to be in a voice channel for this to work").queue();
            return;
        }

        final Member member = ctx.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        assert memberVoiceState != null;
        if (!memberVoiceState.inVoiceChannel()) {
            textChannel.sendMessage("You need to be in a voice channel for this command to work").queue();
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            textChannel.sendMessage("You need to be in the same voice channel as me for this to work").queue();
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final boolean newRepeating = !musicManager.scheduler.repeating;

        musicManager.scheduler.repeating = newRepeating;
        setRepeat(ctx.getGuild().getIdLong(), newRepeating);

        textChannel.sendMessageFormat("The player has been set to **%s**", newRepeating ? "loop Enable" : "loop Disable").queue();
    }

    @Override
    public String getName() {
        return "loop";
    }

    @Override
    public String getHelp() {
        return "Loops the current song\n" +
                "Usage: `" + Config.PREFIX + "loop`";
    }

    public static void setRepeat(long guildId, boolean isRepeat) {
        int repeatValue;
        if (isRepeat)
            repeatValue = 1;
        else
            repeatValue = 0;
        try (final PreparedStatement preparedStatement = MySQLDatabase.getConnection()
                .prepareStatement("UPDATE music SET `repeat` = ? WHERE guild_id = ?")) {

            preparedStatement.setString(1, String.valueOf(repeatValue));
            preparedStatement.setString(2, String.valueOf(guildId));

            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean getRepeat(long guildId) {
        try (final PreparedStatement preparedStatement = MySQLDatabase
                .getConnection()
                .prepareStatement("SELECT `repeat` FROM music WHERE guild_id = ?")) {

            preparedStatement.setString(1, String.valueOf(guildId));

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean("repeat");
                }
            }

            try (final PreparedStatement insertStatement = MySQLDatabase
                    .getConnection()
                    .prepareStatement("INSERT INTO music(guild_id) VALUES(?) ")) {

                insertStatement.setString(1, String.valueOf(guildId));

                insertStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

}
