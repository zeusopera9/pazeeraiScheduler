package com.example.pazeeraiScheduler.discord;

import com.example.pazeeraiScheduler.service.PazeeraiSchedulerService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DiscordBotListener extends ListenerAdapter {

    @Autowired
    private PazeeraiSchedulerService schedulerService;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw().trim();
        String author = event.getAuthor().getName();

        if (event.getAuthor().isBot()) return;

        if (message.startsWith("!skip")) {
            String csv = message.substring(5).trim();
            var skippers = List.of(csv.split("\\s*,\\s*"));
            String assigned = schedulerService.scheduleWeek(skippers);
            event.getChannel().sendMessage("✅ Assigned this week: " + assigned).queue();
        }

        if (message.equalsIgnoreCase("!summary")) {
            String summary = schedulerService.getFormattedSummary();
            event.getChannel().sendMessage("📋 Summary: " + summary).queue();
        }

        if (message.equalsIgnoreCase("!help")) {
            String helpMessage = """
                🤖 **Available Commands:**
                `!skip XYZ,ABC` – Mark ABC and XYZ as unavailable for this week.
                `!summary` – Get current scheduling summary.
                `!help` – Show this help message.
                """;
            event.getChannel().sendMessage(helpMessage).queue();
        }
    }
}
