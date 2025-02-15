package buildcraft.compat.module.waila;

import buildcraft.lib.tile.craft.IAssemblyCraft;
import buildcraft.silicon.BCSilicon;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec2;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.impl.ui.ItemStackElement;
import snownee.jade.impl.ui.SpacerElement;

public class AssemblyCraftDataProvider {
    static class BodyProvider extends BaseWailaDataProvider.BodyProvider {
        @Override
        public void getWailaBody(ITooltip currentTip, BlockAccessor accessor, IPluginConfig iPluginConfig) {
            BlockEntity tile = accessor.getBlockEntity();
            if (tile instanceof IAssemblyCraft) {
//            CompoundTag nbt = accessor.getNBTData();
                CompoundTag nbt = accessor.getServerData();
                if (nbt.contains("recipe_result", Tag.TAG_COMPOUND)) {
                    ItemStack recipe_result = ItemStack.of(nbt.getCompound("recipe_result"));
                    if (!recipe_result.isEmpty()) {
                        currentTip.add(Component.translatable("buildcraft.waila.crafting"));
                        currentTip.append(ItemStackElement.of(recipe_result));
                        // Calen: an empty line because the item icon is 2 lines height
                        // if ItemStackElement.of(result, 0.5F), the count text of the stack will not scale
                        currentTip.add(new SpacerElement(new Vec2(0, 5)));
                        return;
                    }
                }
                currentTip.add(Component.translatable("buildcraft.waila.no_recipe"));
            }
//            else {
//                currentTip.add(new TextComponent(ChatFormatting.RED + "{wrong tile entity}"));
//            }
        }

        @Override
        public ResourceLocation getUid() {
            return ResourceLocation.tryBuild(BCSilicon.MODID, "assembly_client_data");
        }
    }

    static class NBTProvider extends BaseWailaDataProvider.NBTProvider {
        @Override
        public void getNBTData(CompoundTag nbt, BlockAccessor accessor) {
            if (accessor.getBlockEntity() instanceof IAssemblyCraft assembly) {
                nbt.put("recipe_result", assembly.getAssemblyResult().serializeNBT());
            }
        }

        @Override
        public ResourceLocation getUid() {
            return ResourceLocation.tryBuild(BCSilicon.MODID, "assembly_server_data");
        }
    }
}
