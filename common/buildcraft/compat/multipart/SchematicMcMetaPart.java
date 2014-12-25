package buildcraft.compat.multipart;

import codechicken.multipart.minecraft.McMetaPart;
import net.minecraft.nbt.NBTTagCompound;

public class SchematicMcMetaPart extends SchematicSimplePart<McMetaPart>
{
    private static final int[] rotateMap = new int[]{0, 3, 4, 2, 1};

    public SchematicMcMetaPart(String type) {
        super(type);
    }

    @Override
    public McMetaPart create(NBTTagCompound tag, int rotation) {
        McMetaPart part = super.create(tag, rotation);
        part.meta &= 0xF;//deactivate redstone torches
        return part;
    }

    @Override
    public void rotateLeft(McMetaPart part) {
        part.meta = (byte) (part.meta & 8 | rotateMap[part.meta & 7]);
    }
}
