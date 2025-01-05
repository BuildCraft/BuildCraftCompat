package buildcraft.compat.bundledredstone;

import buildcraft.api.statements.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import buildcraft.core.lib.utils.ColorUtils;
import buildcraft.core.statements.BCStatement;
import buildcraft.transport.TileGenericPipeCompat;

/**
 * FIXME: This cou
 */
public class TriggerBundledInput extends BCStatement implements ITriggerInternal {
	private boolean active;
	
	public TriggerBundledInput(boolean active) {
		super("buildcraftcompat:bundled.input." + (active ? "active" : "inactive"), "buildcraftcompat.bundled.input." + (active ? "active" : "inactive"));
		this.active = active;
	}
	
	@Override
	public String getDescription() {
		return "Bundled Signal " + (active ? "Active" : "Inactive");
	}

	@Override
	public boolean isTriggerActive(IStatementContainer container, IStatementParameter[] parameter) {
		if (parameter == null || parameter.length < 1 || parameter[0] == null || parameter[0].getItemStack() == null) {
			return false;
		}

		int color = ColorUtils.getColorIDFromDye(parameter[0].getItemStack());

		if (color < 0) {
			return false;
		}

	    TileEntity cTile = container.getTile();

		if (cTile instanceof TileGenericPipeCompat) {
			TileGenericPipeCompat tile = (TileGenericPipeCompat) cTile;
			return tile.getBundledCable(-1, color) ^ !active;
		} else {
			return false;
		}
	}

	@Override
	public void registerIcons(IIconRegister r) {
		icon = r.registerIcon("buildcraftcompat:trigger_bundled_" + (active ? "on" : "off"));
	}

	@Override
	public int minParameters() {
		return 1;
	}

	@Override
	public int maxParameters() {
		return 1;
	}

	@Override
	public IStatementParameter createParameter(int var1) {
		return new StatementParameterItemStack();
	}
}
