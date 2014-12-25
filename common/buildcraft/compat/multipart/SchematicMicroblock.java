package buildcraft.compat.multipart;

import buildcraft.api.blueprints.IBuilderContext;
import codechicken.lib.vec.BlockCoord;
import codechicken.microblock.*;
import codechicken.multipart.MultiPartRegistry;
import codechicken.multipart.TileMultipart;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class SchematicMicroblock implements SchematicPart<Microblock>
{
    public final String type;
    private final int[] rotateMap;

    public SchematicMicroblock(String type) {
        this.type = type;
        if(type.equals("mcr_face") || type.equals("mcr_hllw")) rotateMap = new int[]{0, 1, 5, 4, 2, 3};
        else if(type.equals("mcr_cnr")) rotateMap = new int[]{4, 5, 0, 1, 6, 7, 2, 3};
        else if(type.equals("mcr_edge")) rotateMap = new int[]{2, 0, 3, 1, 8, 10, 9, 11, 5, 7, 4, 6};
        else rotateMap = new int[]{0, 2, 1};//post
    }

    @Override
    public List<ItemStack> getRequirements(Microblock part) {
        return part.getDrops();
    }

    @Override
    public NBTTagCompound writePart(Microblock part) {
        NBTTagCompound tag = new NBTTagCompound();
        part.save(tag);
        return tag;
    }

    @Override
    public void placePart(IBuilderContext context, BlockCoord pos, NBTTagCompound tag, int rotation) {
        Microblock micro = create(tag, rotation);
        if(micro != null && TileMultipart.canPlacePart(context.world(), pos, micro))
            TileMultipart.addPart(context.world(), pos, micro);
    }

    private Microblock create(NBTTagCompound tag, int rotation) {
        String material = tag.getString("material");
        if(MicroMaterialRegistry.getMaterial(material) == null)
            return null;//material doesn't exist

        Microblock micro = (Microblock) MultiPartRegistry.createPart(type, false);
        micro.load(tag);
        rotate(micro, rotation);

        return micro;
    }

    private void rotate(Microblock micro, int rotation) {
        if(rotation == 0) return;

        int shape = micro.getShape();
        for(int i = 0; i < rotation; i++)
            shape = rotateMap[shape];

        micro.shape_$eq((byte) (micro.shape() & 0xF0 | shape));
    }
}
