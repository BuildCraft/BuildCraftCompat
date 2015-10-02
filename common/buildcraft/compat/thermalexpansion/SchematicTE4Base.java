package buildcraft.compat.thermalexpansion;

import java.util.ArrayList;
import java.util.LinkedList;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldSettings;

import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.api.blueprints.SchematicTile;
import buildcraft.core.lib.inventory.StackHelper;

public class SchematicTE4Base extends SchematicTile {
	protected static final int[] shiftMatrix = {0, 1, 5, 4, 2, 3, 6, 7};
	private static final String[] checkedNBTTags = {
			"Level"
	};

	@Override
	public boolean isItemMatchingRequirement(ItemStack suppliedStack, ItemStack requiredStack) {
		Block b = Block.getBlockFromItem(requiredStack.getItem());
		if (b != null && b.isAssociatedBlock(block)) {
			if (StackHelper.isMatchingItem(suppliedStack, requiredStack, true, false)) {
				NBTTagCompound suppliedTag = suppliedStack.getTagCompound();
				NBTTagCompound requiredTag = requiredStack.getTagCompound();
				if (suppliedTag == null || requiredTag == null) {
					return false;
				}

				for (String s : checkedNBTTags) {
					if (suppliedTag.getTag(s) == null) {
						if (requiredTag.getTag(s) != null) {
							return false;
						}
					} else if (requiredTag.getTag(s) == null) {
						return false;
					} else if (!requiredTag.getTag(s).equals(suppliedTag.getTag(s))) {
						return false;
					}
				}

				return true;
			} else {
				return false;
			}
		} else {
			return super.isItemMatchingRequirement(suppliedStack, requiredStack);
		}
	}

	@Override
	public void rotateLeft(IBuilderContext context) {
		if (tileNBT != null) {
			if (tileNBT.hasKey("Facing")) {
				tileNBT.setByte("Facing", (byte) shiftMatrix[tileNBT.getByte("Facing") & 7]);
			}
			if (tileNBT.hasKey("SideCache")) {
				byte[] sideCache = tileNBT.getByteArray("SideCache");
				byte[] newSideCache = new byte[sideCache.length];
				for (int i = 0; i < sideCache.length; i++) {
					if (i < 6) {
						newSideCache[shiftMatrix[i]] = sideCache[i];
					} else {
						newSideCache[i] = sideCache[i];
					}
				}
				tileNBT.setByteArray("SideCache", newSideCache);
			}
		}
	}

	@Override
	public void placeInWorld(IBuilderContext context, int x, int y, int z, LinkedList<ItemStack> stacks) {
		this.setBlockInWorld(context, x, y, z);

		if(this.block.hasTileEntity(this.meta)) {
			this.tileNBT.setInteger("x", x);
			this.tileNBT.setInteger("y", y);
			this.tileNBT.setInteger("z", z);

			for (ItemStack s : stacks) {
				if (s != null) {
					Block b = Block.getBlockFromItem(s.getItem());
					if (b != null && b.isAssociatedBlock(block)) {
						NBTTagCompound nbt = s.getTagCompound();

						if (nbt != null && nbt.hasKey("Energy")) {
							tileNBT.setInteger("Energy", nbt.getInteger("Energy"));
						}

						if (nbt != null && nbt.hasKey("Augments")) {
							tileNBT.setTag("Augments", nbt.getTag("Augments"));
						} else if (context.world().getWorldInfo().getGameType() != WorldSettings.GameType.CREATIVE) {
							tileNBT.removeTag("Augments");
						}

						if (nbt != null && nbt.hasKey("Enchant")) {
							tileNBT.setByte("Enchant", nbt.getByte("Enchant"));
						} else if (context.world().getWorldInfo().getGameType() != WorldSettings.GameType.CREATIVE) {
							tileNBT.setByte("Enchant", (byte) 0);
						}
					}
				}
			}

			context.world().setTileEntity(x, y, z, TileEntity.createAndLoadEntity(this.tileNBT));
		}
	}

	protected void fixDualNBT(NBTTagCompound tileNBT) {
		if (tileNBT.hasKey("Owner")) {
			tileNBT.setString("Owner", "[None]");
			tileNBT.removeTag("OwnerUUID");
		}

		if (tileNBT.hasKey("Energy")) {
			tileNBT.setInteger("Energy", 0);
		}
	}

	@Override
	public void initializeFromObjectAt(IBuilderContext context, int x, int y, int z) {
		super.initializeFromObjectAt(context, x, y, z);
		if (tileNBT != null) {
			if (tileNBT.hasKey("RS", 10)) {
				tileNBT.getCompoundTag("RS").setByte("Power", (byte) 0);
			}

			if (tileNBT.hasKey("Active")) {
				tileNBT.setByte("Active", (byte) 0);
			}


			if (tileNBT.hasKey("Access")) {
				tileNBT.setByte("Access", (byte) 0);
			}

			fixDualNBT(tileNBT);
		}
	}

	@Override
	public void storeRequirements(IBuilderContext context, int x, int y, int z) {
		if (block != null) {
			ArrayList<ItemStack> rqs = block.getDrops(context.world(), x, y, z, meta, 0);
			storedRequirements = rqs.toArray(new ItemStack[rqs.size()]);
		}
		for (ItemStack s : storedRequirements) {
			if (s != null) {
				Block b = Block.getBlockFromItem(s.getItem());
				if (b.isAssociatedBlock(block) && s.getTagCompound() != null) {
					fixDualNBT(s.getTagCompound());
				}
			}
		}
	}
}
