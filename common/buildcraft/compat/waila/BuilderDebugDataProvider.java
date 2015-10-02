package buildcraft.compat.waila;

import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import buildcraft.api.blueprints.BuildingPermission;
import buildcraft.api.blueprints.SchematicBlock;
import buildcraft.core.blueprints.SchematicRegistry;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;

public class BuilderDebugDataProvider implements IWailaDataProvider {
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		if (accessor.getBlock() != null && accessor.getBlock() != Blocks.air) {
			SchematicBlock b = SchematicRegistry.INSTANCE.createSchematicBlock(accessor.getBlock(), accessor.getMetadata());
			if (b != null && b.getBuildingPermission() != BuildingPermission.NONE) {
				currenttip.add(b.getBuildingPermission() != BuildingPermission.CREATIVE_ONLY ?
						EnumChatFormatting.GREEN + "Supported by Builder"
						: EnumChatFormatting.RED + "Not supported by Builder (Creative Only)");
			} else {
				currenttip.add(EnumChatFormatting.RED + "Not supported by Builder");
			}
		}
		return currenttip;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {
		return tag;
	}
}
