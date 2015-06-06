package mods.immibis.core.api.traits;

import java.util.HashMap;
import java.util.Map;

/**
 * Marker interface implemented by trait types.
 */
public interface ITrait {

	/**
	 * Maps interfaces to their default implementations.
	 */
	public static Map<Class<? extends ITrait>, Class<? extends ITrait>> knownInterfaces = new HashMap<Class<? extends ITrait>, Class<? extends ITrait>>();
}
