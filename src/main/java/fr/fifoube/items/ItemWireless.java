/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class ItemWireless extends Item {

    public ItemWireless(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        ItemStack stackInHand = playerIn.getHeldItem(handIn);
        ItemStack stackCard = ItemStack.EMPTY;
        boolean keepSearching = true;
        for (int i = 0; i < playerIn.inventory.getSizeInventory(); i++) {

            if (keepSearching) {
                if (playerIn.inventory.getStackInSlot(i).getItem().equals(ItemsRegistery.ITEM_CREDITCARD)) {
                    if (playerIn.inventory.getStackInSlot(i).hasTag() && playerIn.inventory.getStackInSlot(i).getTag().getString("OwnerUUID").equals(playerIn.getUniqueID().toString()) && !playerIn.inventory.getStackInSlot(i).getTag().getBoolean("Linked")) {
                        keepSearching = false;
                        stackCard = playerIn.inventory.getStackInSlot(i);
                    }
                }
            }
        }
        if (stackCard != ItemStack.EMPTY) {

            playerIn.inventory.deleteStack(stackInHand);
            stackCard.getTag().putBoolean("Linked", true);
            return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stackInHand);

        }
        return new ActionResult<ItemStack>(ActionResultType.FAIL, stackInHand);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent(I18n.format("title.wireless")));
    }
}
