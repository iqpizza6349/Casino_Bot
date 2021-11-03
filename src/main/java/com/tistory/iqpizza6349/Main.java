package com.tistory.iqpizza6349;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.tistory.iqpizza6349.database.MySQLDatabase;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.apache.log4j.PropertyConfigurator;

import javax.security.auth.login.LoginException;
import java.sql.SQLException;

public class Main {

    public static final String BOT_TOKEN = Config.TOKEN;

    public static void main(String[] args) throws LoginException, InterruptedException, SQLException, ClassNotFoundException {
        PropertyConfigurator.configure("log4j.properties");

        WebUtils.setUserAgent("Mozilla/5.0 MenuDocs BOT_TEST#8047 / IQPIZZA6349#8983");
        MySQLDatabase.getConnection();

        EventWaiter waiter = new EventWaiter();

        JDA jda = JDABuilder
                .createDefault(BOT_TOKEN)
                .addEventListeners(new Listener(waiter), waiter)
                .build();
        jda.awaitReady();
    }
}
