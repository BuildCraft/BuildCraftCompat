package buildcraft.core.triggers;

import buildcraft.api.gates.IGate;
import buildcraft.api.gates.ITriggerParameter;
import buildcraft.api.transport.IPipeTile;
import buildcraft.compat.DyeUtil;
import buildcraft.core.triggers.BCTrigger;
import buildcraft.transport.TileGenericPipeCompat;

public class TriggerBundledInput extends BCTrigger {
	@Override
	public boolean isTriggerActive(IGate gate, ITriggerParameter[] parameter) {
		if (parameter.length != 1) {
			return false;
		}
		
		int color = DyeUtil.getColor(parameter[0]);
		if (color < 0) {
			return false;
		}
		
		IPipeTile pipeTile = gate.getPipe().getTile();
		
		if (pipeTile instanceof TileGenericPipeCompat) {
			TileGenericPipeCompat tile = (TileGenericPipeCompat) pipeTile;
			return tile.getBundledCable(gate.getSide().ordinal(), color);
		} else {
			return false;
		}
	}
}
