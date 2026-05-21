package org.wet.world_event_tracker.handlers.chat.event;

import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.text.Text;

public class ChatMessageInit {
    public void init() {
        ClientReceiveMessageEvents.GAME.register(this::onMessage);
    }

    private void onMessage(Text text, boolean b) {
        ChatMessageReceived.EVENT.invoker().interact(text);
    }
}