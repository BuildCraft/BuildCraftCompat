package buildcraft.compat.module.forestry.pipe;

import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.ShapedOreRecipe;

import buildcraft.api.transport.pipe.PipeApi;
import buildcraft.api.transport.pipe.PipeDefinition;
import buildcraft.api.transport.pipe.PipeDefinition.PipeDefinitionBuilder;

import buildcraft.lib.misc.ColourUtil;
import buildcraft.lib.registry.CreativeTabManager;

public class ForestryPipes {

    public static Item pipeItemPropolis;
    public static PipeDefinition pipeDefinitionPropolis;

    public static void preInit() {
        MinecraftForge.EVENT_BUS.register(ForestryPipes.class);

        String[] textureSuffixes = new String[8];
        textureSuffixes[0] = "";
        textureSuffixes[7] = "_itemstack";
        for (EnumFacing face : EnumFacing.VALUES) {
            textureSuffixes[face.ordinal() + 1] = "_" + face.getName();
        }

        pipeDefinitionPropolis = new PipeDefinitionBuilder()//
            .id("forestry_propolis")// Note: id() automatically sets the namespace to "buildcraftcompat"
            .texPrefix("propolis")//
            .texSuffixes(textureSuffixes)//
            .logic(PipeBehaviourPropolis::new, PipeBehaviourPropolis::new)//
            .flowItem()//
            .define();

        PipeApi.pipeRegistry.createUnnamedItemForPipe(pipeDefinitionPropolis, item -> {
            pipeItemPropolis = item;
            item.setRegistryName("pipe_item_propolis");
            item.setUnlocalizedName("buildcraftPipe.pipeitemspropolis");
            item.setCreativeTab(CreativeTabManager.getTab("buildcraft.pipes"));
        });
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent<IRecipe> event) {
        Item propolis = ForgeRegistries.ITEMS.getValue(new ResourceLocation("forestry:propolis"));
        if (propolis != null && propolis != Items.AIR) {
            addPipeRecipe(pipeItemPropolis, propolis, Items.DIAMOND);
        }
    }

    private static void addPipeRecipe(Item pipe, Object surround) {
        addPipeRecipe(pipe, surround, surround);
    }

    private static void addPipeRecipe(Item pipe, Object left, Object right) {
        // Copied directly from BCTransportRecipes
        if (pipe == null) {
            return;
        }
        ItemStack result = new ItemStack(pipe, 8);
        IRecipe recipe = new ShapedOreRecipe(pipe.getRegistryName(), result, "lgr", 'l', left, 'r', right, 'g',
            "blockGlassColorless");
        recipe.setRegistryName(new ResourceLocation(pipe.getRegistryName() + "_colorless"));
        ForgeRegistries.RECIPES.register(recipe);

        for (EnumDyeColor colour : EnumDyeColor.values()) {
            ItemStack resultStack = new ItemStack(pipe, 8, colour.getMetadata() + 1);
            IRecipe colorRecipe = new ShapedOreRecipe(pipe.getRegistryName(), resultStack, "lgr", 'l', left, 'r', right,
                'g', "blockGlass" + ColourUtil.getName(colour));
            colorRecipe.setRegistryName(new ResourceLocation(pipe.getRegistryName() + "_" + colour));
            ForgeRegistries.RECIPES.register(colorRecipe);
        }
    }
}
