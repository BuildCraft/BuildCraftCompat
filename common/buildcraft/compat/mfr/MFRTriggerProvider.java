package buildcraft.compat.mfr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.api.statements.IStatementContainer;
import buildcraft.api.statements.ITriggerExternal;
import buildcraft.api.statements.ITriggerInternal;
import buildcraft.api.statements.ITriggerProvider;

import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryInventory;
import powercrystals.minefactoryreloaded.tile.transport.TileEntityConveyor;

public class MFRTriggerProvider implements ITriggerProvider {
	public TriggerConveyorReversed triggerConveyorReversed = new TriggerConveyorReversed();
	public TriggerConveyorRunning triggerConveyorRunning = new TriggerConveyorRunning();
	public TriggerMachineIsBackstuffed triggerMachineIsBackstuffed = new TriggerMachineIsBackstuffed();

	@Override
	public Collection<ITriggerInternal> getInternalTriggers(IStatementContainer iStatementContainer) {
		return null;
	}

	@Override
	public Collection<ITriggerExternal> getExternalTriggers(ForgeDirection forgeDirection, TileEntity tileEntity) {
		List<ITriggerExternal> triggers = new ArrayList<ITriggerExternal>();
		if (tileEntity instanceof TileEntityConveyor) {
			triggers.add(triggerConveyorReversed);
			triggers.add(triggerConveyorRunning);
		} else if (tileEntity instanceof TileEntityFactoryInventory) {
			triggers.add(triggerMachineIsBackstuffed);
		}
		return triggers;
	}
}
