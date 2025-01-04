package buildcraft.compat.forestry.pipes;

import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.api.blueprints.SchematicTile;
import buildcraft.core.lib.inventory.SimpleInventory;
import buildcraft.transport.schematics.BptPipeExtension;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class BptPipePropolis extends BptPipeExtension {
    public BptPipePropolis(Item item) {
        super(item);
    }

    public void rotateLeft(SchematicTile slot, IBuilderContext context) {
        NBTTagCompound oldNBT = (NBTTagCompound) slot.tileNBT.copy();

        for (int fromDir = 0; fromDir < 6; fromDir++) {
            for (int i = 0; i < 3; i++) {
                slot.tileNBT.removeTag("GenomeFilterS" + fromDir + "-" + i + "-0");
                slot.tileNBT.removeTag("GenomeFilterS" + fromDir + "-" + i + "-1");
            }
        }

        for (int fromDir = 0; fromDir < 6; fromDir++) {
            int toDir = ForgeDirection.values()[fromDir].getRotation(ForgeDirection.UP).ordinal();

            slot.tileNBT.setTag("TypeFilter" + toDir, oldNBT.getTag("TypeFilter" + fromDir));

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 2; j++) {
                    NBTBase fromTag = oldNBT.getTag("GenomeFilterS" + fromDir + "-" + i + "-" + j);
                    if (fromTag != null) {
                        slot.tileNBT.setTag("GenomeFilterS" + toDir + "-" + i + "-" + j, fromTag);
                    }
                }
            }
        }
    }
}
