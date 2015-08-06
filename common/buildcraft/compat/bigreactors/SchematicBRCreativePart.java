package buildcraft.compat.bigreactors;

import buildcraft.api.blueprints.BuildingPermission;

public class SchematicBRCreativePart extends SchematicBRPart {
	@Override
	public BuildingPermission getBuildingPermission() {
		return BuildingPermission.CREATIVE_ONLY;
	}
}
