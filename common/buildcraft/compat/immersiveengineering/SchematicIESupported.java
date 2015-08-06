package buildcraft.compat.immersiveengineering;

import buildcraft.api.blueprints.Schematic;

public class SchematicIESupported extends SchematicIEBase {
	@Override
	public Schematic.BuildingStage getBuildStage() {
		return Schematic.BuildingStage.SUPPORTED;
	}
}
