package buildcraft.compat.forestry;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import buildcraft.api.crops.ICropHandler;

import forestry.api.arboriculture.EnumGermlingType;
import forestry.api.arboriculture.ITree;
import forestry.api.arboriculture.ITreeRoot;
import forestry.api.genetics.AlleleManager;

public class CropHandlerForestry implements ICropHandler {
	private static ITreeRoot treeRoot;

	public static void postInit() {
		treeRoot = (ITreeRoot) AlleleManager.alleleRegistry.getSpeciesRoot("rootTrees");
	}

	@Override
	public boolean isSeed(ItemStack itemStack) {
		if (treeRoot.getType(itemStack) != EnumGermlingType.SAPLING) {
			return false;
		}
		ITree tree = treeRoot.getMember(itemStack);
		return tree != null;
	}

	@Override
	public boolean canSustainPlant(World world, ItemStack itemStack, int x, int y, int z) {
		if (world.isAirBlock(x, y + 1, z) && isSeed(itemStack)) {
			ITree tree = treeRoot.getMember(itemStack);
			return tree != null && tree.canStay(world, x, y + 1, z);
		} else {
			return false;
		}
	}

	@Override
	public boolean plantCrop(World world, EntityPlayer entityPlayer, ItemStack itemStack, int x, int y, int z) {
		if (world.isAirBlock(x, y + 1, z) && isSeed(itemStack)) {
			if (treeRoot.plantSapling(world, treeRoot.getMember(itemStack), null, x, y + 1, z)) {
				itemStack.stackSize--;
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isMature(IBlockAccess iBlockAccess, Block block, int i, int i1, int i2, int i3) {
		return false;
	}

	@Override
	public boolean harvestCrop(World world, int i, int i1, int i2, List<ItemStack> list) {
		return false;
	}
}
