package com.tistory.iqpizza6349;

import com.tistory.iqpizza6349.command.CommandManager;
import com.tistory.iqpizza6349.command.commands.game.FlipCoin;
import com.tistory.iqpizza6349.command.commands.game.OddAndEven;
import com.tistory.iqpizza6349.database.MySQLDatabase;
import me.duncte123.botcommons.BotCommons;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Listener extends ListenerAdapter {

    private final CommandManager manager = new CommandManager();

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        System.out.println(event.getJDA().getSelfUser().getAsTag() + " is Ready");
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        final long guildId = event.getGuild().getIdLong();
        String prefix = CustomPrefix.PREFIXES.computeIfAbsent(guildId, this::getPrefix);
        String msg = event.getMessage().getContentRaw();

        User user = event.getAuthor();

        if (user.isBot() || event.isWebhookMessage()) {
            return;
        }

        if (msg.equalsIgnoreCase(prefix + "shutdown") && event.getAuthor().getId().equals(Config.DEVELOPER)) {
            System.out.println("Shutting Down");
            event.getJDA().shutdown();
            BotCommons.shutdown(event.getJDA());

            return;
        }

        if (msg.startsWith(prefix)) {
            manager.handle(event, prefix);
        }
    }

    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {
        User user = event.getUser();
        TextChannel channel = event.getChannel();

        if (user.isBot()) {
            return;
        }

        if (FlipCoin.bettingUserMap.containsKey(channel.getGuild().getIdLong())) {
            if (FlipCoin.bettingUserMap.get(channel.getGuild().getIdLong()).containsKey(user.getIdLong())) {
                channel.sendMessage("You already bet to " + FlipCoin.bettingUserMap.get(channel.getGuild().getIdLong()).get(user.getIdLong())).queue();
            }
            else {
                if (event.getReactionEmote().getName().equals(FlipCoin.head)) {
                    FlipCoin.bettingUserMap.get(channel.getGuild().getIdLong()).put(user.getIdLong(), true);
                    channel.sendMessageFormat("#%s, you bet to head", user).queue();
                }
                else if (event.getReactionEmote().getName().equals(FlipCoin.tail)) {
                    FlipCoin.bettingUserMap.get(channel.getGuild().getIdLong()).put(user.getIdLong(), false);
                    channel.sendMessageFormat("#%s, you bet to tail", user).queue();
                }
            }
        }
        else if (OddAndEven.bettingUserMap.containsKey(channel.getGuild().getIdLong())) {
            if (OddAndEven.bettingUserMap.get(channel.getGuild().getIdLong()).containsKey(user.getIdLong())) {
                channel.sendMessage("You already bet to " + OddAndEven.bettingUserMap.get(channel.getGuild().getIdLong()).get(user.getIdLong())).queue();
            }
            else {
                if (event.getReactionEmote().getName().equals(OddAndEven.odd)) {
                    OddAndEven.bettingUserMap.get(channel.getGuild().getIdLong()).put(user.getIdLong(), true);
                    channel.sendMessageFormat("#%s, you bet to odd", user).queue();
                }
                else if (event.getReactionEmote().getName().equals(OddAndEven.even)) {
                    OddAndEven.bettingUserMap.get(channel.getGuild().getIdLong()).put(user.getIdLong(), false);
                    channel.sendMessageFormat("#%s, you bet to even", user).queue();
                }
            }
        }
    }

    private String getPrefix(long guildId) {
        try (final PreparedStatement preparedStatement = MySQLDatabase
                .getConnection()
                .prepareStatement("SELECT prefix FROM guild_settings WHERE guild_id = ?")) {

            preparedStatement.setString(1, String.valueOf(guildId));

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("prefix");
                }
            }

            try (final PreparedStatement insertStatement = MySQLDatabase
                    .getConnection()
                    .prepareStatement("INSERT INTO guild_settings(guild_id) VALUES(?)")) {

                insertStatement.setString(1, String.valueOf(guildId));

                insertStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return Config.PREFIX;
    }
}
