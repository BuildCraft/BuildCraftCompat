package buildcraft.compat.bundledredstone;

import buildcraft.api.statements.*;
import buildcraft.compat.CompatModuleBundledRedstone;
import buildcraft.transport.TileGenericPipeCompat;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Collection;
import java.util.HashSet;

public class StatementProviderBundledRedstone implements ITriggerProvider, IActionProvider {
	private final HashSet<ITriggerInternal> triggers = new HashSet<>();
	private final HashSet<IActionExternal> actions = new HashSet<>();

	public StatementProviderBundledRedstone() {
		triggers.add(CompatModuleBundledRedstone.triggerBundledInputOff);
		triggers.add(CompatModuleBundledRedstone.triggerBundledInputOn);
		actions.add(CompatModuleBundledRedstone.actionBundledOutput);
	}
	
	@Override
	public Collection<ITriggerExternal> getExternalTriggers(ForgeDirection side, TileEntity arg1) {
		return null;
	}

	@Override
	public Collection<ITriggerInternal> getInternalTriggers(IStatementContainer c) {
		if (c == null) {
			return null;
		}

		TileEntity cTile = c.getTile();
		if (!(cTile instanceof TileGenericPipeCompat)) {
			return null;
		}

		for (ForgeDirection o : ForgeDirection.VALID_DIRECTIONS) {
			TileEntity tile = ((TileGenericPipeCompat) cTile).getTile(o);
			if (CompatModuleBundledRedstone.isBundledOutputPresent(tile, o.getOpposite())) {
				return triggers;
			}
		}
		return null;
	}

	@Override
	public Collection<IActionExternal> getExternalActions(ForgeDirection side, TileEntity arg1) {
		return CompatModuleBundledRedstone.isBundledInputPresent(arg1, side == null ? null : side.getOpposite()) ? actions : null;
	}

	@Override
	public Collection<IActionInternal> getInternalActions(IStatementContainer c) {
		return null;
	}

}
