package com.tistory.iqpizza6349.command.commands.music;

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

public class StopCommand implements ICommand {

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

        musicManager.scheduler.player.stopTrack();
        musicManager.scheduler.queue.clear();
        clearQueue(ctx.getGuild().getIdLong());

        textChannel.sendMessage("The Player has been stopped and the queue has been cleared!").queue();
    }

    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public String getHelp() {
        return "Stops the current song and clear\n" +
                "Usage: `" + Config.PREFIX + "stop`";
    }

    public static void clearQueue(long guildId) {
        String stringGuild_id = String.valueOf(guildId);
        try (final PreparedStatement insertCurrentSong = MySQLDatabase
                .getConnection()
                .prepareStatement("UPDATE music SET current_song = ?, queue = ?, count = ? WHERE guild_id = ?")) {
            insertCurrentSong.setString(1, "NULL");
            insertCurrentSong.setString(2, "NULL");
            insertCurrentSong.setString(3, String.valueOf(0));
            insertCurrentSong.setString(4, stringGuild_id);
            insertCurrentSong.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
