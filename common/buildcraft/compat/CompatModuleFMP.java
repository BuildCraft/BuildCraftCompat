package buildcraft.compat;

import codechicken.multipart.handler.MultipartProxy;

import net.minecraft.block.Block;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import buildcraft.api.blueprints.BuilderAPI;
import buildcraft.compat.multipart.MultipartSchematics;
import buildcraft.compat.multipart.SchematicMcMetaPart;
import buildcraft.compat.multipart.SchematicMicroblock;
import buildcraft.compat.multipart.SchematicMultipartBlock;

public class CompatModuleFMP extends CompatModuleBase {
	@Override
	public String name() {
		return "ForgeMultipart";
	}

	@Override
	public boolean canLoad() {
		return super.canLoad() && Loader.isModLoaded("BuildCraft|Builders");
	}

	@Override
	public void init() {
		BuilderAPI.schematicRegistry.registerSchematicBlock(getMultipartProxyBlock(), SchematicMultipartBlock.class);
		MultipartSchematics.registerSchematic("mcr_face", new SchematicMicroblock("mcr_face"));
		MultipartSchematics.registerSchematic("mcr_cnr", new SchematicMicroblock("mcr_cnr"));
		MultipartSchematics.registerSchematic("mcr_edge", new SchematicMicroblock("mcr_edge"));
		MultipartSchematics.registerSchematic("mcr_post", new SchematicMicroblock("mcr_post"));
		MultipartSchematics.registerSchematic("mcr_hllw", new SchematicMicroblock("mcr_hllw"));
		MultipartSchematics.registerSchematic("mc_torch", new SchematicMcMetaPart("mc_torch"));
		MultipartSchematics.registerSchematic("mc_lever", new SchematicMcMetaPart("mc_lever"));
		MultipartSchematics.registerSchematic("mc_button", new SchematicMcMetaPart("mc_button"));
		MultipartSchematics.registerSchematic("mc_redtorch", new SchematicMcMetaPart("mc_redtorch"));
	}

	@Optional.Method(modid = "ForgeMultipart")
	public Block getMultipartProxyBlock() {
		return MultipartProxy.block();
	}
}
