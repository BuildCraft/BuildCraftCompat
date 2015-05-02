package buildcraft.compat.amt;

import mods.defeatedcrow.api.plants.IRightClickHarvestable;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import buildcraft.api.transport.IStripesActivator;
import buildcraft.api.transport.IStripesHandler;
import buildcraft.core.lib.inventory.SimpleInventory;

public class StripesHandlerAMTHarvest implements IStripesHandler {
	private static final IInventory fakeInventory = new SimpleInventory(9, "RightClickCapture", 64);

	@Override
	public StripesHandlerType getType() {
		return StripesHandlerType.BLOCK_BREAK;
	}

	@Override
	public boolean shouldHandle(ItemStack stack) {
		Block b = Block.getBlockFromItem(stack.getItem());
		return (b instanceof IRightClickHarvestable);
	}

	@Override
	public boolean handle(World world, int x, int y, int z, ForgeDirection dir, ItemStack itemStack, EntityPlayer entityPlayer, IStripesActivator activator) {
		IRightClickHarvestable harvestable = (IRightClickHarvestable) world.getBlock(x, y, z);
		if (harvestable.isHarvestable(world, x, y, z)) {
			harvestable.onHarvest(world, x, y, z, fakeInventory, null);
			for (int i = 0; i < 9; i++) {
				ItemStack stack = fakeInventory.getStackInSlot(i);
				if (stack != null) {
					activator.sendItem(stack, dir.getOpposite());
					fakeInventory.setInventorySlotContents(i, null);
				}
			}
		}
		return true;
	}
}
