package com.tistory.iqpizza6349.command.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import com.tistory.iqpizza6349.Config;
import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import com.tistory.iqpizza6349.lavaplayer.GuildMusicManager;
import com.tistory.iqpizza6349.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class QueueCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        final TextChannel textChannel = ctx.getChannel();
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final BlockingQueue<AudioTrack> audioQueue = musicManager.scheduler.queue;

        if (audioQueue.isEmpty()) {
            textChannel.sendMessage("The queue is empty!").queue();
            return;
        }

        final int trackCount = Math.min(audioQueue.size(), 20);
        final List<AudioTrack> audioTracks = new ArrayList<>(audioQueue);
        final MessageAction messageAction = textChannel.sendMessage("**Current Queue**\n");

        for (int i = 0; i < trackCount; i++) {
            final AudioTrack track = audioTracks.get(i);
            final AudioTrackInfo trackInfo = track.getInfo();

            messageAction.append('#')
                    .append(String.valueOf(i + 1))
                    .append(" `")
                    .append(trackInfo.title)
                    .append(" by ")
                    .append(trackInfo.author)
                    .append("` [`")
                    .append(formatTime(track.getDuration()))
                    .append("`]\n");
        }

        if (audioTracks.size() > trackCount) {
            messageAction.append("And ")
                    .append(String.valueOf(audioQueue.size() - trackCount))
                    .append("` more...`");
        }

        messageAction.queue();
    }

    private String formatTime(long duration) {
        final long hours = duration / TimeUnit.HOURS.toMillis(1);
        final long minutes = duration / TimeUnit.MINUTES.toMillis(1);
        final long seconds = duration % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d%02d", hours, minutes, seconds);
    }

    @Override
    public String getName() {
        return "queue";
    }

    @Override
    public String getHelp() {
        return "Shows the queued up songs\n" +
                "Usage: `" + Config.PREFIX + "queue`";
    }
}
