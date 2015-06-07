package buildcraft.compat;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.event.FMLInterModComms;

import buildcraft.core.BlockEngine;

public class CompatModuleEnderIO extends CompatModuleBase
{
    @Override
    public String name() {
        return "EnderIO";
    }

    @Override
    public boolean canLoad() {
        return super.canLoad();
    }

    private void addConnectableBlock(Class<? extends Block> blockClass) {
        NBTTagCompound cpt = new NBTTagCompound();
        cpt.setString("className", blockClass.getName());
        FMLInterModComms.sendMessage(name(), "redstone:connectable:add", cpt);
    }

    @Override
    public void init() {
        addConnectableBlock(BlockEngine.class);
    }
}
