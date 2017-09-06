package buildcraft.compat;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import buildcraft.api.core.BCLog;

//@formatter:off
@Mod(
        modid = BCCompat.MODID,
        name = "BuildCraft Compat",
        version = BCCompat.VERSION,
        updateJSON = "https://mod-buildcraft.com/version/versions-compat.json",
        acceptedMinecraftVersions = "(gradle_replace_mcversion,)",
        dependencies = "required-after:forge@(gradle_replace_forgeversion,);required-after:buildcraftcore;after:buildcrafttransport;after:buildcraftbuilders"
)
//@formatter:on
public class BCCompat {

    public static final String MODID = "buildcraftcompat";
    public static final String VERSION = "${version}";

    @Mod.Instance(MODID)
    public static BCCompat instance;

    private static Configuration config;
    private static final Map<String, CompatModuleBase> modules = new HashMap<>();

    public Configuration getConfig() {
        return config;
    }

    private void offerModule(final CompatModuleBase module) {
        if (module.canLoad()) {
            Property prop = config.get("modules", module.compatModId(), true);
            if (prop.getBoolean(true)) {
                modules.put(module.compatModId(), module);
            }
        }
    }

    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent evt) {
        File cfgFolder = evt.getModConfigurationDirectory();
        cfgFolder = new File(cfgFolder, "buildcraft");
        config = new Configuration(new File(cfgFolder, "config.cfg"));
        config.load();

        // Module offering/addition goes here


        for (CompatModuleBase m : modules.values()) {
            m.preInit();
        }
        config.save();
    }

    @Mod.EventHandler
    public void init(final FMLInitializationEvent evt) {
        // NetworkRegistry.INSTANCE.registerGuiHandler(instance, new CompatGuiHandler());
        //
        // compatChannelHandler = new ChannelHandler();
        // MinecraftForge.EVENT_BUS.register(this);
        //
        // compatChannelHandler.registerPacketType(PacketGenomeFilterChange.class);
        // compatChannelHandler.registerPacketType(PacketTypeFilterChange.class);
        // compatChannelHandler.registerPacketType(PacketRequestFilterSet.class);
        //
        // channels = NetworkRegistry.INSTANCE.newChannel
        // (DefaultProps.NET_CHANNEL_NAME + "-COMPAT", compatChannelHandler, new PacketHandlerCompat());

        for (final CompatModuleBase m : modules.values()) {
            BCLog.logger.info("[compat] Loading module " + m.compatModId());
            m.init();
        }

        config.save();
    }

    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent evt) {
        for (final CompatModuleBase m : modules.values()) {
            m.postInit();
        }
    }

    // @Mod.EventHandler
    // public void missingMapping(FMLMissingMappingsEvent event) {
    // CompatModuleForestry.missingMapping(event);
    // }

    // @SubscribeEvent
    // @SideOnly(Side.CLIENT)
    // public void handleTextureRemap(TextureStitchEvent.Pre event) {
    // if (event.map.getTextureType() == 1) {
    // TextureManager.getInstance().initIcons(event.map);
    // }
    // }

//    public static boolean isLoaded(String module) {
//        return moduleNames.contains(module);
//    }

//    public static boolean hasModule(final String module) {
//        return BuildCraftCompat.moduleNames.contains(module);
//    }
}
