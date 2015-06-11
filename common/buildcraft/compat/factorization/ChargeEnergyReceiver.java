package buildcraft.compat.factorization;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import cofh.api.energy.IEnergyReceiver;
import buildcraft.compat.CompatModuleFactorization;
import factorization.api.Charge;
import factorization.api.IChargeConductor;

public class ChargeEnergyReceiver implements IEnergyReceiver {
	private final Charge charge;
	private final IChargeConductor conductor;
	private int microFluxes;

	public ChargeEnergyReceiver(TileEntity chargeConductor) {
		this.conductor = (IChargeConductor) chargeConductor;
		this.charge = conductor.getCharge();
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		int maxCharge = Math.min(5, 100 - charge.getValue());
		if (maxCharge < 0) {
			return 0;
		}

		int rfToUse = (int) Math.floor(maxCharge / CompatModuleFactorization.CHARGE_PER_RF);

		if (!simulate) {
			int chargeVal = (int) Math.floor(maxCharge + (microFluxes * CompatModuleFactorization.CHARGE_PER_RF));
			int rfInserted = (int) Math.floor(chargeVal / CompatModuleFactorization.CHARGE_PER_RF);
			microFluxes += (rfToUse - rfInserted);
			charge.addValue(chargeVal);
		}

		return rfToUse;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return (int) Math.floor(charge.getValue() / CompatModuleFactorization.CHARGE_PER_RF);
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return getEnergyStored(from) + 1;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}
}
