/**
 *  Copyright (C) 2013  emris
 *  https://github.com/emris/BCTFCcrossover
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package buildcraft.compat.tfc;

import com.bioxx.tfc.TFCItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import buildcraft.BuildCraftCore;
import buildcraft.BuildCraftEnergy;
import buildcraft.BuildCraftTransport;
import buildcraft.compat.CompatModule;
import buildcraft.compat.tfc.blocks.BlockFluid;
import buildcraft.compat.tfc.items.ItemBlocks;
import buildcraft.compat.tfc.items.ItemBuckets;
import buildcraft.compat.tfc.items.ItemGears;
import buildcraft.compat.tfc.items.ItemLatexBowl;
import buildcraft.compat.tfc.items.ItemPipeFrames;
import buildcraft.compat.tfc.items.ItemRubber;
import buildcraft.compat.tfc.items.ItemWrenchGeneral;
import buildcraft.compat.tfc.pipes.fluids.BlackBronzePipeFluidsItem;
import buildcraft.compat.tfc.pipes.fluids.BronzePipeFluidsItem;
import buildcraft.compat.tfc.pipes.fluids.LeadPipeFluidsItem;
import buildcraft.compat.tfc.pipes.fluids.RedSteelPipeFluidsItem;
import buildcraft.compat.tfc.pipes.fluids.RoseGoldPipeFluidsItem;
import buildcraft.compat.tfc.pipes.fluids.SteelPipeFluidsItem;
import buildcraft.compat.tfc.pipes.fluids.TinPipeFluidsItem;
import buildcraft.compat.tfc.pipes.fluids.WroughtIronPipeFluidsItem;
import buildcraft.compat.tfc.pipes.power.BlackBronzePipePowerItem;
import buildcraft.compat.tfc.pipes.power.CopperPipePowerItem;
import buildcraft.compat.tfc.pipes.power.PlatinumPipePowerItem;
import buildcraft.compat.tfc.pipes.power.RoseGoldPipePowerItem;
import buildcraft.compat.tfc.pipes.power.SilverPipePowerItem;
import buildcraft.compat.tfc.pipes.power.SteelPipePowerItem;
import buildcraft.compat.tfc.pipes.power.WroughtIronPipePowerItem;
import buildcraft.compat.tfc.pipes.structure.LeadPipeStructureItem;
import buildcraft.compat.tfc.pipes.transport.BlackBronzePipeItem;
import buildcraft.compat.tfc.pipes.transport.BlackSteelPipeItem;
import buildcraft.compat.tfc.pipes.transport.BlueSteelPipeItem;
import buildcraft.compat.tfc.pipes.transport.BrassPipeItem;
import buildcraft.compat.tfc.pipes.transport.BronzePipeItem;
import buildcraft.compat.tfc.pipes.transport.LeadPipeItem;
import buildcraft.compat.tfc.pipes.transport.RedSteelPipeItem;
import buildcraft.compat.tfc.pipes.transport.RoseGoldPipeItem;
import buildcraft.compat.tfc.pipes.transport.SteelPipeItem;
import buildcraft.compat.tfc.pipes.transport.SterlingSilverPipeItem;
import buildcraft.compat.tfc.pipes.transport.TinPipeItem;
import buildcraft.compat.tfc.pipes.transport.WroughtIronPipeItem;
import buildcraft.core.BlockSpring;
import buildcraft.core.CreativeTabBuildCraft;
import buildcraft.transport.BlockGenericPipe;
import buildcraft.transport.ItemPipe;
import buildcraft.transport.Pipe;
import buildcraft.transport.PipeTransportFluids;
import buildcraft.transport.PipeTransportPower;
import buildcraft.transport.TransportProxyClient;
import buildcraft.transport.pipes.PipeFluidsCobblestone;
import buildcraft.transport.pipes.PipeFluidsDiamond;
import buildcraft.transport.pipes.PipeFluidsEmerald;
import buildcraft.transport.pipes.PipeFluidsGold;
import buildcraft.transport.pipes.PipeFluidsIron;
import buildcraft.transport.pipes.PipeFluidsQuartz;
import buildcraft.transport.pipes.PipeFluidsSandstone;
import buildcraft.transport.pipes.PipeFluidsStone;
import buildcraft.transport.pipes.PipeFluidsVoid;
import buildcraft.transport.pipes.PipeFluidsWood;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class TFCCompat extends CompatModule {
	public static Fluid fluidLatex;
	public static Block blockLatex;
	public static Item LatexBowl;
	public static Item Buckets;
	public static Item Rubber;
	public static Item Gears;
	public static Item Plans;
	public static Item PipeFrames;
	public static Item TinPipeItem;
	public static Item LeadPipeItem;
	public static Item BronzePipeItem;
	public static Item WroughtIronPipeItem;
	public static Item SteelPipeItem;
	public static Item BlueSteelPipeItem;
	public static Item RedSteelPipeItem;
	public static Item BlackBronzePipeItem;
	public static Item RoseGoldPipeItem;
	public static Item BlackSteelPipeItem;
	public static Item SterlingSilverPipeItem;
	public static Item BrassPipeItem;
	public static Item TinPipeFluidsItem;
	public static Item LeadPipeFluidsItem;
	public static Item BronzePipeFluidsItem;
	public static Item WroughtIronPipeFluidsItem;
	public static Item SteelPipeFluidsItem;
	public static Item RedSteelPipeFluidsItem;
	public static Item BlackBronzePipeFluidsItem;
	public static Item RoseGoldPipeFluidsItem;
	public static Item CopperPipePowerItem;
	public static Item RoseGoldPipePowerItem;
	public static Item BlackBronzePipePowerItem;
	public static Item WroughtIronPipePowerItem;
	public static Item SteelPipePowerItem;
	public static Item PlatinumPipePowerItem;
	public static Item SilverPipePowerItem;
	public static Item LeadPipeStructureItem;
	public static Item CopperWrenchItem;
	public static Item BronzeWrenchItem;
	public static Item BismuthBronzeWrenchItem;
	public static Item BlackBronzeWrenchItem;
	public static Item WroughtIronWrenchItem;
	public static Item SteelWrenchItem;
	public static Item BlackSteelWrenchItem;
	public static Item BlueSteelWrenchItem;
	public static Item RedSteelWrenchItem;

	public static TFCCompat instance;
	public static PipeIconProvider pipeIconProvider = new PipeIconProvider();

	@Override
	public String getModID() {
		return "terrafirmacraft";
	}

	@Override
	public String getConfigDescription() {
		return "Support of TFC's crafting system, recipes, ores, etc. Total conversion.";
	}
	
	private void registerItem(Item item) {
		GameRegistry.registerItem(item, item.getUnlocalizedName().replace("item.", ""));
	}

	@Override
	public void preInit() {
		instance = this;

		fluidLatex = new Fluid("latex").setUnlocalizedName("Latex").setDensity(6000).setViscosity(6000);
		FluidRegistry.registerFluid(fluidLatex);
		blockLatex = new BlockFluid(fluidLatex, Material.water, MapColor.snowColor).setFlammable(true).setFlammability(5).setParticleColor(1F, 1F, 1F).setQuantaPerBlock(3);
		GameRegistry.registerBlock(TFCCompat.blockLatex, ItemBlocks.class, "Latex");

		TFCCompat.Gears = new ItemGears();
		registerItem(TFCCompat.Gears);

		TFCCompat.Buckets = new ItemBuckets();
		registerItem(TFCCompat.Buckets);

		TFCCompat.LatexBowl = new ItemLatexBowl();
		registerItem(TFCCompat.LatexBowl);

		TFCCompat.Rubber = new ItemRubber();
		registerItem(TFCCompat.Rubber);

		TFCCompat.PipeFrames = new ItemPipeFrames();
		registerItem(TFCCompat.PipeFrames);

		PipeTransportFluids.fluidCapacities.put(LeadPipeFluidsItem.class, Integer.valueOf(1 * BuildCraftTransport.pipeFluidsBaseFlowRate));
		//PipeTransportFluids.fluidCapacities.put(PipeFluidsDiamond.class, Integer.valueOf(8 * BuildCraftTransport.pipeFluidsBaseFlowRate));
		PipeTransportFluids.fluidCapacities.put(RedSteelPipeFluidsItem.class, Integer.valueOf(4 * BuildCraftTransport.pipeFluidsBaseFlowRate));
		PipeTransportFluids.fluidCapacities.put(SteelPipeFluidsItem.class, Integer.valueOf(8 * BuildCraftTransport.pipeFluidsBaseFlowRate));
		PipeTransportFluids.fluidCapacities.put(WroughtIronPipeFluidsItem.class, Integer.valueOf(4 * BuildCraftTransport.pipeFluidsBaseFlowRate));
		//PipeTransportFluids.fluidCapacities.put(PipeFluidsQuartz.class, Integer.valueOf(4 * BuildCraftTransport.pipeFluidsBaseFlowRate));
		PipeTransportFluids.fluidCapacities.put(RoseGoldPipeFluidsItem.class, Integer.valueOf(2 * BuildCraftTransport.pipeFluidsBaseFlowRate));
		PipeTransportFluids.fluidCapacities.put(BronzePipeFluidsItem.class, Integer.valueOf(2 * BuildCraftTransport.pipeFluidsBaseFlowRate));
		PipeTransportFluids.fluidCapacities.put(BlackBronzePipeFluidsItem.class, Integer.valueOf(1 * BuildCraftTransport.pipeFluidsBaseFlowRate));
		PipeTransportFluids.fluidCapacities.put(TinPipeFluidsItem.class, Integer.valueOf(1 * BuildCraftTransport.pipeFluidsBaseFlowRate));

		PipeTransportPower.powerCapacities.put(CopperPipePowerItem.class, 80);
		PipeTransportPower.powerCapacities.put(RoseGoldPipePowerItem.class, 160);
		PipeTransportPower.powerCapacities.put(BlackBronzePipePowerItem.class, 320);
		PipeTransportPower.powerCapacities.put(WroughtIronPipePowerItem.class, 640);
		PipeTransportPower.powerCapacities.put(SteelPipePowerItem.class, 1280);
		PipeTransportPower.powerCapacities.put(PlatinumPipePowerItem.class, 2560);
		PipeTransportPower.powerCapacities.put(SilverPipePowerItem.class, 10240);

		TFCCompat.TinPipeItem = createPipe(buildcraft.compat.tfc.pipes.transport.TinPipeItem.class, "Tin Transport Pipe", CreativeTabBuildCraft.PIPES);
		TFCCompat.LeadPipeItem = createPipe(buildcraft.compat.tfc.pipes.transport.LeadPipeItem.class, "Lead Transport Pipe", CreativeTabBuildCraft.PIPES);
		TFCCompat.BronzePipeItem = createPipe(buildcraft.compat.tfc.pipes.transport.BronzePipeItem.class, "Bronze Transport Pipe", CreativeTabBuildCraft.PIPES);
		TFCCompat.WroughtIronPipeItem = createPipe(buildcraft.compat.tfc.pipes.transport.WroughtIronPipeItem.class, "Wrought Iron Transport Pipe", CreativeTabBuildCraft.PIPES);
		TFCCompat.SteelPipeItem = createPipe(buildcraft.compat.tfc.pipes.transport.SteelPipeItem.class, "Steel Transport Pipe", CreativeTabBuildCraft.PIPES);
		TFCCompat.BlueSteelPipeItem = createPipe(buildcraft.compat.tfc.pipes.transport.BlueSteelPipeItem.class, "Blue Steel Transport Pipe", CreativeTabBuildCraft.PIPES);
		TFCCompat.RedSteelPipeItem = createPipe(buildcraft.compat.tfc.pipes.transport.RedSteelPipeItem.class, "Red Steel Transport Pipe", CreativeTabBuildCraft.PIPES);
		TFCCompat.BlackBronzePipeItem = createPipe(buildcraft.compat.tfc.pipes.transport.BlackBronzePipeItem.class, "Black Bronze Transport Pipe", CreativeTabBuildCraft.PIPES);
		TFCCompat.RoseGoldPipeItem = createPipe(buildcraft.compat.tfc.pipes.transport.RoseGoldPipeItem.class, "Rose Gold Transport Pipe", CreativeTabBuildCraft.PIPES);
		TFCCompat.BlackSteelPipeItem = createPipe(buildcraft.compat.tfc.pipes.transport.BlackSteelPipeItem.class, "Black Steel Transport Pipe", CreativeTabBuildCraft.PIPES);
		TFCCompat.SterlingSilverPipeItem = createPipe(buildcraft.compat.tfc.pipes.transport.SterlingSilverPipeItem.class, "Sterling Silver Transport Pipe", CreativeTabBuildCraft.PIPES);
		TFCCompat.BrassPipeItem = createPipe(buildcraft.compat.tfc.pipes.transport.BrassPipeItem.class, "Brass Transport Pipe", CreativeTabBuildCraft.PIPES);
		//BuildCraftTransport.pipeItemsQuartz
		//TODO
		//BuildCraftTransport.pipeItemsEmzuli
		//TODO
		//BuildCraftTransport.pipeItemsClay
		//TODO

		TFCCompat.TinPipeFluidsItem = createPipe(buildcraft.compat.tfc.pipes.fluids.TinPipeFluidsItem.class, "Tin Fluid Pipe", CreativeTabBuildCraft.PIPES); // Wood
		TFCCompat.LeadPipeFluidsItem = createPipe(buildcraft.compat.tfc.pipes.fluids.LeadPipeFluidsItem.class, "Lead Fluid Pipe", CreativeTabBuildCraft.PIPES); // Cobblestone
		TFCCompat.BronzePipeFluidsItem = createPipe(buildcraft.compat.tfc.pipes.fluids.BronzePipeFluidsItem.class, "Bronze Fluid Pipe", CreativeTabBuildCraft.PIPES); // Stone
		TFCCompat.WroughtIronPipeFluidsItem = createPipe(buildcraft.compat.tfc.pipes.fluids.WroughtIronPipeFluidsItem.class, "Wrought Iron Fluid Pipe", CreativeTabBuildCraft.PIPES); // Iron
		TFCCompat.SteelPipeFluidsItem = createPipe(buildcraft.compat.tfc.pipes.fluids.SteelPipeFluidsItem.class, "Steel Fluid Pipe", CreativeTabBuildCraft.PIPES); // Gold
		TFCCompat.RedSteelPipeFluidsItem = createPipe(buildcraft.compat.tfc.pipes.fluids.RedSteelPipeFluidsItem.class, "Red Steel Fluid Pipe", CreativeTabBuildCraft.PIPES); // Emerald
		TFCCompat.BlackBronzePipeFluidsItem = createPipe(buildcraft.compat.tfc.pipes.fluids.BlackBronzePipeFluidsItem.class, "Black Bronze Fluid Pipe", CreativeTabBuildCraft.PIPES); // Void
		TFCCompat.RoseGoldPipeFluidsItem = createPipe(buildcraft.compat.tfc.pipes.fluids.RoseGoldPipeFluidsItem.class, "Rose Gold Fluid Pipe", CreativeTabBuildCraft.PIPES); // Sandstone
		//BuildCraftTransport.pipeFluidsDiamond
		//TODO

		// ===============Power Pipes===============
		TFCCompat.CopperPipePowerItem = createPipe(CopperPipePowerItem.class, "Copper Kinesis Pipe", CreativeTabBuildCraft.PIPES); // Cobblestone
		TFCCompat.RoseGoldPipePowerItem = createPipe(RoseGoldPipePowerItem.class, "Rose Gold Kinesis Pipe", CreativeTabBuildCraft.PIPES); // Stone
		TFCCompat.BlackBronzePipePowerItem = createPipe(BlackBronzePipePowerItem.class, "Black Bronze Kinesis Pipe", CreativeTabBuildCraft.PIPES); // Wood
		TFCCompat.WroughtIronPipePowerItem = createPipe(WroughtIronPipePowerItem.class, "Wrought Iron Kinesis Pipe", CreativeTabBuildCraft.PIPES); // Quartz
		TFCCompat.SteelPipePowerItem = createPipe(SteelPipePowerItem.class, "Steel Kinesis Pipe", CreativeTabBuildCraft.PIPES); // Iron
		TFCCompat.PlatinumPipePowerItem = createPipe(PlatinumPipePowerItem.class, "Platinum Kinesis Pipe", CreativeTabBuildCraft.PIPES); // Gold
		TFCCompat.SilverPipePowerItem = createPipe(SilverPipePowerItem.class, "Silver Kinesis Pipe", CreativeTabBuildCraft.PIPES); // Diamond
		//BuildCraftTransport.pipePowerEmerald
		//TODO

		// ===============Structure Pipes===============
		TFCCompat.LeadPipeStructureItem = createPipe(buildcraft.compat.tfc.pipes.structure.LeadPipeStructureItem.class, "Structure Pipe", CreativeTabBuildCraft.PIPES);

		// ===============Wrenches===============
		TFCCompat.CopperWrenchItem = new ItemWrenchGeneral().setUnlocalizedName("CopperWrenchItem");
		registerItem(TFCCompat.CopperWrenchItem);

		TFCCompat.BismuthBronzeWrenchItem = new ItemWrenchGeneral().setUnlocalizedName("BismuthBronzeWrenchItem");
		registerItem(TFCCompat.BismuthBronzeWrenchItem);

		TFCCompat.BlackBronzeWrenchItem = new ItemWrenchGeneral().setUnlocalizedName("BlackBronzeWrenchItem");
		registerItem(TFCCompat.BlackBronzeWrenchItem);

		TFCCompat.BronzeWrenchItem = new ItemWrenchGeneral().setUnlocalizedName("BronzeWrenchItem");
		registerItem(TFCCompat.BronzeWrenchItem);

		TFCCompat.WroughtIronWrenchItem = new ItemWrenchGeneral().setUnlocalizedName("WroughtIronWrenchItem");
		registerItem(TFCCompat.WroughtIronWrenchItem);

		TFCCompat.SteelWrenchItem = new ItemWrenchGeneral().setUnlocalizedName("SteelWrenchItem");
		registerItem(TFCCompat.SteelWrenchItem);

		TFCCompat.BlackSteelWrenchItem = new ItemWrenchGeneral().setUnlocalizedName("BlackSteelWrenchItem");
		registerItem(TFCCompat.BlackSteelWrenchItem);

		TFCCompat.BlueSteelWrenchItem = new ItemWrenchGeneral().setUnlocalizedName("BlueSteelWrenchItem");
		registerItem(TFCCompat.BlueSteelWrenchItem);

		TFCCompat.RedSteelWrenchItem = new ItemWrenchGeneral().setUnlocalizedName("RedSteelWrenchItem");
		registerItem(TFCCompat.RedSteelWrenchItem);

		BlockSpring.EnumSpring.OIL.canGen = false;
		BlockSpring.EnumSpring.WATER.canGen = false;
		BuildCraftEnergy.biomeOilDesert = null;
		BuildCraftEnergy.biomeOilOcean = null;
		BuildCraftEnergy.spawnOilSprings = false;
		GameRegistry.registerWorldGenerator(new WorldGenOil(), 0);
	}

	@Override
	public void init() {
		registerOreDictionary();
		removeFromCreativeTab();
		Recipes.loadRecipes();

		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("latex", FluidContainerRegistry.BUCKET_VOLUME / 4), new ItemStack(LatexBowl), new ItemStack(Items.bowl));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("latex", FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(Buckets, 1, 0), new ItemStack(TFCItems.WoodenBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("saltwater", FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(Buckets, 1, 2), new ItemStack(Buckets, 1, 1));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("freshwater", FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(Buckets, 1, 3), new ItemStack(Buckets, 1, 1));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("hotwater", FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(Buckets, 1, 4), new ItemStack(Buckets, 1, 1));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("oil", FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(Buckets, 1, 6), new ItemStack(Buckets, 1, 5));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("fuel", FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(Buckets, 1, 7), new ItemStack(Buckets, 1, 5));

		FMLCommonHandler.instance().bus().register(new TFCCraftingHandler());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initClient() {
		MinecraftForgeClient.registerItemRenderer(TinPipeItem, TransportProxyClient.pipeItemRenderer);
		MinecraftForgeClient.registerItemRenderer(LeadPipeItem, TransportProxyClient.pipeItemRenderer);
		MinecraftForgeClient.registerItemRenderer(BronzePipeItem, TransportProxyClient.pipeItemRenderer);
		MinecraftForgeClient.registerItemRenderer(WroughtIronPipeItem, TransportProxyClient.pipeItemRenderer);
		MinecraftForgeClient.registerItemRenderer(SteelPipeItem, TransportProxyClient.pipeItemRenderer);
		MinecraftForgeClient.registerItemRenderer(BlueSteelPipeItem, TransportProxyClient.pipeItemRenderer);
		MinecraftForgeClient.registerItemRenderer(RedSteelPipeItem, TransportProxyClient.pipeItemRenderer);
		MinecraftForgeClient.registerItemRenderer(BlackBronzePipeItem, TransportProxyClient.pipeItemRenderer);
		MinecraftForgeClient.registerItemRenderer(RoseGoldPipeItem, TransportProxyClient.pipeItemRenderer);
		MinecraftForgeClient.registerItemRenderer(BlackSteelPipeItem, TransportProxyClient.pipeItemRenderer);
		MinecraftForgeClient.registerItemRenderer(SterlingSilverPipeItem, TransportProxyClient.pipeItemRenderer);
		MinecraftForgeClient.registerItemRenderer(BrassPipeItem, TransportProxyClient.pipeItemRenderer);
		// Structure Pipes
		MinecraftForgeClient.registerItemRenderer(LeadPipeStructureItem, TransportProxyClient.pipeItemRenderer);
		// Liquid Pipes
		MinecraftForgeClient.registerItemRenderer(TinPipeFluidsItem, TransportProxyClient.pipeItemRenderer);
		MinecraftForgeClient.registerItemRenderer(LeadPipeFluidsItem, TransportProxyClient.pipeItemRenderer);
		MinecraftForgeClient.registerItemRenderer(BronzePipeFluidsItem, TransportProxyClient.pipeItemRenderer);
		MinecraftForgeClient.registerItemRenderer(WroughtIronPipeFluidsItem, TransportProxyClient.pipeItemRenderer);
		MinecraftForgeClient.registerItemRenderer(SteelPipeFluidsItem, TransportProxyClient.pipeItemRenderer);
		MinecraftForgeClient.registerItemRenderer(RedSteelPipeFluidsItem, TransportProxyClient.pipeItemRenderer);
		MinecraftForgeClient.registerItemRenderer(BlackBronzePipeFluidsItem, TransportProxyClient.pipeItemRenderer);
		MinecraftForgeClient.registerItemRenderer(RoseGoldPipeFluidsItem, TransportProxyClient.pipeItemRenderer);
		// Conductive Pipes
		MinecraftForgeClient.registerItemRenderer(CopperPipePowerItem, TransportProxyClient.pipeItemRenderer);
		MinecraftForgeClient.registerItemRenderer(RoseGoldPipePowerItem, TransportProxyClient.pipeItemRenderer);
		MinecraftForgeClient.registerItemRenderer(BlackBronzePipePowerItem, TransportProxyClient.pipeItemRenderer);
		MinecraftForgeClient.registerItemRenderer(WroughtIronPipePowerItem, TransportProxyClient.pipeItemRenderer);
		MinecraftForgeClient.registerItemRenderer(SteelPipePowerItem, TransportProxyClient.pipeItemRenderer);
		MinecraftForgeClient.registerItemRenderer(PlatinumPipePowerItem, TransportProxyClient.pipeItemRenderer);
		MinecraftForgeClient.registerItemRenderer(SilverPipePowerItem, TransportProxyClient.pipeItemRenderer);

		fluidLatex.setIcons(blockLatex.getIcon(0, 0), blockLatex.getIcon(1, 0));
	}

	private void registerOreDictionary() {
		String[] rubberNames = new String[]{"rubberBlack", "rubberRed", "rubberGreen",
				"rubberBrown","rubberBlue", "rubberPurple", "rubberCyan", "rubberLightGray",
				"rubberGray", "rubberPink", "rubberLimeGreen", "rubberYellow", "rubberLightBlue",
				"rubberMagenta", "rubberOrange", "rubberWhite"};
		for (int i = 0; i < rubberNames.length; ++i)
			OreDictionary.registerOre(rubberNames[i], new ItemStack(Rubber, 1, i));

		String[] woodBucketNames = new String[]{"bucketLatex","bucketZinc","bucketZincWater",
				"bucketSteel", "bucketSteelOil","bucketSteelFuel"};
		for (int i = 0; i < woodBucketNames.length; ++i)
			OreDictionary.registerOre(woodBucketNames[i], new ItemStack(Buckets, 1, i));

		String[] pipeFrameNames = new String[]{"pipeFrameTin", "pipeFrameLead", "pipeFrameBronze",
				"pipeFrameWroughtIron", "pipeFrameSteel", "pipeFrameBlueSteel", "pipeFrameRedSteel",
				"pipeFrameBlackBronze", "pipeFrameRoseGold", "pipeFrameBlackSteel", "pipeFrameZinc",
				"pipeFrameCopper", "pipeFrameSilver", "pipeFramePlatinum", "pipeFrameSterlingSilver",
				"pipeFrameBrass"};
		for (int i = 0; i < pipeFrameNames.length; ++i)
			OreDictionary.registerOre(pipeFrameNames[i], new ItemStack(PipeFrames, 1, i));

		String[] gearNames= new String[]{"gearBismuthBronze", "gearBlackBronze", "gearBronze", "gearCopper",
				"gearWroughtIron", "gearSteel", "gearBlackSteel", "gearBlueSteel", "gearRedSteel"};
		for (int i = 0; i < gearNames.length; ++i)
			OreDictionary.registerOre(gearNames[i], new ItemStack(Gears, 1, i));

		OreDictionary.registerOre("bowlLatex", new ItemStack(LatexBowl, 1, 0));
	}

	private void removeFromCreativeTab()
	{
		// Remove BC gears
		BuildCraftCore.woodenGearItem.setCreativeTab(null);
		BuildCraftCore.stoneGearItem.setCreativeTab(null);
		BuildCraftCore.ironGearItem.setCreativeTab(null);
		BuildCraftCore.goldGearItem.setCreativeTab(null);
		BuildCraftCore.diamondGearItem.setCreativeTab(null);

		// Remove BC buckets
		BuildCraftEnergy.bucketFuel.setCreativeTab(null);
		BuildCraftEnergy.bucketOil.setCreativeTab(null);

		// Remove BC transport pipes
		BuildCraftTransport.pipeItemsWood.setCreativeTab(null);
		BuildCraftTransport.pipeItemsCobblestone.setCreativeTab(null);
		BuildCraftTransport.pipeItemsStone.setCreativeTab(null);
		BuildCraftTransport.pipeItemsIron.setCreativeTab(null);
		BuildCraftTransport.pipeItemsGold.setCreativeTab(null);
		BuildCraftTransport.pipeItemsDiamond.setCreativeTab(null);
		BuildCraftTransport.pipeItemsEmerald.setCreativeTab(null);
		BuildCraftTransport.pipeItemsVoid.setCreativeTab(null);
		BuildCraftTransport.pipeItemsSandstone.setCreativeTab(null);
		BuildCraftTransport.pipeItemsObsidian.setCreativeTab(null);
		BuildCraftTransport.pipeItemsDaizuli.setCreativeTab(null);
		BuildCraftTransport.pipeItemsLapis.setCreativeTab(null);
		BuildCraftTransport.pipeItemsQuartz.setCreativeTab(null);
		BuildCraftTransport.pipeItemsEmzuli.setCreativeTab(null);

		// Remove BC fluid pipes
		BuildCraftTransport.pipeFluidsWood.setCreativeTab(null);
		BuildCraftTransport.pipeFluidsCobblestone.setCreativeTab(null);
		BuildCraftTransport.pipeFluidsStone.setCreativeTab(null);
		BuildCraftTransport.pipeFluidsIron.setCreativeTab(null);
		BuildCraftTransport.pipeFluidsGold.setCreativeTab(null);
		BuildCraftTransport.pipeFluidsEmerald.setCreativeTab(null);
		BuildCraftTransport.pipeFluidsVoid.setCreativeTab(null);
		BuildCraftTransport.pipeFluidsSandstone.setCreativeTab(null);

		// Remove BC kinesis pipes
		BuildCraftTransport.pipePowerCobblestone.setCreativeTab(null);
		BuildCraftTransport.pipePowerStone.setCreativeTab(null);
		BuildCraftTransport.pipePowerWood.setCreativeTab(null);
		BuildCraftTransport.pipePowerQuartz.setCreativeTab(null);
		BuildCraftTransport.pipePowerIron.setCreativeTab(null);
		BuildCraftTransport.pipePowerGold.setCreativeTab(null);
		BuildCraftTransport.pipePowerDiamond.setCreativeTab(null);

		// Remove BC structure pipe
		BuildCraftTransport.pipeStructureCobblestone.setCreativeTab(null);

		// Remove BC wranch item
		BuildCraftCore.wrenchItem.setCreativeTab(null);

		// Remove BC pipe sealant item
		BuildCraftTransport.pipeWaterproof.setCreativeTab(null);

		// Remove BC water and oil spring blocks
		BuildCraftCore.springBlock.setCreativeTab(null);
	}

	@SuppressWarnings("rawtypes")
	private static Item createPipe(Class<? extends Pipe> clas, String descr, CreativeTabBuildCraft creativeTab)
	{
		//String name = Character.toLowerCase(clas.getSimpleName().charAt(0)) + clas.getSimpleName().substring(1);
		ItemPipe res = BlockGenericPipe.registerPipe(clas, creativeTab);
		res.setUnlocalizedName(clas.getSimpleName());
		return res;
	}
}
