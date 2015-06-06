package buildcraft.compat.railcraft;

import buildcraft.core.builders.schematics.SchematicRotateMeta;

public class SchematicRCTrackElevator extends SchematicRotateMeta {
	public SchematicRCTrackElevator() {
		super(new int[]{2, 5, 3, 4}, true);
	}

	@Override
	public BuildingStage getBuildStage() {
		return BuildingStage.SUPPORTED;
	}
}
