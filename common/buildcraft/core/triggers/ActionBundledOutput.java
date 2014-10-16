package buildcraft.core.triggers;

import buildcraft.api.gates.IActionParameter;
import buildcraft.api.gates.IGate;
import buildcraft.api.transport.IPipeTile;
import buildcraft.compat.DyeUtil;
import buildcraft.core.triggers.BCActionActive;
import buildcraft.transport.TileGenericPipeCompat;

public class ActionBundledOutput extends BCActionActive {
	
	public ActionBundledOutput() {
		super("buildcraftcompat:bundled.output", "buildcraftcompat.bundled.output");
	}
	
	@Override
	public String getDescription() {
		return "Bundled Signal";
	}
	
	@Override
	public void actionActivate(IGate gate, IActionParameter[] parameter) {
		if (parameter.length != 1) {
			return;
		}
		
		int color = DyeUtil.getColor(parameter[0]);
		if (color < 0) {
			return;
		}
		
		IPipeTile pipeTile = gate.getPipe().getTile();
		
		if (pipeTile instanceof TileGenericPipeCompat) {
			TileGenericPipeCompat tile = (TileGenericPipeCompat) pipeTile;
			tile.setBundledCable(gate.getSide().ordinal(), color, true);
		}
	}
}
