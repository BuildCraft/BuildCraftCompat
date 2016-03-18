package buildcraft.compat.forestry.pipes;

import java.util.ArrayList;
import java.util.LinkedList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.BuildCraftCompat;
import buildcraft.api.core.IIconProvider;
import buildcraft.compat.CompatModuleForestry;
import buildcraft.gui.GuiIds;
import buildcraft.transport.BlockGenericPipe;
import buildcraft.transport.Pipe;
import buildcraft.transport.PipeTransportItems;
import buildcraft.transport.TransportConstants;
import buildcraft.transport.pipes.events.PipeEventItem;
import forestry.api.apiculture.IBee;
import forestry.api.genetics.IAllele;

public class PipeItemsPropolis extends Pipe<PipeTransportItems> {

	public final PipeLogicPropolis pipeLogic;

	public PipeItemsPropolis(Item item) {
		super(new PipeTransportItems(), item);
		pipeLogic = new PipeLogicPropolis(this);
		transport.allowBouncing = true;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		pipeLogic.readFromNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		pipeLogic.writeToNBT(nbt);
	}

	IIconProvider provider;

	@SideOnly(Side.CLIENT)
	@Override
	public IIconProvider getIconProvider() {
		if (provider == null) {
			provider = new PipeIconProvider();
		}

		return provider;
	}

	@Override
	public int getIconIndex(ForgeDirection direction) {
		if (direction == ForgeDirection.UNKNOWN) {
			return 0;
		}

		return direction.ordinal() + 1;
	}

	@Override
	public int getIconIndexForItem() {
		return 7;
	}

	@Override
	public boolean blockActivated(EntityPlayer player, ForgeDirection side) {
		if (getWorld().isRemote) {
			return true;
		}

		if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() != null) {
			if (Block.getBlockFromItem(player.getCurrentEquippedItem().getItem()) instanceof BlockGenericPipe) {
				return false;
			}
		}

		player.openGui(BuildCraftCompat.instance, GuiIds.PIPE_APIARIST, player.worldObj, container.xCoord, container.yCoord, container.zCoord);

		return true;
	}

	public void eventHandler(PipeEventItem.FindDest event) {
		LinkedList<ForgeDirection> filteredOrientations = new LinkedList<ForgeDirection>();
		LinkedList<ForgeDirection> typedOrientations = new LinkedList<ForgeDirection>();
		LinkedList<ForgeDirection> defaultOrientations = new LinkedList<ForgeDirection>();

		// We need a bee!
		EnumFilterType type = EnumFilterType.getType(event.item.getItemStack());
		IBee bee = null;

		if (type != EnumFilterType.ITEM) {
			bee = CompatModuleForestry.beeRoot.getMember(event.item.getItemStack());
		}

		// Filtered outputs
		for (ForgeDirection dir : event.destinations) {

			// Continue if this direction is closed.
			if (pipeLogic.isClosed(dir)) {
				continue;
			}

			if (pipeLogic.isIndiscriminate(dir)) {
				defaultOrientations.add(dir);
				continue;
			}

			// We need to match the type for this orientation's filter
			if (!pipeLogic.matchType(dir, type, bee)) {
				continue;
			}

			// Passing the type filter is enough for non-bee items.
			if (type == EnumFilterType.ITEM) {
				filteredOrientations.add(dir);
				continue;
			}

			ArrayList<IAllele[]> filters = pipeLogic.getGenomeFilters(dir);
			// If we have no genome filters, this is only a typed route.
			if (filters.size() <= 0) {
				typedOrientations.add(dir);
				continue;
			}

			// Bees need to match one of the genome filters
			for (IAllele[] pattern : filters) {
				if (matchAllele(pattern[0], bee.getIdent()) && matchAllele(pattern[1], bee.getGenome().getSecondary().getUID())) {
					filteredOrientations.add(dir);
				}
			}
		}

		event.destinations.clear();

		if (filteredOrientations.size() > 0) {
			event.destinations.addAll(filteredOrientations);
		} else if (typedOrientations.size() > 0) {
			event.destinations.addAll(typedOrientations);
		} else {
			event.destinations.addAll(defaultOrientations);
		}
	}

	private static boolean matchAllele(IAllele filter, String ident) {
		return filter == null || filter.getUID().equals(ident);
	}

	public void eventHandler(PipeEventItem.Entered event) {
		// A bit of speed to perhaps prevent bees from popping out of the pipe.
		try {
			event.item.setSpeed(TransportConstants.PIPE_DEFAULT_SPEED * 20F);
		} catch (Throwable ignored) {
		}
	}
}
