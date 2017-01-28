package buildcraft.compat.agricraft;

import java.util.List;

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

public class CropHandlerAgriCraft implements ICropHandler {

	@Override
	public boolean isSeed(ItemStack stack) {
		return stack.getItem() instanceof IPlantable;
	}

	@Override
	public boolean canSustainPlant(World world, ItemStack seed, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		return block.getUnlocalizedName().equals("tile.agricraft:crops") && meta == 0;
	}

	@Override
	public boolean plantCrop(World world, EntityPlayer player, ItemStack seed, int x, int y, int z) {
		player.setCurrentItemOrArmor(0, seed);
		boolean result = world.getBlock(x, y, z).onBlockActivated(world, x, y, z, player,
				ForgeDirection.UP.ordinal(), 0.5f, 0.5f, 0.5f);
		player.setCurrentItemOrArmor(0, null);
		return result;

	}

	@Override
	public boolean isMature(IBlockAccess blockAccess, Block block, int meta, int x, int y, int z) {
		if (block.getUnlocalizedName().equals("tile.agricraft:crops")) {
			return meta >= 7;
		}
		return false;
	}

	@Override
	public boolean harvestCrop(World world, int x, int y, int z, List<ItemStack> drops) {
		EntityPlayer player = BuildCraftAPI.proxy.getBuildCraftPlayer((WorldServer) world).get();
		return world.getBlock(x, y, z).onBlockActivated(world, x, y, z, player,
				ForgeDirection.UP.ordinal(), 0.5f, 0.5f, 0.5f);
	}

}
