package com.tistory.iqpizza6349.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

public class GuildMusicManager {
    public final AudioPlayer player;
    public final TrackScheduler scheduler;

    private final AudioPlayerSendHandler handler;

    public GuildMusicManager(AudioPlayerManager manager) {
        this.player = manager.createPlayer();
        this.scheduler = new TrackScheduler(this.player);
        this.player.addListener(this.scheduler);
        this.handler = new AudioPlayerSendHandler(this.player);
    }

    public AudioPlayerSendHandler getHandler() {
        return handler;
    }
}
