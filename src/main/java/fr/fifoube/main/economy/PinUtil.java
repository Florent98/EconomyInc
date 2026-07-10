package fr.fifoube.main.economy;

import net.minecraft.world.item.ItemStack;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public final class PinUtil {

    private PinUtil() {
    }

    public static boolean isValidPinFormat(String pin) {
        return pin != null && pin.matches("\\d{4}");
    }

    public static String hashPin(String pin, String ownerUuid) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest((ownerUuid + ":" + pin).getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    public static boolean isPinEnabled(ItemStack card) {
        return card.hasTag() && card.getTag().getBoolean("PinEnabled");
    }

    public static boolean verifyPin(ItemStack card, String pin, String ownerUuid) {
        if (!isPinEnabled(card)) {
            return true;
        }
        if (!isValidPinFormat(pin)) {
            return false;
        }
        String expected = card.getTag().getString("PinHash");
        return expected.equals(hashPin(pin, ownerUuid));
    }
}
