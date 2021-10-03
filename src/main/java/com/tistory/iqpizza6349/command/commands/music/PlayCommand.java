package com.tistory.iqpizza6349.command.commands.music;

import com.tistory.iqpizza6349.Config;
import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import com.tistory.iqpizza6349.database.MySQLDatabase;
import com.tistory.iqpizza6349.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        final TextChannel textChannel = ctx.getChannel();

        if (ctx.getStrings().isEmpty()) {
            textChannel.sendMessage("Correct usage is `" + Config.PREFIX + "play <youtube link>`").queue();
            return;
        }

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

        if (getQueueCount(ctx.getGuild().getIdLong()) >= 20) {
            textChannel.sendMessage("The maximum music cue is 20.\n" +
                    "Please wait until the current song ends!").queue();
            return;
        }

        String link = String.join(" ", ctx.getStrings());

        if (!isUrl(link)) {
            link = "ytsearch:" + link;
        }

        PlayerManager.getInstance()
                .loadAndPlay(textChannel, link);
    }

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public String getHelp() {
        return "Plays a song\n" +
                "Usage: `" + Config.PREFIX + "play <youtube link>`";
    }

    private boolean isUrl(String url) {
        try {
            new URL(url);
            return true;
        }catch (MalformedURLException e) {
            return false;
        }
    }

    public static int getQueueCount(long guildId) {
        try (final PreparedStatement preparedStatement = MySQLDatabase
                .getConnection()
                .prepareStatement("SELECT count FROM music WHERE guild_id = ?")) {

            preparedStatement.setString(1, String.valueOf(guildId));

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("count");
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return 0;
    }



}
