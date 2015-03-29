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
}
