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

import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryInventory;

public class TriggerMachineIsBackstuffed extends BCStatement implements ITriggerExternal {
	public TriggerMachineIsBackstuffed() {
		super("MFR:IsBackstuffed");
	}

	@Override
	public String getDescription() {
		return StringUtils.localize("gate.trigger.mfr.backstuffed");
	}

	@Override
	public boolean isTriggerActive(TileEntity tileEntity, ForgeDirection forgeDirection, IStatementContainer iStatementContainer, IStatementParameter[] iStatementParameters) {
		if (tileEntity instanceof TileEntityFactoryInventory) {
			return ((TileEntityFactoryInventory) tileEntity).hasDrops();
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		icon = iconRegister.registerIcon("buildcrafttransport:triggers/guitriggers_3_4");
	}
}
