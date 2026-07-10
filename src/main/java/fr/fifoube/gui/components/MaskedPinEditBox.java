package fr.fifoube.gui.components;

import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.util.function.Predicate;

public class MaskedPinEditBox extends EditBox {

    private static final int DEFAULT_MAX = 4;

    private String secretValue = "";
    private int maxLength = DEFAULT_MAX;

    public MaskedPinEditBox(net.minecraft.client.gui.Font font, int x, int y, int width, int height, Component hint) {
        super(font, x, y, width, height, hint);
        super.setFilter(MaskedPinEditBox::acceptDisplayOrDigits);
    }

    private static boolean acceptDisplayOrDigits(String text) {
        if (text.isEmpty()) {
            return true;
        }
        if (text.chars().allMatch(ch -> ch == '*')) {
            return true;
        }
        return text.matches("\\d+");
    }

    @Override
    public void setMaxLength(int length) {
        this.maxLength = length;
        super.setMaxLength(length);
    }

    @Override
    public String getValue() {
        return this.secretValue;
    }

    @Override
    public void setValue(String value) {
        this.secretValue = value == null ? "" : value;
        refreshDisplay();
    }

    @Override
    public void setFilter(Predicate<String> validator) {
        super.setFilter(text -> acceptDisplayOrDigits(text)
                && (text.isEmpty() || text.chars().allMatch(ch -> ch == '*') || validator.test(text)));
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (!this.isFocused()) {
            return false;
        }
        if (codePoint >= '0' && codePoint <= '9' && this.secretValue.length() < this.maxLength) {
            this.secretValue += codePoint;
            refreshDisplay();
            return true;
        }
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (!this.isFocused()) {
            return false;
        }
        if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
            if (!this.secretValue.isEmpty()) {
                this.secretValue = this.secretValue.substring(0, this.secretValue.length() - 1);
                refreshDisplay();
            }
            return true;
        }
        if (keyCode == GLFW.GLFW_KEY_DELETE) {
            this.secretValue = "";
            refreshDisplay();
            return true;
        }
        return false;
    }

    private void refreshDisplay() {
        super.setValue("*".repeat(this.secretValue.length()));
    }
}
