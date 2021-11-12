package com.tistory.iqpizza6349.command.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.tistory.iqpizza6349.Config;
import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import com.tistory.iqpizza6349.lavaplayer.GuildMusicManager;
import com.tistory.iqpizza6349.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class LeaveCommand implements ICommand {

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

        final Guild guild = ctx.getGuild();

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);

        musicManager.scheduler.repeating = false;
        musicManager.scheduler.queue.clear();
        musicManager.player.stopTrack();

        final AudioManager audioManager = ctx.getGuild().getAudioManager();
        audioManager.closeAudioConnection();
        textChannel.sendMessage("I have left the voice channel").queue();
    }

    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getHelp() {
        return "Leaves the voice channel that the bot is in\n"+
                "Usage: `" + Config.PREFIX + "leave`";
    }
}
