package buildcraft.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.IGuiHandler;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.IApiaristTracker;

import buildcraft.compat.CompatModuleForestry;
import buildcraft.compat.forestry.pipes.PipeHelper;
import buildcraft.compat.forestry.pipes.PipeItemsPropolis;
import buildcraft.compat.forestry.pipes.gui.ContainerPropolisPipe;
import buildcraft.compat.forestry.pipes.gui.GuiPropolisPipe;

public class CompatGuiHandler implements IGuiHandler {

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

		switch (id) {
			case GuiIds.PIPE_APIARIST:
				return new GuiPropolisPipe(player, PipeHelper.getPipe(world, x, y, z, PipeItemsPropolis.class));
			default:
				return null;
		}
	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

		switch (id) {
			case GuiIds.PIPE_APIARIST: {
				IApiaristTracker tracker = CompatModuleForestry.beeRoot.getBreedingTracker(world, player.getGameProfile());
				tracker.synchToPlayer(player);
				return new ContainerPropolisPipe(player.inventory, PipeHelper.getPipe(world, x, y, z, PipeItemsPropolis.class));
			}
			default:
				return null;
		}
	}
}
