package buildcraft.compat.redlogic;

import java.util.Collection;
import java.util.HashSet;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.api.statements.IActionExternal;
import buildcraft.api.statements.IActionInternal;
import buildcraft.api.statements.IActionProvider;
import buildcraft.api.statements.IStatementContainer;
import buildcraft.api.statements.ITriggerExternal;
import buildcraft.api.statements.ITriggerInternal;
import buildcraft.api.statements.ITriggerProvider;
import buildcraft.compat.CompatModuleBundledRedstone;
import buildcraft.transport.TileGenericPipeCompat;
import mods.immibis.redlogic.api.wiring.IBundledWire;

public class BRProviderRedLogic implements ITriggerProvider, IActionProvider {
	private final HashSet<ITriggerExternal> triggers = new HashSet<ITriggerExternal>();
	private final HashSet<IActionExternal> actions = new HashSet<IActionExternal>();
	
	public BRProviderRedLogic() {
		triggers.add(CompatModuleBundledRedstone.triggerBundledInputOff);
		triggers.add(CompatModuleBundledRedstone.triggerBundledInputOn);
		actions.add(CompatModuleBundledRedstone.actionBundledOutput);
	}
	
	@Override
	public Collection<ITriggerExternal> getExternalTriggers(ForgeDirection side, TileEntity arg1) {
		return (arg1 instanceof IBundledWire) ? triggers : null;
	}

	@Override
	public Collection<ITriggerInternal> getInternalTriggers(IStatementContainer c) {
		return null;
	}

	@Override
	public Collection<IActionExternal> getExternalActions(ForgeDirection side, TileEntity arg1) {
		return (arg1 instanceof IBundledWire) ? actions : null;
	}

	@Override
	public Collection<IActionInternal> getInternalActions(IStatementContainer c) {
		return null;
	}

}
