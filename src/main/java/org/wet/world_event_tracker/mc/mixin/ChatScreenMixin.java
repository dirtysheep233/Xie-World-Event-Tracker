package org.wet.world_event_tracker.mc.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.util.ChatMessages;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.wet.world_event_tracker.mc.mixin.accessors.ChatHudAccessorInvoker;
import org.wet.world_event_tracker.utils.text.TextUtils;
import org.wet.world_event_tracker.utils.text.type.TextParseOptions;

import java.util.List;

import static org.lwjgl.glfw.GLFW.*;


@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin extends Screen {
    protected ChatScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void onMouseClicked(Click click, boolean doubled, CallbackInfoReturnable<Boolean> cir) {
        assert client != null;
        assert client.currentScreen != null;
        ChatHud chatHud = client.inGameHud.getChatHud();
        ChatHudAccessorInvoker chatHudAccessorInvoker = (ChatHudAccessorInvoker) chatHud;

        int chatBottom = client.currentScreen.height - 40;
        int chatWidth = chatHudAccessorInvoker.invokeGetWidth();
        double lineHeight =
                chatHudAccessorInvoker.invokeGetLineHeight() * MinecraftClient.getInstance().options.getChatScale()
                        .getValue(); //

        double scrollOffset = chatHudAccessorInvoker.getScrolledLines();
        if (this.keyPressed(new KeyInput(GLFW_KEY_LEFT_CONTROL,0, GLFW_KEY_LEFT_CONTROL)) || this.keyPressed(new KeyInput(GLFW_KEY_LEFT_SHIFT,0, GLFW_KEY_LEFT_SHIFT)) || this.keyPressed(new KeyInput(GLFW_KEY_LEFT_ALT,0, GLFW_KEY_LEFT_ALT))) {
            List<ChatHudLine> messages = chatHudAccessorInvoker.getMessages();
            int line = 0;
            for (ChatHudLine message : messages) {
                if (line > chatHud.getVisibleLineCount() + scrollOffset) break;

                int lines = ChatMessages.breakRenderedChatMessageLines(message.content(), chatWidth, textRenderer)
                        .size();
                if (line >= scrollOffset) {
                    if (click.x() <= chatWidth && click.y() <= chatBottom - lineHeight * (line - scrollOffset) && click.y() >= chatBottom - lineHeight * (line + lines - scrollOffset)) {
                        if (this.keyPressed(new KeyInput(GLFW_KEY_LEFT_CONTROL,0, GLFW_KEY_LEFT_CONTROL))) {
                            MinecraftClient.getInstance().keyboard.setClipboard(
                                    TextUtils.parsePlain(message.content()));
                        }
                        if (this.keyPressed(new KeyInput(GLFW_KEY_LEFT_ALT,0, GLFW_KEY_LEFT_ALT))) {
                            MinecraftClient.getInstance().keyboard.setClipboard(
                                    TextUtils.parseStyled(message.content(), TextParseOptions.DEFAULT));
                        }
                        if (this.keyPressed(new KeyInput(GLFW_KEY_LEFT_SHIFT,0, GLFW_KEY_LEFT_SHIFT))) {
                            MinecraftClient.getInstance().keyboard.setClipboard(message.content().toString());
                        }
                    }
               }
                line += lines;
            }
            cir.cancel();
        }
    }
}