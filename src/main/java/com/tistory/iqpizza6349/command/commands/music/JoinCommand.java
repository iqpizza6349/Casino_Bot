package com.tistory.iqpizza6349.command.commands.music;

import com.tistory.iqpizza6349.Config;
import com.tistory.iqpizza6349.CustomPrefix;
import com.tistory.iqpizza6349.Listener;
import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import com.tistory.iqpizza6349.database.MySQLDatabase;
import com.tistory.iqpizza6349.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JoinCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        final TextChannel textChannel = ctx.getChannel();
        final Member selfMember = ctx.getSelfMember();
        final GuildVoiceState selfVoiceState = selfMember.getVoiceState();

        assert selfVoiceState != null;
        if (selfVoiceState.inVoiceChannel()) {
            textChannel.sendMessage("I'm already in a voice channel!").queue();
            return;
        }

        final Member member = ctx.getMember();
        final GuildVoiceState memberGuildVoiceState = member.getVoiceState();

        assert memberGuildVoiceState != null;
        if (!memberGuildVoiceState.inVoiceChannel()) {
            textChannel.sendMessage("You need to be in a voice channel for this command to work!").queue();
            return;
        }

        final AudioManager audioManager = ctx.getGuild().getAudioManager();
        final VoiceChannel memberVoiceChannel = memberGuildVoiceState.getChannel();

        audioManager.openAudioConnection(memberVoiceChannel);
        assert memberVoiceChannel != null;
        textChannel.sendMessageFormat("Connecting to `\uD83D\uDD0A %s`", memberVoiceChannel.getName()).queue();

        int queueCount = PlayCommand.getQueueCount(textChannel.getGuild().getIdLong());

        if (getCurrentString(textChannel.getGuild().getIdLong()) == null) {
            return;
        }
        String current = getCurrentString(textChannel.getGuild().getIdLong());

        String currentSong = String.join(" ", current);

        if (!isUrl(currentSong)) {
            currentSong = "ytsearch:" + currentSong;
        }
        PlayerManager.getInstance()
                .loadAndPlay(textChannel, currentSong);

        if (queueCount > 0) {

            String[] allList = getQueueString(textChannel.getGuild().getIdLong()).split(";");
            for (String list : allList) {

                String link = String.join(" ", list);

                if (!isUrl(link)) {
                    link = "ytsearch:" + link;
                }
                PlayerManager.getInstance()
                        .loadAndPlay(textChannel, link);
            }

        }

    }

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getHelp() {
        return "Makes the bot join your voice channel\n" +
                "Usage: `" + Config.PREFIX + "join`";
    }

    private String getCurrentString(long guildId) {
        try (final PreparedStatement preparedStatement = MySQLDatabase
                .getConnection()
                .prepareStatement("SELECT current_song FROM music WHERE guild_id = ?")) {
            preparedStatement.setString(1, String.valueOf(guildId));

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("current_song");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getQueueString(long guildId) {
        try (final PreparedStatement preparedStatement = MySQLDatabase
                .getConnection()
                .prepareStatement("SELECT queue FROM music WHERE guild_id = ?")) {
            preparedStatement.setString(1, String.valueOf(guildId));

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("queue");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isUrl(String url) {
        try {
            new URL(url);
            return true;
        }catch (MalformedURLException e) {
            return false;
        }
    }
}
