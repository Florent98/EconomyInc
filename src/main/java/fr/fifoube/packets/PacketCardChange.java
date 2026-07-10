/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.packets;

import fr.fifoube.main.atm.AtmService;
import fr.fifoube.main.capabilities.CapabilityMoney;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketCardChange {

	private long amount;
	private boolean deposit;
	private String pin;
	private boolean confirmed;

	public PacketCardChange() {
	}

	public PacketCardChange(long amount, boolean deposit, String pin, boolean confirmed) {
		this.amount = amount;
		this.deposit = deposit;
		this.pin = pin == null ? "" : pin;
		this.confirmed = confirmed;
	}

	public static PacketCardChange decode(FriendlyByteBuf buf) {
		long amount = buf.readLong();
		boolean deposit = buf.readBoolean();
		String pin = buf.readUtf(16);
		boolean confirmed = buf.readBoolean();
		return new PacketCardChange(amount, deposit, pin, confirmed);
	}

	public static void encode(PacketCardChange packet, FriendlyByteBuf buf) {
		buf.writeLong(packet.amount);
		buf.writeBoolean(packet.deposit);
		buf.writeUtf(packet.pin == null ? "" : packet.pin, 16);
		buf.writeBoolean(packet.confirmed);
	}

	public static void handle(PacketCardChange packet, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Player player = ctx.get().getSender();
			if (!(player instanceof ServerPlayer serverPlayer)) {
				return;
			}
			if (!AtmService.isValidAmount(packet.amount)) {
				player.sendSystemMessage(Component.translatable("title.invalidAmount"));
				return;
			}
			if (!AtmService.canUseAtm(player)) {
				player.sendSystemMessage(Component.translatable("title.atmAccessDenied"));
				return;
			}
			player.getCapability(CapabilityMoney.MONEY_CAPABILITY, null).ifPresent(data -> {
				if (packet.deposit) {
					AtmService.deposit(serverPlayer, data, packet.amount, packet.pin);
				} else {
					AtmService.withdraw(serverPlayer, data, packet.amount, packet.pin, packet.confirmed);
				}
			});
		});
		ctx.get().setPacketHandled(true);
	}
}
