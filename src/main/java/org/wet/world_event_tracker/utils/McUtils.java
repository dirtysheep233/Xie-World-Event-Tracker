package org.wet.world_event_tracker.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.util.ChatMessages;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.wet.world_event_tracker.World_event_tracker;
import org.wet.world_event_tracker.mc.mixin.accessors.ChatHudAccessorInvoker;
import org.wet.world_event_tracker.utils.text.TextUtils;
import org.wet.world_event_tracker.utils.type.Prepend;

public class McUtils {
    public static String devName = "Samuelblue123";

    public static String playerName() {
        if (World_event_tracker.isDevelopment()) return devName;
        if (player() != null) {
            return player().getName().getString();
        }
        return mc().getSession().getUsername();
    }

    public static PlayerEntity player() {
        return mc().player;
    }

    public static MinecraftClient mc() {
        return MinecraftClient.getInstance();
    }

    public static void sendLocalMessage(Text message, MutableText prepend, boolean wynncraftStyle) {
        if (player() == null) {
            World_event_tracker.LOGGER.error("Tried to send local message but player was null.");
            return;
        }
        ChatHud chatHud = MinecraftClient.getInstance().inGameHud.getChatHud();
        ChatHudAccessorInvoker chatHudAccessorInvoker = (ChatHudAccessorInvoker) chatHud;
        Text withPrepend = Text.empty().append(prepend).append(message);
        if (wynncraftStyle) withPrepend = TextUtils.toBlockMessage(withPrepend, prepend.getStyle());
        Prepend.linesSinceBadge += ChatMessages.breakRenderedChatMessageLines(withPrepend, chatHudAccessorInvoker.invokeGetWidth(), MinecraftClient.getInstance().textRenderer)
                .size();
        player().sendMessage(withPrepend, false);
    }
}
