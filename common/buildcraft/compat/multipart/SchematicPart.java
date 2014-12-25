package buildcraft.compat.multipart;

import buildcraft.api.blueprints.IBuilderContext;
import codechicken.lib.vec.BlockCoord;
import codechicken.multipart.TMultiPart;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class SchematicPart<T extends TMultiPart>
{
    /**
     * Get all the itemstacks required to place this part. If the part is not valid or cannot be placed, return null.
     * It is important that this method returns null if writePart does.
     * @param part The multipart instance to be copied
     * @return A list of ItemStacks required to place this part, may be empty, or null if the part cannot be copied.
     */
    public Iterable<ItemStack> getRequirements(T part) {
        return part.getDrops();
    }

    /**
     * Convert this part to an NBT tag to be used with placePart. If this part is not valid or cannot be placed return null
     * It is important that this method returns null if getRequirements does.
     * @param part The multipart instance to be copied
     * @return An NBT tag to be used with placePart or null if this part cannot be copied.
     */
    public NBTTagCompound writePart(T part) {
        NBTTagCompound tag = new NBTTagCompound();
        part.save(tag);
        return tag;
    }

    /**
     * Determine if the loading and placing a part from tag would succeed
     * @param tag The tag to load the part from
     * @return true if this part should result in valid placement
     */
    public boolean isValid(NBTTagCompound tag) {
        return true;
    }

    /**
     * Place the part represented by tag into the world. This method should call TileMultipart.canPlacePart and return without placement if the part cannot be placed
     * It is not necessary to apply rotations, but failing to do so may prevent this part, or others in the same block space from being placed.
     * @param context The environment for placement
     * @param pos The position in the world to place the part
     * @param tag The tag to load the part from
     * @param rotation The number of left rotations to be applied (0-3), 90 degrees counter-clockwise looking down
     */
    public abstract void placePart(IBuilderContext context, BlockCoord pos, NBTTagCompound tag, int rotation);
}
