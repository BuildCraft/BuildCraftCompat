package buildcraft.compat;

import java.util.Collection;
import java.util.HashSet;

import mods.immibis.redlogic.api.wiring.IBundledWire;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import buildcraft.BuildCraftCompat;
import buildcraft.api.gates.IAction;
import buildcraft.api.gates.IActionProvider;
import buildcraft.api.gates.ITrigger;
import buildcraft.api.gates.ITriggerProvider;
import buildcraft.api.transport.IPipeTile;

public class RedLogicProvider implements IActionProvider, ITriggerProvider {
	private final HashSet<ITrigger> triggers = new HashSet<ITrigger>();
	private final HashSet<IAction> actions = new HashSet<IAction>();
	
	public RedLogicProvider() {
		triggers.add(BuildCraftCompat.triggerBundledInputOff);
		triggers.add(BuildCraftCompat.triggerBundledInputOn);
		actions.add(BuildCraftCompat.actionBundledOutput);
	}
	
	@Override
	public Collection<ITrigger> getNeighborTriggers(Block arg0, TileEntity arg1) {
		return (arg1 instanceof IBundledWire) ? triggers : null;
	}

	@Override
	public Collection<ITrigger> getPipeTriggers(IPipeTile arg0) {
		return null;
	}

	@Override
	public Collection<IAction> getNeighborActions(Block arg0, TileEntity arg1) {
		return (arg1 instanceof IBundledWire) ? actions : null;
	}

	@Override
	public Collection<IAction> getPipeActions(IPipeTile arg0) {
		return null;
	}

}
