package buildcraft.compat.multipart;

import buildcraft.api.blueprints.*;
import codechicken.lib.vec.BlockCoord;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.handler.MultipartProxy;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class SchematicMultipartBlock extends SchematicBlock
{
    private int rotation = 0;
    private LinkedList<Iterable<ItemStack>> requirements = new LinkedList<Iterable<ItemStack>>();
    private NBTTagList parts;

    @Override
    public boolean doNotUse() {
        return parts.tagCount() == 0;
    }

    @Override
    public boolean isAlreadyBuilt(IBuilderContext context, int x, int y, int z) {
        Block block = context.world().getBlock(x, y, z);
        if (block == MultipartProxy.block()) {
            // The following code is technically accurate, but does not take actual part information
            // into account very well. If you wish to fix it, be my guest, but it will require
            // better knowledge of FMP than what I have (that is, nearly none).

            TObjectIntMap<String> partList = new TObjectIntHashMap<>();

            for (int i = 0; i < parts.tagCount(); i++) {
                NBTTagCompound tag = parts.getCompoundTagAt(i);
                partList.adjustOrPutValue(tag.getString("type"), 1, 1);
            }

            for(Entry<TMultiPart, SchematicPart> s : MultipartSchematics.getSchematics(context, x, y, z)) {
                String type = s.getKey().getType();
                partList.adjustOrPutValue( type, -1, -1);
            }

            for (String s : partList.keySet()) {
                if (partList.get(s) != 0) {
                    return false;
                }
            }

            return true;

            /* Multimap<String, NBTTagCompound> partList = HashMultimap.create();

            for (int i = 0; i < parts.tagCount(); i++) {
                NBTTagCompound tag = parts.getCompoundTagAt(i);
                partList.put(tag.getString("type"), tag.getCompoundTag("part"));
            }

            for(Entry<TMultiPart, SchematicPart> s : MultipartSchematics.getSchematics(context, x, y, z)) {
                String type = s.getKey().getType();
                if (!partList.containsKey(type)) {
                    return false;
                }

                if (!partList.remove(type, s.getValue().writePart(s.getKey()))) {
                    return false;
                }
            }

            return partList.isEmpty(); */
        }

        return false;
    }

    @Override
    public void storeRequirements(IBuilderContext context, int x, int y, int z) {
        for(Entry<TMultiPart, SchematicPart> s : MultipartSchematics.getSchematics(context, x, y, z)) {
            Iterable<ItemStack> stacks = s.getValue().getRequirements(s.getKey());
            if(stacks != null)
                requirements.add(stacks);
        }
    }

    @Override
    public void getRequirementsForPlacement(IBuilderContext context, LinkedList<ItemStack> requirements) {
        for(Iterable<ItemStack> stacks : this.requirements)
            for(ItemStack stack : stacks)
                requirements.add(stack);
    }

    @Override
    public void initializeFromObjectAt(IBuilderContext context, int x, int y, int z) {
        parts = new NBTTagList();
        for(Entry<TMultiPart, SchematicPart> s : MultipartSchematics.getSchematics(context, x, y, z)) {
            NBTTagCompound tag = s.getValue().writePart(s.getKey());
            if(tag != null) {
                NBTTagCompound tag2 = new NBTTagCompound();
                tag2.setString("type", s.getKey().getType());
                tag2.setTag("part", tag);
                parts.appendTag(tag2);
            }
        }
    }

    @Override
    public void writeSchematicToNBT(NBTTagCompound nbt, MappingRegistry registry) {
        nbt.setInteger("blockId", registry.getIdForBlock(MultipartProxy.block()));
        for(int i = 0; i < parts.tagCount(); i++) {
            NBTTagCompound partTag = parts.getCompoundTagAt(i);
            NBTTagList items = new NBTTagList();
            for(ItemStack stack : requirements.get(i)) {
                NBTTagCompound itemTag = new NBTTagCompound();
                stack.writeToNBT(itemTag);
                registry.stackToRegistry(itemTag);
                items.appendTag(itemTag);
            }
            partTag.setTag("items", items);
        }
        nbt.setTag("parts", parts);
    }

    @Override
    public void readSchematicFromNBT(NBTTagCompound nbt, MappingRegistry registry) {
        requirements.clear();
        parts = nbt.getTagList("parts", 10);
        for(int i = 0; i < parts.tagCount(); i++) {
            NBTTagCompound tag = parts.getCompoundTagAt(i);
            SchematicPart s = MultipartSchematics.getSchematic(tag.getString("type"));
            if(s == null || !s.isValid(tag.getCompoundTag("part"))) {
                parts.removeTag(i);
                continue;
            }

            requirements.add(readItems(tag.getTagList("items", 10), registry));
        }
    }

    /**
     * Copied from SchematicBlock.readSchematicFromNBT
     */
    private LinkedList<ItemStack> readItems(NBTTagList itemList, MappingRegistry registry) {
        LinkedList<ItemStack> items = new LinkedList<ItemStack>();
        for (int i = 0; i < itemList.tagCount(); ++i) {
            try {
                NBTTagCompound sub = itemList.getCompoundTagAt(i);
                if (sub.getInteger("id") >= 0) {
                    registry.stackToWorld(sub);
                    items.add(ItemStack.loadItemStackFromNBT(sub));
                } else {
                    defaultPermission = BuildingPermission.CREATIVE_ONLY;
                }
            } catch (MappingNotFoundException e) {
                defaultPermission = BuildingPermission.CREATIVE_ONLY;
            } catch (Throwable t) {
                t.printStackTrace();
                defaultPermission = BuildingPermission.CREATIVE_ONLY;
            }
        }
        return items;
    }

    @Override
    public void placeInWorld(IBuilderContext context, int x, int y, int z, LinkedList<ItemStack> stacks) {
        BlockCoord pos = new BlockCoord(x, y, z);
        for(int i = 0; i < parts.tagCount(); i++) {
            NBTTagCompound tag = parts.getCompoundTagAt(i);
            MultipartSchematics.getSchematic(tag.getString("type")).placePart(context, pos, tag.getCompoundTag("part"), rotation);
        }
    }

    @Override
    public void rotateLeft(IBuilderContext context) {
        rotation++;
    }
}
