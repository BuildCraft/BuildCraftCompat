package buildcraft.compat.multipart;

import buildcraft.api.blueprints.BuilderAPI;
import buildcraft.api.blueprints.IBuilderContext;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;
import codechicken.multipart.handler.MultipartProxy;
import net.minecraft.tileentity.TileEntity;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

public class MultipartSchematics
{
    private static HashMap<String, SchematicPart> schematicMap = new HashMap<String, SchematicPart>();

    public static void registerSchematic(String type, SchematicPart schematic) {
        schematicMap.put(type, schematic);
    }

    public static LinkedList<Entry<TMultiPart, SchematicPart>> getSchematics(IBuilderContext context, int x, int y, int z) {
        LinkedList<Entry<TMultiPart, SchematicPart>> schematics = new LinkedList<Entry<TMultiPart, SchematicPart>>();
        TileEntity tile = context.world().getTileEntity(x, y, z);
        if(!(tile instanceof TileMultipart))
            return schematics;

        TileMultipart tileMultipart = (TileMultipart)tile;
        for(TMultiPart part : tileMultipart.jPartList()) {
            SchematicPart schematic = getSchematic(part.getType());
            if(schematic != null)
                schematics.add(new SimpleEntry<TMultiPart, SchematicPart>(part, schematic));
        }

        return schematics;
    }

    public static SchematicPart getSchematic(String type) {
        return schematicMap.get(type);
    }

    public static void init() {
        BuilderAPI.schematicRegistry.registerSchematicBlock(MultipartProxy.block(), SchematicMultipartBlock.class);
        registerSchematic("mcr_face", new SchematicMicroblock("mcr_face"));
        registerSchematic("mcr_cnr", new SchematicMicroblock("mcr_cnr"));
        registerSchematic("mcr_edge", new SchematicMicroblock("mcr_edge"));
        registerSchematic("mcr_post", new SchematicMicroblock("mcr_post"));
        registerSchematic("mcr_hllw", new SchematicMicroblock("mcr_hllw"));
        registerSchematic("mc_torch", new SchematicMcMetaPart("mc_torch"));
        registerSchematic("mc_lever", new SchematicMcMetaPart("mc_lever"));
        registerSchematic("mc_button", new SchematicMcMetaPart("mc_button"));
        registerSchematic("mc_redtorch", new SchematicMcMetaPart("mc_redtorch"));
    }
}
