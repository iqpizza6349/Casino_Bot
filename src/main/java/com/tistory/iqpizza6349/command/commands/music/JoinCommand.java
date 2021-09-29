package com.tistory.iqpizza6349.command.commands.music;

import com.tistory.iqpizza6349.Config;
import com.tistory.iqpizza6349.CustomPrefix;
import com.tistory.iqpizza6349.Listener;
import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;

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
}
