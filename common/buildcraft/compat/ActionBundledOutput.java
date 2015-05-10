package buildcraft.compat;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import buildcraft.api.statements.IActionExternal;
import buildcraft.api.statements.IStatementContainer;
import buildcraft.api.statements.IStatementParameter;
import buildcraft.api.statements.StatementParameterItemStack;
import buildcraft.core.lib.utils.ColorUtils;
import buildcraft.core.statements.BCStatement;
import buildcraft.transport.TileGenericPipeCompat;

public class ActionBundledOutput extends BCStatement implements IActionExternal {
	
	public ActionBundledOutput() {
		super("buildcraftcompat:bundled.output", "buildcraftcompat.bundled.output");
	}
	
	@Override
	public String getDescription() {
		return "Bundled Signal";
	}

	@Override
	public void actionActivate(TileEntity tileEntity, ForgeDirection side, IStatementContainer container, IStatementParameter[] parameter) {
		if (parameter == null || parameter.length < 1 || parameter[0] == null || parameter[0].getItemStack() == null) {
			return;
		}

		int color = ColorUtils.getColorIDFromDye(parameter[0].getItemStack());
		if (color < 0) {
			return;
		}

		TileEntity cTile = container.getTile();

		if (cTile instanceof TileGenericPipeCompat) {
			TileGenericPipeCompat tile = (TileGenericPipeCompat) cTile;
			tile.setBundledCable(side.ordinal(), color, true);
		}
	}

	@Override
	public void registerIcons(IIconRegister r) {
		icon = r.registerIcon("buildcraftcompat:action_bundled");
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
