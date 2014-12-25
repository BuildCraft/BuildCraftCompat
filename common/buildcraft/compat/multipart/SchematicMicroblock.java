package buildcraft.compat.multipart;

import codechicken.microblock.*;
import net.minecraft.nbt.NBTTagCompound;

public class SchematicMicroblock extends SchematicSimplePart<Microblock>
{
    private final int[] rotateMap;

    public SchematicMicroblock(String type) {
        super(type);
        if(type.equals("mcr_face") || type.equals("mcr_hllw")) rotateMap = new int[]{0, 1, 5, 4, 2, 3};
        else if(type.equals("mcr_cnr")) rotateMap = new int[]{4, 5, 0, 1, 6, 7, 2, 3};
        else if(type.equals("mcr_edge")) rotateMap = new int[]{2, 0, 3, 1, 8, 10, 9, 11, 5, 7, 4, 6};
        else rotateMap = new int[]{0, 2, 1};//post
    }

    @Override
    public boolean isValid(NBTTagCompound tag) {
        return MicroMaterialRegistry.getMaterial(tag.getString("material")) != null;
    }

    @Override
    public void rotateLeft(Microblock micro) {
        micro.shape_$eq((byte) (micro.shape() & 0xF0 | rotateMap[micro.getShape()]));
    }
}
