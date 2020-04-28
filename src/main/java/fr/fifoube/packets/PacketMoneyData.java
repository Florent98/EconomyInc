/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.packets;

import java.util.function.Supplier;

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
	private boolean linked;

	
	public PacketMoneyData(IMoney instance) {
		this.money = instance.getMoney();
		this.linked = instance.getLinked();
	}
	
    public PacketMoneyData(double money, boolean linked)
    {
        this.money = money;
		this.linked = linked;
    }
    
    public static void encode(PacketMoneyData pck, PacketBuffer buf)
    {
        buf.writeDouble(pck.money);
        buf.writeBoolean(pck.linked);
    }
 
    public static PacketMoneyData decode(PacketBuffer buf)
    {
        return new PacketMoneyData(buf.readDouble(), buf.readBoolean());
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
                	capa.setLinked(pck.linked);
                	}
                );
    }
}