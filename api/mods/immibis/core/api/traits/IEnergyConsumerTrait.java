package mods.immibis.core.api.traits;

import net.minecraft.nbt.NBTTagCompound;

public interface IEnergyConsumerTrait extends ITrait {
	/**
	 * Returns the amount of stored energy, in the units requested by the tile entity.
	 */
	public double getStoredEnergy();
	
	/**
	 * Directly sets the amount of stored energy, in preferred units.
	 */
	public void setStoredEnergy(double amt);
	
	/**
	 * Uses some energy. min and max specify the possible range of energy consumption.
	 * min and max are measured in the units requested by the tile entity.
	 * The return value is the amount of energy actually used, in the units requested by the tile entity.
	 */
	public double useEnergy(double min, double max);
	
	// These must be called at the appropriate times
	public void readFromNBT(NBTTagCompound tag);
	public void writeToNBT(NBTTagCompound tag);
	public void onValidate();
	public void onChunkUnload();
	public void onInvalidate();
	
	public static final class EnergyUnit {
		public static final EnergyUnit FURNACE_TICKS = new EnergyUnit(1);
		
		public static final EnergyUnit EU = new EnergyUnit(0.4, FURNACE_TICKS);
		public static final EnergyUnit MJ = new EnergyUnit(1.0, FURNACE_TICKS);
		public static final EnergyUnit RF = new EnergyUnit(0.1, FURNACE_TICKS);
		
		public final double furnaceTicksPerUnit;
		public final double unitsPerFurnaceTick;
		
		// ThisUnit * ReturnValue = OtherUnits
		// in other words, returns number of OtherUnits per ThisUnit
		public double getConversionRate(EnergyUnit to) {
			return furnaceTicksPerUnit * to.unitsPerFurnaceTick;
		}
		
		public EnergyUnit(double unitsPerBaseUnit, EnergyUnit baseUnit) {
			this(unitsPerBaseUnit * baseUnit.furnaceTicksPerUnit);
		}
		
		private EnergyUnit(double furnaceTicksPerUnit) {
			this.unitsPerFurnaceTick = 1/furnaceTicksPerUnit;
			this.furnaceTicksPerUnit = furnaceTicksPerUnit;
		}
	}
}
