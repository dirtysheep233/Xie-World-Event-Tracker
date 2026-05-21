package org.wet.world_event_tracker.components;

import org.wet.world_event_tracker.handlers.server.ServerMessageHandler;

public final class Handlers {
    public static final ServerMessageHandler ServerMessage = new ServerMessageHandler();
    public static void init() {
        ServerMessage.init();
    }
}