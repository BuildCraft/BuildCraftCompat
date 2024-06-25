package buildcraft.compat.module.ic2;

import buildcraft.compat.CompatModuleBase;
import buildcraft.lib.misc.StackMatchingPredicate;
import buildcraft.lib.misc.StackNbtMatcher;
import buildcraft.lib.misc.StackUtil;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class CompatModuleIndustrialCraft2 extends CompatModuleBase {
    public CompatModuleIndustrialCraft2() {
    }

    public String compatModId() {
        return "ic2";
    }

    public void preInit() {
        this.registerCableMatchingPredicate();
    }

    private void registerCableMatchingPredicate() {
//        Item cable = Item.func_111206_d("ic2:cable");
        Item cable = ForgeRegistries.ITEMS.getValue(new ResourceLocation("ic2:cable"));
        if (cable != null) {
            StackMatchingPredicate predicate = new StackNbtMatcher(new String[] { "insulation", "type" });
            StackUtil.registerMatchingPredicate(cable, predicate);
        }

    }
}
