package com.petrustoica;

import discord4j.core.DiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
    
    private static final Map<String, Command> commandMap = new HashMap<>();
    
    public static void main(String[] args) {
        commandMap.put("mp3", new DownloadCommand());


        try {
            DiscordClient discordClient = DiscordClient.create(System.getenv("DOWNLOAD_BOT_DISCORD_TOKEN"));
            discordClient.withGateway(client ->
                client.on(MessageCreateEvent.class, event -> {
                    Message message = event.getMessage();
                    if(message.getUserData().username().equals(discordClient.getUserService().getCurrentUser().block().username())){
                        if(message.getContent().startsWith("mp3")){
                            return commandMap.get("mp3").execute(event);
                        }
                        return Mono.empty();
                    }else{
                        return Mono.empty();
                        }
                }))
                    .block();
        }catch (Exception e){
            System.out.println("Failed to get Token");
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}