package dev.bunnyhop.forge.client;

import dev.bunnyhop.config.BunnyhopClientConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BunnyhopSettingsScreen extends Screen {
    private final Screen parent;
    private final List<Button> valueButtons = new ArrayList<>();

    public BunnyhopSettingsScreen(Screen parent) {
        super(Component.literal("Bunnyhop Settings"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.valueButtons.clear();

        int left = this.width / 2 - 155;
        int top = 36;
        int rowH = 22;

        addToggle(left, top, "Enable Bunnyhop", BunnyhopClientConfig.ENABLE_BUNNYHOP::get, BunnyhopClientConfig.ENABLE_BUNNYHOP::set);
        addToggle(left, top + rowH, "Scroll Down Jump", BunnyhopClientConfig.SCROLL_DOWN_JUMP::get, BunnyhopClientConfig.SCROLL_DOWN_JUMP::set);

        int y = top + rowH * 2 + 6;
        addDoubleRow(left, y, "Strafe Smoothing", BunnyhopClientConfig.STRAFE_SMOOTHING::get, BunnyhopClientConfig.STRAFE_SMOOTHING::set, 0.03, 0.0, 0.95);
        y += rowH;
        addDoubleRow(left, y, "Ground Acceleration", BunnyhopClientConfig.GROUND_ACCELERATION::get, BunnyhopClientConfig.GROUND_ACCELERATION::set, 0.01, 0.0, 1.0);
        y += rowH;
        addDoubleRow(left, y, "Air Acceleration", BunnyhopClientConfig.AIR_ACCELERATION::get, BunnyhopClientConfig.AIR_ACCELERATION::set, 0.01, 0.0, 1.0);
        y += rowH;
        addDoubleRow(left, y, "Air Control", BunnyhopClientConfig.AIR_CONTROL::get, BunnyhopClientConfig.AIR_CONTROL::set, 0.01, 0.0, 1.0);
        y += rowH;
        addDoubleRow(left, y, "Ground Friction", BunnyhopClientConfig.GROUND_FRICTION::get, BunnyhopClientConfig.GROUND_FRICTION::set, 0.01, 0.5, 1.0);
        y += rowH;
        addDoubleRow(left, y, "Momentum Retention", BunnyhopClientConfig.MOMENTUM_RETENTION::get, BunnyhopClientConfig.MOMENTUM_RETENTION::set, 0.002, 0.5, 1.0);
        y += rowH;
        addDoubleRow(left, y, "Max Air Speed", BunnyhopClientConfig.MAX_AIR_SPEED::get, BunnyhopClientConfig.MAX_AIR_SPEED::set, 0.02, 0.05, 2.0);
        y += rowH;
        addDoubleRow(left, y, "Max Ground Speed", BunnyhopClientConfig.MAX_GROUND_SPEED::get, BunnyhopClientConfig.MAX_GROUND_SPEED::set, 0.02, 0.05, 2.0);

        this.addRenderableWidget(Button.builder(Component.literal("Reset Defaults"), button -> {
            BunnyhopClientConfig.resetDefaults();
            if (this.minecraft != null) {
                this.minecraft.setScreen(new BunnyhopSettingsScreen(this.parent));
            }
        }).bounds(this.width / 2 - 155, this.height - 56, 150, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("Done"), button -> onClose())
                .bounds(this.width / 2 + 5, this.height - 56, 150, 20)
                .build());
    }

    private void addToggle(int x, int y, String name, Supplier<Boolean> getter, Consumer<Boolean> setter) {
        Button button = Button.builder(toggleText(name, getter.get()), b -> {
            setter.accept(!getter.get());
            b.setMessage(toggleText(name, getter.get()));
        }).bounds(x, y, 310, 20).build();
        this.addRenderableWidget(button);
    }

    private void addDoubleRow(int x, int y, String name, Supplier<Double> getter, Consumer<Double> setter, double step, double min, double max) {
        this.addRenderableWidget(Button.builder(Component.literal("-"), b -> {
            setter.accept(clamp(getter.get() - step, min, max));
            refreshLabels();
        }).bounds(x, y, 20, 20).build());

        Button valueButton = Button.builder(doubleText(name, getter.get()), b -> {
        }).bounds(x + 24, y, 242, 20).build();
        valueButton.active = false;
        this.valueButtons.add(valueButton);
        this.addRenderableWidget(valueButton);

        this.addRenderableWidget(Button.builder(Component.literal("+"), b -> {
            setter.accept(clamp(getter.get() + step, min, max));
            refreshLabels();
        }).bounds(x + 270, y, 40, 20).build());
    }

    private void refreshLabels() {
        int i = 0;
        valueButtons.get(i++).setMessage(doubleText("Strafe Smoothing", BunnyhopClientConfig.STRAFE_SMOOTHING.get()));
        valueButtons.get(i++).setMessage(doubleText("Ground Acceleration", BunnyhopClientConfig.GROUND_ACCELERATION.get()));
        valueButtons.get(i++).setMessage(doubleText("Air Acceleration", BunnyhopClientConfig.AIR_ACCELERATION.get()));
        valueButtons.get(i++).setMessage(doubleText("Air Control", BunnyhopClientConfig.AIR_CONTROL.get()));
        valueButtons.get(i++).setMessage(doubleText("Ground Friction", BunnyhopClientConfig.GROUND_FRICTION.get()));
        valueButtons.get(i++).setMessage(doubleText("Momentum Retention", BunnyhopClientConfig.MOMENTUM_RETENTION.get()));
        valueButtons.get(i++).setMessage(doubleText("Max Air Speed", BunnyhopClientConfig.MAX_AIR_SPEED.get()));
        valueButtons.get(i++).setMessage(doubleText("Max Ground Speed", BunnyhopClientConfig.MAX_GROUND_SPEED.get()));
    }

    private static Component toggleText(String label, boolean value) {
        return Component.literal(label + ": " + (value ? "ON" : "OFF"));
    }

    private static Component doubleText(String label, double value) {
        return Component.literal(label + ": " + String.format("%.3f", value));
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 14, 0xFFFFFF);
        graphics.drawCenteredString(this.font, Component.literal("Mods -> Bunnyhop -> Config"), this.width / 2, this.height - 28, 0xA0A0A0);
    }

    @Override
    public void onClose() {
        if (this.minecraft != null) {
            this.minecraft.setScreen(this.parent);
        }
    }
}
