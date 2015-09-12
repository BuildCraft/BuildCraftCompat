package buildcraft.compat;

import buildcraft.api.crops.CropManager;
import buildcraft.compat.forestry.CropHandlerForestry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.client.MinecraftForgeClient;

import buildcraft.BuildCraftTransport;
import buildcraft.compat.forestry.pipes.PipeItemsPropolis;
import buildcraft.compat.forestry.schematics.SchematicForestryFarmBlock;
import buildcraft.compat.forestry.schematics.SchematicForestryWorktable;
import buildcraft.compat.forestry.schematics.SchematicTileForestry;
import buildcraft.compat.forestry.schematics.SchematicTileStairsForestry;
import buildcraft.core.BCCreativeTab;
import buildcraft.transport.BlockGenericPipe;
import buildcraft.transport.TransportProxyClient;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAlleleRegistry;

public class CompatModuleForestry extends CompatModuleBase
{
    public static IBeeRoot beeRoot;

    /** Pipe used to sort bees from Forestry. */
    private static Item pipeItemsPropolis;
    
    @Override
    public String name() {
        return "Forestry";
    }

    @Override
    public boolean canLoad() {
        return super.canLoad();
    }

    @Override
    public void preInit() {
        if (Loader.isModLoaded("BuildCraft|Transport")) {
            preInitTransport();
        }
    }

    @Override
    public void init() {
        CropManager.registerHandler(new CropHandlerForestry());

        if (Loader.isModLoaded("BuildCraft|Builders")) {
            initBuilders();
        }
    }

    @Override
    public void postInit() {
        CropHandlerForestry.postInit();
    }

    @Optional.Method(modid = "BuildCraft|Transport")
    private void preInitTransport() {
        IAlleleRegistry alleleRegistry = AlleleManager.alleleRegistry;
        if (alleleRegistry == null) {
            return;
        }

        beeRoot = (IBeeRoot) alleleRegistry.getSpeciesRoot("rootBees");
        if (beeRoot == null) {
            return;
        }

        ItemStack propolis = GameRegistry.findItemStack(name(), "propolis", 1);

        if (propolis != null && propolis.getItem() != null) {
            pipeItemsPropolis = BlockGenericPipe.registerPipe(PipeItemsPropolis.class, BCCreativeTab.get("pipes"));

            if (FMLCommonHandler.instance().getSide().isClient()) {
                MinecraftForgeClient.registerItemRenderer(CompatModuleForestry.pipeItemsPropolis, TransportProxyClient.pipeItemRenderer);
            }

            GameRegistry.addRecipe(new ItemStack(pipeItemsPropolis), "#X#", '#', propolis, 'X', BuildCraftTransport.pipeItemsDiamond);
        }
    }

    @Optional.Method(modid = "BuildCraft|Builders")
    private void initBuilders() {
        CompatUtils.registerSchematic("Forestry:engine", SchematicTileForestry.class);
        CompatUtils.registerSchematic("Forestry:factory2", 0, SchematicTileForestry.class); // Th. Fabricator
        CompatUtils.registerSchematic("Forestry:factory2", 1, SchematicTileForestry.class); // Raintank
        CompatUtils.registerSchematic("Forestry:factory2", 2, SchematicForestryWorktable.class); // Worktable
        CompatUtils.registerSchematic("Forestry:factory", 0, 7, SchematicTileForestry.class);
        CompatUtils.registerSchematic("Forestry:ffarm", 0, 5, SchematicForestryFarmBlock.class);
        CompatUtils.registerSchematic("Forestry:stairs", SchematicTileStairsForestry.class);
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
