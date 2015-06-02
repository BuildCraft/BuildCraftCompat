package buildcraft.compat;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.client.MinecraftForgeClient;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import cpw.mods.fml.common.registry.GameRegistry;

import forestry.api.core.ForestryAPI;

import buildcraft.BuildCraftTransport;
import buildcraft.compat.forestry.pipes.PipeItemsPropolis;
import buildcraft.core.BCCreativeTab;
import buildcraft.transport.BlockGenericPipe;
import buildcraft.transport.TransportProxyClient;

public class CompatModuleForestry extends CompatModuleBase
{
    /** Pipe used to sort bees from Forestry. */
    private static Item pipeItemsPropolis;
    
    @Override
    public String name() {
        return "Forestry";
    }

    @Override
    public boolean canLoad() {
        return super.canLoad() && ForestryAPI.enabledPlugins.contains("APICULTURE");
    }

    @Override
    public void init() {
        pipeItemsPropolis = BlockGenericPipe.registerPipe(PipeItemsPropolis.class, BCCreativeTab.get("pipes"));

        if (FMLCommonHandler.instance().getSide().isClient()) {
            MinecraftForgeClient.registerItemRenderer(CompatModuleForestry.pipeItemsPropolis, TransportProxyClient.pipeItemRenderer);
        }

        ItemStack propolis = GameRegistry.findItemStack(name(), "propolis", 1);
        GameRegistry.addRecipe(new ItemStack(pipeItemsPropolis), "#X#", '#', propolis, 'X', BuildCraftTransport.pipeItemsDiamond);
    }

    public static void missingMapping(FMLMissingMappingsEvent event) {
        for (FMLMissingMappingsEvent.MissingMapping mapping : event.getAll()) {
            if (mapping.type == GameRegistry.Type.ITEM) {
                if (mapping.name.equals("Forestry:item.PipeItemsPropolis")) {
                    mapping.remap(pipeItemsPropolis);
                }
            }
        }
    }
}
