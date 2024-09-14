/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.packets;

import fr.fifoube.main.ModEconomyInc;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;


public class PacketsRegistery {

	public static final String PROTOCOL_VERSION = String.valueOf(1);
	
	public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(ModEconomyInc.MOD_ID, "packets_ei")).networkProtocolVersion(() -> PROTOCOL_VERSION)
			.clientAcceptedVersions(PROTOCOL_VERSION::equals).serverAcceptedVersions(PROTOCOL_VERSION::equals).simpleChannel();
	
	public static void registerNetworkPackets() {

		CHANNEL.messageBuilder(PacketListNBT.class, 1).encoder(PacketListNBT::encode).decoder(PacketListNBT::decode).consumerMainThread(PacketListNBT::handle).add();
		CHANNEL.messageBuilder(PacketMoneyData.class, 2).encoder(PacketMoneyData::encode).decoder(PacketMoneyData::decode).consumerMainThread(PacketMoneyData::handle).add();
		CHANNEL.messageBuilder(PacketSellerCreated.class, 3).encoder(PacketSellerCreated::encode).decoder(PacketSellerCreated::decode).consumerMainThread(PacketSellerCreated::handle).add();
		CHANNEL.messageBuilder(PacketSellerFundsTotal.class, 4).encoder(PacketSellerFundsTotal::encode).decoder(PacketSellerFundsTotal::decode).consumerMainThread(PacketSellerFundsTotal::handle).add();
		CHANNEL.messageBuilder(PacketCardChange.class, 5).encoder(PacketCardChange::encode).decoder(PacketCardChange::decode).consumerMainThread(PacketCardChange::handle).add();
		CHANNEL.messageBuilder(PacketChangerUpdate.class, 6).encoder(PacketChangerUpdate::encode).decoder(PacketChangerUpdate::decode).consumerMainThread(PacketChangerUpdate::handle).add();
		CHANNEL.messageBuilder(PacketVaultSettings.class, 7).encoder(PacketVaultSettings::encode).decoder(PacketVaultSettings::decode).consumerMainThread(PacketVaultSettings::handle).add();
		CHANNEL.messageBuilder(PacketBuyerCreation.class, 8).encoder(PacketBuyerCreation::encode).decoder(PacketBuyerCreation::decode).consumerMainThread(PacketBuyerCreation::handle).add();
		CHANNEL.messageBuilder(PacketBuyerChange.class, 9).encoder(PacketBuyerChange::encode).decoder(PacketBuyerChange::decode).consumerMainThread(PacketBuyerChange::handle).add();
		CHANNEL.messageBuilder(PacketRefillSeller.class, 10).encoder(PacketRefillSeller::encode).decoder(PacketRefillSeller::decode).consumerMainThread(PacketRefillSeller::handle).add();

	}



	
}
