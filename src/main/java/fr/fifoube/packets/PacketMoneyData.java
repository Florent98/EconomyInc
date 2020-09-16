/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.packets;

import java.util.function.Supplier;

import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.main.capabilities.CapabilityMoney;
import fr.fifoube.main.capabilities.IMoney;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketMoneyData {

	private double money;

	
	public PacketMoneyData(IMoney instance) {
		this.money = instance.getMoney();
	}
	
	public PacketMoneyData(double money)
	{
		this.money = money;
	}
    
    public static void encode(PacketMoneyData pck, PacketBuffer buf)
    {
        buf.writeDouble(pck.money);
    }
 
    public static PacketMoneyData decode(PacketBuffer buf)
    {
        return new PacketMoneyData(buf.readDouble());
    }
 
    public static void handle(PacketMoneyData pck, Supplier<NetworkEvent.Context> ctxSupplier)
    {
        if(ctxSupplier.get().getDirection().getReceptionSide() == LogicalSide.CLIENT)
            ctxSupplier.get().enqueueWork(() -> handleClientUpdate(pck));
        ctxSupplier.get().setPacketHandled(true);
    }
 
    @OnlyIn(Dist.CLIENT)
    private static void handleClientUpdate(PacketMoneyData pck)
    {
        Minecraft.getInstance().player.getCapability(CapabilityMoney.MONEY_CAPABILITY)
                .ifPresent(capa -> {
                	capa.setMoney(pck.money);
                }
                );
    }
}