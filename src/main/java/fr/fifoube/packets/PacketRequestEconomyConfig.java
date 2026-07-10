package fr.fifoube.packets;

import fr.fifoube.main.economy.EconomyConfigService;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketRequestEconomyConfig {

    public PacketRequestEconomyConfig() {
    }

    public static void encode(PacketRequestEconomyConfig packet, FriendlyByteBuf buf) {
    }

    public static PacketRequestEconomyConfig decode(FriendlyByteBuf buf) {
        return new PacketRequestEconomyConfig();
    }

    public static void handle(PacketRequestEconomyConfig packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                EconomyConfigService.syncToClient(player);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
