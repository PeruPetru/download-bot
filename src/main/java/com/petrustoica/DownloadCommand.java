package com.petrustoica;

import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

import java.io.IOException;

public class DownloadCommand implements Command {
    //managing memory by not creating a string every time the command is sent
    private final String download_path = "/home/peru/yt-dlp/%(title)s.mp3";
    private String content;
    private String url;

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        content = event.getMessage().getContent();
        url = content.substring(content.indexOf(' ') + 1, content.length() - 1);
        int exitCode;
        try {
            exitCode = processBuilder.command(
                    "yt-dlp",
                    "--extract-audio",
                    "--audio-format", "mp3",
                    "--audio-quality", "0",
                    "--output", download_path,
                    url
            ).start().exitValue();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        event.getMessage().getChannel().block().createMessage(String.valueOf(exitCode)).block();
        return null;
    }
}
