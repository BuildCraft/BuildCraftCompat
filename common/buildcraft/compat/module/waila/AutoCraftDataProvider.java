package buildcraft.compat.module.waila;

import buildcraft.compat.CompatUtils;
import buildcraft.factory.BCFactory;
import buildcraft.lib.tile.craft.IAutoCraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
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

import java.util.List;

public class AutoCraftDataProvider {

    static class BodyProvider extends BaseWailaDataProvider.BodyProvider {
        @Override
        public void getWailaBody(ITooltip currentTip, BlockAccessor accessor, IPluginConfig iPluginConfig) {
            BlockEntity tile = accessor.getBlockEntity();
            if (tile instanceof IAutoCraft) {
                CompoundTag nbt = accessor.getServerData();
                if (nbt.contains("recipe_result", Tag.TAG_COMPOUND)) {
                    // Calen: add -> create new line / append -> append at the last line
                    ItemStack result = ItemStack.of(nbt.getCompound("recipe_result"));
                    currentTip.add(Component.translatable("buildcraft.waila.crafting"));
                    currentTip.append(ItemStackElement.of(result));
                    // Calen: an empty line, because the item icon is 2 lines height
                    // if ItemStackElement.of(result, 0.5F), the count text of the stack will not scale
                    currentTip.add(new SpacerElement(new Vec2(0, 5)));
                    if (nbt.contains("recipe_inputs", Tag.TAG_LIST)) {
                        ListTag list = nbt.getList("recipe_inputs", Tag.TAG_COMPOUND);
                        currentTip.add(Component.translatable("buildcraft.waila.crafting_from"));

                        for (int index = 0; index < list.size(); ++index) {
                            CompoundTag compound = list.getCompound(index);
                            currentTip.append(ItemStackElement.of(ItemStack.of(compound)));
                        }
                        currentTip.add(new SpacerElement(new Vec2(0, 5)));
                    }
                } else {
                    currentTip.add(Component.translatable("buildcraft.waila.no_recipe"));
                }
            }
//            else {
//                currentTip.add(new TextComponent(ChatFormatting.RED + "{wrong tile entity}"));
//            }
        }

        @Override
        public ResourceLocation getUid() {
            return ResourceLocation.tryBuild(BCFactory.MODID, "autocraft_client_data");
        }
    }

    static class NBTProvider extends BaseWailaDataProvider.NBTProvider {
        @Override
        public void getNBTData(CompoundTag nbt, BlockAccessor accessor) {
            if (accessor.getBlockEntity() instanceof IAutoCraft auto) {
                ItemStack output = auto.getCurrentRecipeOutput();
                if (!output.isEmpty()) {
                    nbt.put("recipe_result", output.serializeNBT());
                    List<ItemStack> stacks = CompatUtils.compactInventory(auto.getInvBlueprint());
                    ListTag list = new ListTag();

                    for (int index = 0; index < stacks.size(); ++index) {
                        list.add((stacks.get(index)).serializeNBT());
                    }

                    nbt.put("recipe_inputs", list);
                }
            }
        }

        @Override
        public ResourceLocation getUid() {
            return ResourceLocation.tryBuild(BCFactory.MODID, "autocraft_server_data");
        }
    }
}
