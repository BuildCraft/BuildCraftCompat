package buildcraft.compat.multipart;

import buildcraft.api.blueprints.IBuilderContext;
import codechicken.lib.vec.BlockCoord;
import codechicken.multipart.MultiPartRegistry;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;
import net.minecraft.nbt.NBTTagCompound;

public abstract class SchematicSimplePart<T extends TMultiPart> extends SchematicPart<T>
{
    public final String type;

    public SchematicSimplePart(String type) {
        this.type = type;
    }

    @Override
    public void placePart(IBuilderContext context, BlockCoord pos, NBTTagCompound tag, int rotation) {
        T part = create(tag, rotation);
        if(TileMultipart.canPlacePart(context.world(), pos, part))
            TileMultipart.addPart(context.world(), pos, part);
    }

    /**
     * placePart delegate method. Default implementation uses MultipartRegistry.createPart and TMultiPart.load and then calls rotate
     * @param tag The tag to load the part from
     * @param rotation The number of left rotations to be applied (0-3), 90 degrees counter-clockwise looking down
     * @return A new part instance created from tag with rotation applied.
     */
    public T create(NBTTagCompound tag, int rotation) {
        T part = (T) MultiPartRegistry.loadPart(type, tag);
        part.load(tag);
        rotate(part, rotation);
        return part;
    }

    /**
     * @param part The newly created part to rotate
     * @param rotation The number of left rotations to be applied (0-3), 90 degrees counter-clockwise looking down
     */
    public void rotate(T part, int rotation) {
        for(int i = 0; i < rotation; i++)
            rotateLeft(part);
    }

    /**
     * Default rotate implementation delegate method. Rotates 90 degrees counter-clockwise looking down
     * @param part The newly created part to rotate
     */
    public void rotateLeft(T part) {}
}
