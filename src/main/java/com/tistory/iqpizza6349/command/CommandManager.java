package com.tistory.iqpizza6349.command;

import com.tistory.iqpizza6349.command.commands.*;
import com.tistory.iqpizza6349.command.commands.Gamecommands.Todayluck;
import com.tistory.iqpizza6349.command.commands.Gamecommands.UpgradetheSword;
import com.tistory.iqpizza6349.command.commands.Gamecommands.sellcommand;
import com.tistory.iqpizza6349.command.commands.music.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CommandManager {

    private final List<ICommand> commandList = new ArrayList<>();

    public CommandManager() {
        addCommand(new PingCommand());  // 핑 명령어
        addCommand(new HelpCommand(this)); // 명령어 도움말
        addCommand(new PasteCommand()); // 일반적인 코드 전달 시, 디코로 편하게 보내기 위한 명령어
        addCommand(new HasteCommand()); // HTML 코드 전달 시, 디코로 편하게 보내기 위한 명령어
        addCommand(new KickCommand());  // 멤버 추방
        //addCommand(new WebhookCommand());   // 웹 훅
        addCommand(new sellcommand());
        addCommand(new UpgradetheSword());
        addCommand(new Nowmoney());
        addCommand(new Todayluck());

        addCommand(new SetPrefixCommand()); // 커스텀 prefix

        addCommand(new JoinCommand());  // 음악 봇 참가 명령어
        addCommand(new PlayCommand());
        addCommand(new StopCommand());
        addCommand(new SkipCommand());
        addCommand(new NowPlayingCommand());
        addCommand(new QueueCommand());
        addCommand(new RepeatCommand());
        addCommand(new LeaveCommand());
    }

    private void addCommand(ICommand cmd) {
        boolean nameFound = this.commandList.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(cmd.getName()));

        // 이미 추가된 명령어라면 오류를 뱉어냄
        if (nameFound) {
            throw new IllegalArgumentException("A Command with this name is already present");
        }

        commandList.add(cmd);
    }

    public List<ICommand> getCommandList() {
        return commandList;
    }

    @Nullable
    public ICommand getCommand(String search) {
        String searchLower = search.toLowerCase();

        for (ICommand cmd : this.commandList) {
            if (cmd.getName().equals(searchLower) || cmd.getAliases().contains(searchLower)) {
                return cmd;
            }
        }

        return null;
    }

    public void handle(GuildMessageReceivedEvent event, String prefix) {
        String[] split = event.getMessage().getContentRaw()
                .replaceFirst("(?i)" + Pattern.quote(prefix), "")
                .split("\\s+");

        String invoke = split[0].toLowerCase();
        ICommand cmd = this.getCommand(invoke);

        // 대충 대소문자와 무관하게 prefix뒤에 있는 명령어를 invoke에 넣고
        // invoke는 전부 소문자로 바꿔, 해당 명령어가 있는 지 확인

        // 예) .ping -> invoke에 ping을 담음(모든 문자를 소문자로 변환)
        // 해당 invoke를 실제 만든 명령어리스트안에 있다면 명령어 작동

        if (cmd != null) {
            event.getChannel().sendTyping().queue();
            List<String> strings = Arrays.asList(split).subList(1, split.length);

            CommandContext ctx = new CommandContext(event, strings);

            cmd.handle(ctx);
        }
    }


}
