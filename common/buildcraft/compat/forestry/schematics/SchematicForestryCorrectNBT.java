package buildcraft.compat.forestry.schematics;

public class SchematicForestryCorrectNBT extends SchematicTileForestry {
	@Override
	public boolean shouldClearNBT() {
		return false;
	}
}
