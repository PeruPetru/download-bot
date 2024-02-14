package com.petrustoica;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.MessageCreateFields;
import discord4j.core.spec.MessageCreateSpec;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class DownloadMP4Command implements Command {
    //managing memory by not creating a string every time the command is sent
    private final String download_path = new String("/home/peru/yt-dlp/");
    private String output;
    private String content;
    private String url;

    @Override
    public void execute(MessageCreateEvent event) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        MessageChannel channel = event.getMessage().getChannel().block();
        content = event.getMessage().getContent();
        url = content.substring(content.indexOf(' ') + 1, content.length());
        Process process = processBuilder.command(
            "yt-dlp",
            "ytsearch1:" + url,
            "-f", "mp4",
            "-o", download_path + "%(id)s.mp4")
        .start();
        process.waitFor();

        if(process.exitValue() != 0) return;

        output = new String(process.getInputStream().readAllBytes());
        output = output.substring(output.indexOf(download_path) + download_path.length());
        output = output.substring(0, output.indexOf("."));
        channel.createMessage(MessageCreateSpec.builder().addFile(new MessageCreateFields.File() {
            @Override
            public String name() {
                return output + ".mp4";
            }

            @Override
            public InputStream inputStream() {
                try {
                    return new FileInputStream(download_path + output + ".mp4");
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }).build()).block();
    }
}
