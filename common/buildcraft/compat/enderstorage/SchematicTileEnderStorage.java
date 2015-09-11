package buildcraft.compat.enderstorage;

import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.api.blueprints.SchematicTile;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

// Copy all NBT, including:
// owner
// freq
// ir (if ender tank)
public class SchematicTileEnderStorage extends SchematicTile {
    @Override
    public void rotateLeft(IBuilderContext context) {
        if (tileNBT != null) {
            tileNBT.setInteger("rot", (tileNBT.getInteger("rot") + 1) % 4);
        }
    }

    @Override
    public void storeRequirements(IBuilderContext context, int x, int y, int z) {
        // ignore inventory contents (that's world state, not block state)
        if (block != null) {
            ArrayList<ItemStack> req = block.getDrops(context.world(), x,
                    y, z, context.world().getBlockMetadata(x, y, z), 0);

            if (req != null) {
                storedRequirements = new ItemStack [req.size()];
                req.toArray(storedRequirements);
            }
        }
    }
}
