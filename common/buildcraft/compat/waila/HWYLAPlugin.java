package buildcraft.compat.waila;

import javax.annotation.Nonnull;
import net.minecraft.item.ItemStack;
import mcp.mobius.waila.api.IWailaBlockDecorator;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.api.WailaPlugin;

@WailaPlugin
public class HWYLAPlugin implements IWailaPlugin {
    @Override
    public void register(IWailaRegistrar registrar) {
        // registrar.registerDecorator(new CraftingTableDecorator(), );
    }

    class CraftingTableDecorator implements IWailaBlockDecorator {

        @Override
        public void decorateBlock(@Nonnull ItemStack itemStack, @Nonnull IWailaDataAccessor accessor, @Nonnull IWailaConfigHandler config) {

        }
    }
}
