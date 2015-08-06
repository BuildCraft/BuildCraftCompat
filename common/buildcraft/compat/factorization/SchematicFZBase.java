package buildcraft.compat.factorization;

import java.util.ArrayList;
import java.util.LinkedList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.api.blueprints.SchematicTile;
import buildcraft.compat.CompatModuleFactorization;
import buildcraft.compat.CompatUtils;
import factorization.common.FactoryType;
import factorization.servo.TileEntityServoRail;
import factorization.sockets.TileEntitySocketBase;

public class SchematicFZBase extends SchematicTile {
	protected static final int[] shiftMatrix = {0, 1, 5, 4, 2, 3, 6, 7};

	@Override
	public void rotateLeft(IBuilderContext context) {
		if (tileNBT != null) {
			if (tileNBT.hasKey("facing")) {
				tileNBT.setByte("facing", (byte) shiftMatrix[tileNBT.getByte("facing") & 7]);
			} else if (tileNBT.hasKey("side")) {
				tileNBT.setByte("side", (byte) shiftMatrix[tileNBT.getByte("side") & 7]);
			} else if (tileNBT.hasKey("fc")) {
				tileNBT.setByte("fc", (byte) shiftMatrix[tileNBT.getByte("fc") & 7]);
			} else if (tileNBT.hasKey("dir")) {
				tileNBT.setByte("dir", (byte) shiftMatrix[tileNBT.getByte("dir") & 7]);
			}
		}
	}

	@Override
	public void initializeFromObjectAt(IBuilderContext context, int x, int y, int z) {
		super.initializeFromObjectAt(context, x, y, z);
		if (tileNBT != null) {
			tileNBT.removeTag("draw_active_byte");

			// Furnacelikes
			tileNBT.removeTag("burnTime");
			tileNBT.removeTag("cookTime");

			// Energy
			tileNBT.removeTag("store");
			tileNBT.removeTag("storage");
			tileNBT.removeTag("charge");
			tileNBT.removeTag("heat");
			tileNBT.removeTag("progress");
			tileNBT.removeTag("prog");

			// Caliometric
			tileNBT.removeTag("digest");
			tileNBT.removeTag("food");
			tileNBT.removeTag("stomache");

			// Boiler/Turbine
			tileNBT.removeTag("steam");
			tileNBT.removeTag("water");

			// Mirror
			tileNBT.removeTag("targetx");
			tileNBT.removeTag("targety");
			tileNBT.removeTag("targetz");

			// Compression Crafter
			tileNBT.removeTag("rs");
			tileNBT.removeTag("root");
			tileNBT.removeTag("prog");

			// Sockets
			if (meta == 3) {
				tileNBT.removeTag("spd");
				tileNBT.removeTag("prg");
				tileNBT.removeTag("buf");
				tileNBT.removeTag("hsh");
				tileNBT.removeTag("chargecharge");

				tileNBT.removeTag("pow");

				tileNBT.removeTag("open");

				tileNBT.removeTag("wait");

				tileNBT.removeTag("fanw");
				tileNBT.removeTag("dropDelay");
				tileNBT.removeTag("buff");
				tileNBT.removeTag("auxBuff");
				tileNBT.removeTag("murderBuff");
				tileNBT.removeTag("drainTank");
				tileNBT.removeTag("floodTank");
			}

			if ("factory_battery".equals(tileNBT.getString("id"))) {
				tileNBT.setInteger("storage", 6400);
			}
		}
	}

	@Override
	public boolean isAlreadyBuilt(IBuilderContext context, int x, int y, int z) {
		if (super.isAlreadyBuilt(context, x, y, z)) {
			NBTTagCompound targetNBT = CompatUtils.getTileNBT(context.world(), x, y, z);
			return targetNBT.getByte("facing") == tileNBT.getByte("facing")
					&& targetNBT.getByte("side") == tileNBT.getByte("side")
					&& targetNBT.getByte("fc") == tileNBT.getByte("fc")
					&& targetNBT.getByte("dir") == tileNBT.getByte("dir");
		} else {
			return false;
		}
	}

	@Override
	public void getRequirementsForPlacement(IBuilderContext context, LinkedList<ItemStack> requirements) {
		if (!doNotBuild()) {
			super.getRequirementsForPlacement(context, requirements);
		}
	}

	@Override
	public void storeRequirements(IBuilderContext context, int x, int y, int z) {
		if (!doNotBuild()) {
			// Oh, FZ, I love thee so much...
			if (tileNBT != null) {
				if ("factory_battery".equals(tileNBT.getString("id"))) {
					storedRequirements = new ItemStack[1];
					storedRequirements[0] = new ItemStack(Item.getItemFromBlock(Block.getBlockFromName("factorization:charge_battery")), 1, 2);
					storedRequirements[0].setTagCompound(new NBTTagCompound());
					return;
				} else if ("factory_leyjar".equals(tileNBT.getString("id"))) {
					storedRequirements = new ItemStack[1];
					storedRequirements[0] = new ItemStack(Item.getItemFromBlock(Block.getBlockFromName("factorization:FzBlock")), 1, 26);
					return;
				}
			}
			if (meta == 3) {
				TileEntity tile = context.world().getTileEntity(x, y, z);
				if (tile instanceof TileEntitySocketBase) {
					ArrayList<ItemStack> reqs = new ArrayList<ItemStack>();
					TileEntitySocketBase socket;
					FactoryType type = ((TileEntitySocketBase) tile).getFactoryType();

					while (type != null) {
						socket = (TileEntitySocketBase) type.getRepresentative();
						if (socket != null && socket.getCreatingItem() != null) {
							reqs.add(socket.getCreatingItem());
						}
						type = socket.getParentFactoryType();
					}

					reqs.add(FactoryType.SOCKET_EMPTY.itemStack());

					storedRequirements = reqs.toArray(new ItemStack[reqs.size()]);
					return;
				}
			}
			if (meta == 9) {
				TileEntity tile = context.world().getTileEntity(x, y, z);

				if (tile instanceof TileEntityServoRail) {
					ArrayList<ItemStack> reqs = new ArrayList<ItemStack>();
					TileEntityServoRail rail = (TileEntityServoRail) tile;
					reqs.add(rail.getDroppedBlock());

					if (rail.getDecoration() != null && !rail.getDecoration().isFreeToPlace()) {
						reqs.add(rail.getDecoration().toItem());
					}

					storedRequirements = reqs.toArray(new ItemStack[reqs.size()]);
					return;
				}
			}
			super.storeRequirements(context, x, y, z);
		}
	}

	@Override
	public boolean doNotBuild() {
		if (tileNBT != null) {
			String id = tileNBT.getString("id");
			if ("factory_hinge".equals(id)) {
				return true;
			}
			if ("factory_rail".equals(id) && !CompatModuleFactorization.ENABLE_SERVO_RAILS) {
				return true;
			}
		}
		return false;
	}
}
