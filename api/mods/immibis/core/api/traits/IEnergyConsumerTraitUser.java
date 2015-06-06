package mods.immibis.core.api.traits;

public interface IEnergyConsumerTraitUser {
	
	/**
	 * Returns the energy unit this tile will use for consuming energy.
	 */
	public IEnergyConsumerTrait.EnergyUnit EnergyConsumer_getPreferredUnit();
	
	/**
	 * For power sources that require buffers, returns the preferred buffer size, in preferred units.
	 */
	public double EnergyConsumer_getPreferredBufferSize();
	
	/**
	 * Returns true if this tile would like to use a buffer, even if it's not required.
	 */
	public boolean EnergyConsumer_isBufferingPreferred();
	
}
