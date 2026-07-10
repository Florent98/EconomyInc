package fr.fifoube.packets;

import fr.fifoube.main.economy.EconomyClientConfig;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketEconomyConfigSync {

    private final EconomyClientConfig config;

    public PacketEconomyConfigSync(EconomyClientConfig config) {
        this.config = config;
    }

    public static void encode(PacketEconomyConfigSync packet, FriendlyByteBuf buf) {
        buf.writeBoolean(packet.config.enableAtmWithdrawFee());
        buf.writeDouble(packet.config.atmWithdrawFeePercent());
        buf.writeDouble(packet.config.atmConfirmThreshold());
    }

    public static PacketEconomyConfigSync decode(FriendlyByteBuf buf) {
        return new PacketEconomyConfigSync(new EconomyClientConfig(
                buf.readBoolean(),
                buf.readDouble(),
                buf.readDouble()
        ));
    }

    public static void handle(PacketEconomyConfigSync packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (ctx.get().getDirection().getReceptionSide().isClient()) {
                handleClient(packet);
            }
        });
        ctx.get().setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void handleClient(PacketEconomyConfigSync packet) {
        ClientEconomyData.setConfig(packet.config);
    }
}
