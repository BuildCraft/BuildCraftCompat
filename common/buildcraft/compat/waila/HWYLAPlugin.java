package buildcraft.compat.waila;

import net.minecraft.item.ItemStack;
import buildcraft.api.mj.ILaserTarget;
import buildcraft.factory.util.IAutoCraft;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.api.SpecialChars;
import mcp.mobius.waila.api.WailaPlugin;

@WailaPlugin
public class HWYLAPlugin implements IWailaPlugin {
    @Override
    public void register(IWailaRegistrar registrar) {
        IWailaDataProvider autoCraftProvider = new AutoCraftDataProvider();
        registrar.registerNBTProvider(autoCraftProvider, IAutoCraft.class);
        registrar.registerBodyProvider(autoCraftProvider, IAutoCraft.class);

        IWailaDataProvider laserTargetProvider = new LaserTargetDataProvider();
        registrar.registerNBTProvider(laserTargetProvider, ILaserTarget.class);
        registrar.registerBodyProvider(laserTargetProvider, ILaserTarget.class);
    }

    static String getItemStackString(ItemStack stack) {
        return getItemStackString(stack, "1");
    }

    private static String getItemStackString(ItemStack stack, String thing) {
        // TODO: find out what that 'thing' really is
        return SpecialChars.getRenderString("waila.stack", thing,
                stack.getItem().getRegistryName().toString(),
                String.valueOf(stack.getCount()),
                String.valueOf(stack.getItemDamage())
        );
    }
}
