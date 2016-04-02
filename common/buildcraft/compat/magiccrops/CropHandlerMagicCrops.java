package buildcraft.compat.magiccrops;

import java.util.List;

import buildcraft.api.crops.CropManager;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.api.core.BuildCraftAPI;
import buildcraft.api.crops.ICropHandler;
import buildcraft.core.proxy.CoreProxy;

public class CropHandlerMagicCrops implements ICropHandler {

	@Override
	public boolean isSeed(ItemStack stack) {
        return false;
	}

	@Override
	public boolean canSustainPlant(World world, ItemStack seed, int x, int y, int z) {
        return false;
	}

	@Override
	public boolean plantCrop(World world, EntityPlayer player, ItemStack seed, int x, int y, int z) {
        return false;

	}

	@Override
	public boolean isMature(IBlockAccess blockAccess, Block block, int meta, int x, int y, int z) {
		String temp =  Block.blockRegistry.getNameForObject(block);
		return  temp.contains("magicalcrops:") && temp.endsWith("Crop") && meta == 7;
	}

	@Override
	public boolean harvestCrop(World world, int x, int y, int z, List<ItemStack> drops) {
        return CropManager.getDefaultHandler().harvestCrop(world, x, y, z, drops);
	}

}