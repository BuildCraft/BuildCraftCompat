package buildcraft.compat.module.waila;

import buildcraft.lib.block.BlockBCTile_Neptune;
import buildcraft.lib.tile.TileBC_Neptune;
import mcp.mobius.waila.api.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

@WailaPlugin
public class HWYLAPlugin implements IWailaPlugin {
    // Calen: in 1.18.2 we should use Block or TE class instead of interface here, it's too difficult to get all matched if allowing some BC modules absent,
    // so just use TileBC_Neptune and BlockBCTile_Neptune

    @Override
    public void register(IRegistrar registrar) {
        IServerDataProvider<TileEntity> autoCraftNbtProvider = new AutoCraftDataProvider.NBTProvider();
        IServerDataProvider<TileEntity> laserTargetNbtProvider = new LaserTargetDataProvider.NBTProvider();
        IServerDataProvider<TileEntity> assemblyCraftNbtProvider = new AssemblyCraftDataProvider.NBTProvider();

        registrar.registerBlockDataProvider(autoCraftNbtProvider, TileBC_Neptune.class);
        registrar.registerBlockDataProvider(laserTargetNbtProvider, TileBC_Neptune.class);
        registrar.registerBlockDataProvider(assemblyCraftNbtProvider, TileBC_Neptune.class);

        IComponentProvider autoCraftBodyProvider = new AutoCraftDataProvider.BodyProvider();
        IComponentProvider laserTargetBodyProvider = new LaserTargetDataProvider.BodyProvider();
        IComponentProvider assemblyCraftBodyProvider = new AssemblyCraftDataProvider.BodyProvider();

        registrar.registerComponentProvider(autoCraftBodyProvider, TooltipPosition.BODY, BlockBCTile_Neptune.class);
        registrar.registerComponentProvider(laserTargetBodyProvider, TooltipPosition.BODY, BlockBCTile_Neptune.class);
        registrar.registerComponentProvider(assemblyCraftBodyProvider, TooltipPosition.BODY, BlockBCTile_Neptune.class);
    }

    static ITextComponent getItemStackString(ItemStack stack) {
        return getItemStackString(stack, "1");
    }

    private static ITextComponent getItemStackString(ItemStack stack, String thing) {
        // TODO: find out what that 'thing' really is
//        return SpecialChars.getRenderString("waila.stack", thing,
//                stack.getItem().getRegistryName().toString(),
//                String.valueOf(stack.getCount()),
//                String.valueOf(stack.getDamageValue())
//        );
        return new StringTextComponent("").append(stack.getDisplayName()).append(" x " + stack.getCount());
    }
}
