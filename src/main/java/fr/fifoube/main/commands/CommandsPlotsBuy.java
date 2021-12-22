/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.main.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.main.capabilities.CapabilityMoney;
import fr.fifoube.world.saveddata.PlotsData;
import fr.fifoube.world.saveddata.PlotsWorldSavedData;
import net.minecraft.block.Blocks;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;

import java.util.UUID;

public class CommandsPlotsBuy {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {

        dispatcher.register(
                LiteralArgumentBuilder.<CommandSource>literal("plotbuy")
                        .then(
                                Commands.literal("buy")
                                        .requires(src -> src.hasPermissionLevel(0))
                                        .then(
                                                Commands.argument("plotname", StringArgumentType.string())
                                                        .executes(ctx -> requireBuy(ctx.getSource(), StringArgumentType.getString(ctx, "plotname")))
                                        )
                        )
        );
    }

    public static int requireBuy(CommandSource src, String plotName) {
        boolean canProceedBuy = false;
        int indexToProceedBuy = -1;
        ServerPlayerEntity player = null;

        try {
            player = src.asPlayer();
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        if (player != null) {
            String uuid = player.getUniqueID().toString();
            ServerWorld worldIn = player.getServerWorld();
            DimensionSavedDataManager storage = worldIn.getSavedData();
            PlotsWorldSavedData dataWorld = (PlotsWorldSavedData) storage.getOrCreate(PlotsWorldSavedData::new, PlotsWorldSavedData.DATA_NAME);
            if (dataWorld != null) {
                for (int i = 0; i < dataWorld.getListContainer().size(); i++) {
                    PlotsData plotsData = dataWorld.getListContainer().get(i);
                    if (plotsData != null)
                        if (plotsData.getList().get(0).equals(plotName)) {
                            boolean bought = plotsData.getBought();
                            if (!bought) {
                                indexToProceedBuy = i;
                                canProceedBuy = true;
                            } else {
                                src.sendFeedback(new TranslationTextComponent("commands.plotbuy.alreadybought"), false);
                            }
                        }
                }
            }
            if (canProceedBuy && indexToProceedBuy != -1) {
                ServerPlayerEntity s = player;
                PlotsData plotsData = dataWorld.getListContainer().get(indexToProceedBuy);
                player.getCapability(CapabilityMoney.MONEY_CAPABILITY, null).ifPresent(data -> {

                    double playerMoney = data.getMoney();
                    ModEconomyInc.LOGGER.info(s.getDisplayName().getString() + " has bought plot " + plotsData.name + ". Balance was at " + data.getMoney() + ", balance is now " + (data.getMoney() - plotsData.price) + "." + "[UUID: " + s.getUniqueID() + ",PlotID: " + plotsData.name + "]");
                    if (playerMoney >= plotsData.price) {
                        plotsData.bought = true;
                        plotsData.owner = uuid;
                        dataWorld.markDirty();
                        double newMoney = playerMoney - plotsData.price;
                        data.setMoney(newMoney);
                        replaceSign(worldIn, plotsData.xPosFirst, plotsData.yPos, plotsData.zPosFirst, plotsData.xPosSecond, plotsData.zPosSecond, plotsData.name, plotsData.owner);
                    }
                });
                src.sendFeedback(new TranslationTextComponent("commands.plotbuy.success"), false);
            }
        } else {
            src.sendFeedback(new TranslationTextComponent("commands.plot.noplayer"), false);
        }
        return 0;
    }

    public static Vector3d getCenter(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return new Vector3d(minX + (maxX - minX) * 0.5D, minY + (maxY - minY) * 0.5D, minZ + (maxZ - minZ) * 0.5D);
    }

    public static void replaceSign(ServerWorld worldIn, int xPosFirst, int yPos, int zPosFirst, int xPosSecond, int zPosSecond, String name, String owner) {
        PlayerEntity playerIn = worldIn.getPlayerByUuid(UUID.fromString(owner));
        if (playerIn != null) {
            Vector3d vec = getCenter(xPosFirst, yPos, zPosFirst, xPosSecond, yPos, zPosSecond);
            BlockPos posSign = new BlockPos(vec.x, vec.y, vec.z);
            worldIn.destroyBlock(posSign, false);
            worldIn.setBlockState(posSign, Blocks.OAK_SIGN.getDefaultState(), 2);

            TileEntity tileEntityIn = new SignTileEntity();
            tileEntityIn.validate();

            worldIn.setTileEntity(posSign, tileEntityIn);

            SignTileEntity signTe = (SignTileEntity) worldIn.getTileEntity(posSign);

            if (signTe != null) {
                signTe.setText(0, new StringTextComponent("[" + name + "]").mergeStyle(TextFormatting.BOLD).mergeStyle(TextFormatting.BLUE));
                signTe.setText(1, new StringTextComponent("Owned by").mergeStyle(TextFormatting.BOLD).mergeStyle(TextFormatting.BLACK));
                signTe.setText(2, new StringTextComponent(playerIn.getDisplayName().getString()).mergeStyle(TextFormatting.BOLD).mergeStyle(TextFormatting.BLACK));
                signTe.setText(3, new StringTextComponent("[SOLD]").mergeStyle(TextFormatting.BOLD).mergeStyle(TextFormatting.RED));
                signTe.markDirty();
            }
        }

    }


}
