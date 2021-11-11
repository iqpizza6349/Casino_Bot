package com.tistory.iqpizza6349.command.commands;

import com.tistory.iqpizza6349.Config;
import com.tistory.iqpizza6349.CustomPrefix;
import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import com.tistory.iqpizza6349.database.MySQLDatabase;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SetPrefixCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final List<String> strings = ctx.getStrings();
        final Member member = ctx.getMember();

        if (!member.hasPermission(Permission.MANAGE_SERVER)) {
            channel.sendMessage("You must have the Manage Server permission to use his command").queue();
            channel.sendMessage("해당 명령어를 사용하기 위해서는 관리자 권한이 요구됩니다.").queue();
            return;
        }

        if (strings.isEmpty()) {
            channel.sendMessage("Missing Arguments").queue();
            return;
        }

        final String newPrefix = String.join("", strings);
        updatePrefix(ctx.getGuild().getIdLong(), newPrefix);

        channel.sendMessageFormat("New prefix has been set to `%s`", newPrefix).queue();
        channel.sendMessageFormat("`%s`로 prefix를 변경하였습니다.", newPrefix).queue();
    }

    @Override
    public String getName() {
        return "setprefix";
    }

    @Override
    public String getHelp() {
        return "Sets the prefix for this server\n" +
                "Usage: `" + Config.PREFIX + "setprefix <prefix>`";
    }

    private void updatePrefix(long guildId, String newPrefix) {
        CustomPrefix.PREFIXES.put(guildId, newPrefix);

        try (final PreparedStatement preparedStatement = MySQLDatabase.getConnection()
                .prepareStatement("UPDATE guild_settings SET prefix = ? WHERE guild_id = ?")) {

            preparedStatement.setString(1, newPrefix);
            preparedStatement.setString(2, String.valueOf(guildId));

            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
