package com.petrustoica;

import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface Command {
    void execute(MessageCreateEvent event) throws IOException, InterruptedException;
}
