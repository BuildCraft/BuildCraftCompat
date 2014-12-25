package buildcraft.compat.multipart;

import buildcraft.api.blueprints.IBuilderContext;
import codechicken.lib.vec.BlockCoord;
import codechicken.multipart.TMultiPart;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public interface SchematicPart<T extends TMultiPart>
{
    /**
     * Get all the itemstacks required to place this part. If the part is not valid or cannot be placed, return an empty list.
     * @param part The multipart instance to be copied
     * @return A list of ItemStacks required to place this part. May be empty.
     */
    public List<ItemStack> getRequirements(T part);

    /**
     * Convert this part to an NBT tag to be used with placePart. If this part is not valid or cannot be placed return null
     * @param part The multipart instance to be copied
     * @return An NBT tag to be used with placePart or null if this part cannot be copied
     */
    public NBTTagCompound writePart(T part);

    /**
     * Place the part represented by tag into the world. This method should call TileMultipart.canPlacePart and return without placement if the part cannot be placed, or there is an error in the tag.
     * It is not necessary to apply rotations, but failing to do so may prevent this part, or others in the same block space from being placed.
     * @param context The environment for placement
     * @param pos The position in the world to place the part
     * @param tag The tag to load the part from
     * @param rotation The number of left rotations to be applied (0-3), 90 degrees counter-clockwise looking down
     */
    public void placePart(IBuilderContext context, BlockCoord pos, NBTTagCompound tag, int rotation);
}
