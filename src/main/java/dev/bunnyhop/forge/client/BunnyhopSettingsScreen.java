package dev.bunnyhop.forge.client;

import dev.bunnyhop.config.BunnyhopClientConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class BunnyhopSettingsScreen extends Screen {
    private final Screen parent;

    public BunnyhopSettingsScreen(Screen parent) {
        super(Component.literal("Bunnyhop Settings"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int x = this.width / 2 - 120;
        int y = this.height / 4;
        int w = 240;

        this.addRenderableWidget(Button.builder(enableText(), button -> {
            boolean next = !BunnyhopClientConfig.ENABLE_BUNNYHOP.get();
            BunnyhopClientConfig.ENABLE_BUNNYHOP.set(next);
            button.setMessage(enableText());
        }).bounds(x, y, w, 20).build());

        this.addRenderableWidget(Button.builder(scrollText(), button -> {
            boolean next = !BunnyhopClientConfig.SCROLL_DOWN_JUMP.get();
            BunnyhopClientConfig.SCROLL_DOWN_JUMP.set(next);
            button.setMessage(scrollText());
        }).bounds(x, y + 24, w, 20).build());

        this.addRenderableWidget(Button.builder(smoothingText(), button -> {
            double current = BunnyhopClientConfig.STRAFE_SMOOTHING.get();
            double next = current + (hasShiftDown() ? -0.03D : 0.03D);
            if (next > 0.95D) {
                next = 0.0D;
            }
            if (next < 0.0D) {
                next = 0.95D;
            }
            BunnyhopClientConfig.STRAFE_SMOOTHING.set(next);
            button.setMessage(smoothingText());
        }).bounds(x, y + 48, w, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("Done"), button -> {
            if (this.minecraft != null) {
                this.minecraft.setScreen(this.parent);
            }
        }).bounds(x, y + 90, w, 20).build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
        graphics.drawCenteredString(this.font, Component.literal("Shift + click on smoothing = decrease"), this.width / 2, 130, 0xA0A0A0);
    }

    @Override
    public void onClose() {
        if (this.minecraft != null) {
            this.minecraft.setScreen(this.parent);
        }
    }

    private static Component enableText() {
        return Component.literal("Enable Bunnyhop: " + (BunnyhopClientConfig.ENABLE_BUNNYHOP.get() ? "ON" : "OFF"));
    }

    private static Component scrollText() {
        return Component.literal("Scroll Down Jump: " + (BunnyhopClientConfig.SCROLL_DOWN_JUMP.get() ? "ON" : "OFF"));
    }

    private static Component smoothingText() {
        return Component.literal("Strafe Smoothing: " + String.format("%.2f", BunnyhopClientConfig.STRAFE_SMOOTHING.get()));
    }
}
