package com.petrustoica;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    
    private static final Map<String, Command> commandMap = new HashMap<>();
    
        static{
            commandMap.put("mp3", new DownloadCommand());
        }
    public static void main(String[] args) {

        GatewayDiscordClient client = DiscordClientBuilder.create(System.getenv("DOWNLOAD_BOT_DISCORD_TOKEN")).build()
                    .login()
                    .block();

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .subscribe(event -> {
                Message message = event.getMessage();
                if(!message.getUserData().username().equals(client.getSelf().block().getUsername())){
                    if(message.getContent().startsWith("mp3")){
                        try {
                            commandMap.get("mp3").execute(event);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });

        client.onDisconnect().block();
    }
}