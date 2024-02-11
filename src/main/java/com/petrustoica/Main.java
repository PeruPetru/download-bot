package com.petrustoica;

import discord4j.core.DiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;
public class Main {
    public static void main(String[] args) {
        DiscordClient discordClient = DiscordClient.create(System.getenv("DOWNLOAD_BOT_DISCORD_TOKEN"));
        discordClient.withGateway(client ->
            client.on(MessageCreateEvent.class, event -> {
                Message message = event.getMessage();
                return message.getChannel().flatMap(channel -> channel.createMessage(event.getMessage().getContent()));
            }))
            .block();

    }
}