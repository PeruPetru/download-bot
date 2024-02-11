package com.petrustoica;

import discord4j.core.DiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;
public class Main {
    public static void main(String[] args) {
        DiscordClient discordClient = DiscordClient.create("MTIwMjMzNTMyMDgyNjQ2NjQyNQ.G3_qWN.h5a0OCdposCuSwamSr56anPFUmO4k5o1slpAuM");
        discordClient.withGateway(client ->
            client.on(MessageCreateEvent.class, event -> {
                Message message = event.getMessage();
                return message.getChannel().flatMap(channel -> channel.createMessage(event.getMessage().getContent()));
            }))
            .block();

    }
}