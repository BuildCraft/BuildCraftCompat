package buildcraft.compat.module.waila;

import buildcraft.lib.tile.craft.IAssemblyCraft;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.util.List;

public class AssemblyCraftDataProvider {
    static class BodyProvider extends BaseWailaDataProvider.BodyProvider {
        @Override
        public void getWailaBody(List<ITextComponent> currentTip, IDataAccessor accessor, IPluginConfig iPluginConfig) {
            TileEntity tile = accessor.getTileEntity();
            if (tile instanceof IAssemblyCraft) {
//            CompoundTag nbt = accessor.getNBTData();
                CompoundNBT nbt = accessor.getServerData();
                if (nbt.contains("recipe_result", Constants.NBT.TAG_COMPOUND)) {
                    ItemStack recipe_result = ItemStack.of(nbt.getCompound("recipe_result"));
                    if (!recipe_result.isEmpty()) {
                        currentTip.add(new TranslationTextComponent("buildcraft.waila.crafting"));
                        currentTip.add(HWYLAPlugin.getItemStackString(recipe_result));
                        return;
                    }
                }
                currentTip.add(new TranslationTextComponent("buildcraft.waila.no_recipe"));
            }
//            else {
//                currentTip.add(new TextComponent(ChatFormatting.RED + "{wrong tile entity}"));
//            }
        }
    }

    static class NBTProvider extends BaseWailaDataProvider.NBTProvider {
        @Override
        public void getNBTData(CompoundNBT nbt, ServerPlayerEntity player, World world, TileEntity tile) {
            if (tile instanceof IAssemblyCraft) {
                IAssemblyCraft assembly = (IAssemblyCraft) tile;
                nbt.put("recipe_result", assembly.getAssemblyResult().serializeNBT());
            }
        }
    }
}
