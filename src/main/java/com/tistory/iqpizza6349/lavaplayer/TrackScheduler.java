package com.tistory.iqpizza6349.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import com.tistory.iqpizza6349.command.commands.music.PlayCommand;
import com.tistory.iqpizza6349.database.MySQLDatabase;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter {
    public final AudioPlayer player;
    public final BlockingQueue<AudioTrack> queue;
    public boolean repeating = false;
    public long guildId;

    public TrackScheduler(AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
    }

    public void queue(AudioTrack audioTrack, long guildId) {
        if (!this.player.startTrack(audioTrack, true)) {
            // UPDATE CLEAR QUEUE AND UPDATE NEW QUEUE
            try (final PreparedStatement preparedStatement = MySQLDatabase
                    .getConnection()
                    .prepareStatement("UPDATE music SET queue = ? WHERE guild_id = ?")) {
                preparedStatement.setString(1, "NULL");
                preparedStatement.setString(2, String.valueOf(guildId));

                preparedStatement.executeUpdate();

                try (final PreparedStatement updateStatement = MySQLDatabase
                        .getConnection()
                        .prepareStatement("UPDATE music SET queue = ? WHERE guild_id = ?")) {
                    StringBuilder stringBuilder = new StringBuilder();
                    final int queueCount = PlayCommand.getQueueCount(guildId);
                    for (int i = 0; i <= queueCount; i++) {
                        stringBuilder.append(audioTrack.getInfo().uri)
                                .append(";");
                    }
                    this.queue.offer(audioTrack);
                    String sql = stringBuilder.toString();

                    updateStatement.setString(1, sql);
                    updateStatement.setString(2, String.valueOf(guildId));

                    updateStatement.executeUpdate();
                }

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            setCurrentSong(guildId, audioTrack.getInfo().uri);
        }
        putQueueCount(guildId, true);
    }

    public void nextTrack() {
        this.player.startTrack(this.queue.poll(), false);
        putQueueCount(getGuild(), false);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            if (this.repeating) {
                this.player.startTrack(track.makeClone(), false);
                return;
            }
            nextTrack();
        }
    }

    private void setCurrentSong(long guildId, String trackUrl)  {
        try (final PreparedStatement preparedStatement = MySQLDatabase.getConnection()
                .prepareStatement("UPDATE music SET current_song = ? WHERE guild_id = ?")) {

            preparedStatement.setString(1, String.valueOf(trackUrl));
            preparedStatement.setString(2, String.valueOf(guildId));

            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void putQueueCount(long guildId, boolean isPlus) {

        int queueCount = PlayCommand.getQueueCount(guildId);

        if (isPlus) {
            try (final PreparedStatement preparedStatement = MySQLDatabase.getConnection()
                    .prepareStatement("UPDATE music SET count = ? WHERE guild_id = ?")) {

                preparedStatement.setString(1, String.valueOf(++queueCount));
                preparedStatement.setString(2, String.valueOf(guildId));

                preparedStatement.executeUpdate();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            try (final PreparedStatement preparedStatement = MySQLDatabase.getConnection()
                    .prepareStatement("UPDATE music SET count = ? WHERE guild_id = ?")) {

                preparedStatement.setString(1, String.valueOf(--queueCount));
                preparedStatement.setString(2, String.valueOf(guildId));

                preparedStatement.executeUpdate();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void setGuildId(long guildId) {
        this.guildId = guildId;
    }

    private long getGuild() {
        return this.guildId;
    }

}
