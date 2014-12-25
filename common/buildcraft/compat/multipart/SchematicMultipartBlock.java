package buildcraft.compat.multipart;

import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.api.blueprints.SchematicTile;
import codechicken.lib.vec.BlockCoord;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;
import codechicken.multipart.handler.MultipartProxy;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.LinkedList;
import java.util.Map.Entry;

public class SchematicMultipartBlock extends SchematicTile
{
    private int rotation = 0;

    @Override
    public void storeRequirements(IBuilderContext context, int x, int y, int z) {
        LinkedList<ItemStack> requirements = new LinkedList<ItemStack>();
        for(Entry<TMultiPart, SchematicPart> s : MultipartSchematics.getSchematics(context, x, y, z))
            requirements.addAll(s.getValue().getRequirements(s.getKey()));
        storedRequirements = requirements.toArray(new ItemStack[requirements.size()]);
    }

    @Override
    public void initializeFromObjectAt(IBuilderContext context, int x, int y, int z) {
        NBTTagList parts = new NBTTagList();
        for(Entry<TMultiPart, SchematicPart> s : MultipartSchematics.getSchematics(context, x, y, z)) {
            NBTTagCompound tag = s.getValue().writePart(s.getKey());
            if(tag != null) {
                NBTTagCompound tag2 = new NBTTagCompound();
                tag2.setString("type", s.getKey().getType());
                tag2.setTag("part", tag);
                parts.appendTag(tag2);
            }
        }
        tileNBT.setTag("parts", parts);
    }

    @Override
    public void placeInWorld(IBuilderContext context, int x, int y, int z, LinkedList<ItemStack> stacks) {
        NBTTagList parts = tileNBT.getTagList("parts", 10);
        if(parts.tagCount() == 0) {
            context.world().setBlockToAir(x, y, z);
            return;
        }

        context.world().setBlock(x, y, z, MultipartProxy.block());
        BlockCoord pos = new BlockCoord(x, y, z);

        for(int i = 0; i < parts.tagCount(); i++) {
            NBTTagCompound tag = parts.getCompoundTagAt(i);
            SchematicPart s = MultipartSchematics.getSchematic(tag.getString("type"));
            if(s != null)
                s.placePart(context, pos, tag.getCompoundTag("part"), rotation);
        }

        TileMultipart tile = (TileMultipart) context.world().getTileEntity(x, y, z);
        if(tile == null || tile.jPartList().isEmpty())
            context.world().setBlockToAir(x, y, z);
    }

    @Override
    public void rotateLeft(IBuilderContext context) {
        rotation++;
    }
}
