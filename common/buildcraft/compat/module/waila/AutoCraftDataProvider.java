package buildcraft.compat.module.waila;

import buildcraft.compat.CompatUtils;
import buildcraft.lib.tile.craft.IAutoCraft;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.util.List;

public class AutoCraftDataProvider {

    static class BodyProvider extends BaseWailaDataProvider.BodyProvider {
        @Override
        public void getWailaBody(List<ITextComponent> currentTip, IDataAccessor accessor, IPluginConfig config) {
            TileEntity tile = accessor.getTileEntity();
            if (tile instanceof IAutoCraft) {
                CompoundNBT nbt = accessor.getServerData();
                if (nbt.contains("recipe_result", Constants.NBT.TAG_COMPOUND)) {
                    // Calen: add -> create new line / append -> append at the last line
                    ItemStack result = ItemStack.of(nbt.getCompound("recipe_result"));
                    currentTip.add(new TranslationTextComponent("buildcraft.waila.crafting"));
                    currentTip.add(HWYLAPlugin.getItemStackString(result));
                    // Calen: an empty line, because the item icon is 2 lines height
                    // if ItemStackElement.of(result, 0.5F), the count text of the stack will not scale
                    currentTip.add(new StringTextComponent(""));
                    if (nbt.contains("recipe_inputs", Constants.NBT.TAG_LIST)) {
                        ListNBT list = nbt.getList("recipe_inputs", Constants.NBT.TAG_COMPOUND);
                        currentTip.add(new TranslationTextComponent("buildcraft.waila.crafting_from"));

                        for (int index = 0; index < list.size(); ++index) {
                            CompoundNBT compound = list.getCompound(index);
                            currentTip.add(HWYLAPlugin.getItemStackString(ItemStack.of(compound)));
                        }
                    }
                } else {
                    currentTip.add(new TranslationTextComponent("buildcraft.waila.no_recipe"));
                }
            }
//            else {
//                currentTip.add(new TextComponent(ChatFormatting.RED + "{wrong tile entity}"));
//            }
        }
    }

    static class NBTProvider extends BaseWailaDataProvider.NBTProvider {
        @Override
        public void getNBTData(CompoundNBT nbt, ServerPlayerEntity player, World world, TileEntity tile) {
            if (tile instanceof IAutoCraft) {
                IAutoCraft auto = (IAutoCraft) tile;
                ItemStack output = auto.getCurrentRecipeOutput();
                if (!output.isEmpty()) {
                    nbt.put("recipe_result", output.serializeNBT());
                    List<ItemStack> stacks = CompatUtils.compactInventory(auto.getInvBlueprint());
                    ListNBT list = new ListNBT();

                    for (int index = 0; index < stacks.size(); ++index) {
                        list.add((stacks.get(index)).serializeNBT());
                    }

                    nbt.put("recipe_inputs", list);
                }
            }
        }
    }
}
