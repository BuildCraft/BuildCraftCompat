package buildcraft.compat;

import java.util.HashMap;

public class CompatClassTransformer {
	private static final HashMap<String, String> extensionMappings = new HashMap<String, String>();
	
	static {
		extensionMappings.put("buildcraft.core.gui.GuiBuildCraft", "buildcraft.core.gui.GuiBuildCraftExtended");
	}
	
	public CompatClassTransformer() {
		
	}
}
