package com.example.pazeeraiScheduler.discord;

import jakarta.annotation.PostConstruct;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.EnumSet;

@Configuration
public class DiscordBotConfig {

    @Value("${discord.bot.token}")
    private String botToken;

    private final DiscordBotListener listener;

    public DiscordBotConfig(DiscordBotListener listener) {
        this.listener = listener;
    }

    @PostConstruct
    public void startBot() throws Exception {
        JDABuilder.createDefault(botToken)
                .enableIntents(EnumSet.of(
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.MESSAGE_CONTENT   // ✅ This is the fix
                ))
                .addEventListeners(listener)
                .build();
    }
}
