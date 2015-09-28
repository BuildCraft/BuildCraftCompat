package buildcraft.compat.mfr;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.api.statements.IStatementContainer;
import buildcraft.api.statements.IStatementParameter;
import buildcraft.api.statements.ITriggerExternal;
import buildcraft.core.lib.utils.StringUtils;
import buildcraft.core.statements.BCStatement;

import powercrystals.minefactoryreloaded.tile.transport.TileEntityConveyor;

public class TriggerConveyorReversed extends BCStatement implements ITriggerExternal {
	public TriggerConveyorReversed() {
		super("MFR:ConveyorIsReversed");
	}

	@Override
	public String getDescription() {
		return StringUtils.localize("gate.trigger.mfr.conreversed");
	}

	@Override
	public boolean isTriggerActive(TileEntity tileEntity, ForgeDirection forgeDirection, IStatementContainer iStatementContainer, IStatementParameter[] iStatementParameters) {
		if (tileEntity instanceof TileEntityConveyor) {
			return ((TileEntityConveyor) tileEntity).getConveyorReversed();
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		icon = iconRegister.registerIcon("buildcraftcompat:statements/mfr/ConveyorReversed");
	}
}
