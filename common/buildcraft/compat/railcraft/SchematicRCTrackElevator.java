package buildcraft.compat.railcraft;

import java.util.Set;

import com.google.common.collect.Sets;

import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.api.core.BlockIndex;
import buildcraft.core.builders.schematics.SchematicRotateMeta;

public class SchematicRCTrackElevator extends SchematicRotateMeta {
	public SchematicRCTrackElevator() {
		super(new int[]{2, 5, 3, 4}, true);
	}

	@Override
	public Set<BlockIndex> getPrerequisiteBlocks(IBuilderContext context) {
		int side = meta & 7;
		if (side >= 2 && side <= 5) {
			return Sets.newHashSet(new BlockIndex[]{RELATIVE_INDEXES[side ^ 1]});
		}
		return null;
	}
}
