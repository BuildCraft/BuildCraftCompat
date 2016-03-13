package buildcraft;

import java.io.File;
import java.util.HashSet;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import buildcraft.api.core.BCLog;
import buildcraft.compat.CompatModuleBase;
import buildcraft.compat.CompatModuleIronChest;
import buildcraft.compat.CompatModuleMineTweaker3;
import buildcraft.compat.CompatModuleWAILA;

@Mod(name = "BuildCraft Compat", version = "@VERSION@", useMetadata = false, modid = "BuildCraft|Compat", acceptedMinecraftVersions = "[1.8.9]",
		updateJSON = "http://mod-buildcraft.com/version/versions-compat.json",
        dependencies = "required-after:Forge@[11.15.1.1764,);required-after:BuildCraft|Core;after:BuildCraft|Transport;after:BuildCraft|Builders;after:IronChest")
public class BuildCraftCompat extends BuildCraftMod {
    @Mod.Instance("BuildCraft|Compat")
    public static BuildCraftCompat instance;

    private static Configuration config;
    private static final HashSet<CompatModuleBase> modules;
    private static final HashSet<String> moduleNames;

    public Configuration getConfig() {
        return config;
    }

    private void offerModule(final CompatModuleBase module) {
        Property prop = BuildCraftCompat.config.get("modules", module.name(), true);
        if (module.canLoad() && prop.getBoolean(true) == true) {
            BuildCraftCompat.modules.add(module);
            BuildCraftCompat.moduleNames.add(module.name());
        }
    }

    public static boolean isLoaded(String module) {
        return moduleNames.contains(module);
    }

    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent evt) {
        (BuildCraftCompat.config = new Configuration(new File(new File(evt.getSuggestedConfigurationFile().getParentFile(), "buildcraft"),
                "compat.cfg"))).load();

		this.offerModule(new CompatModuleIronChest());
		this.offerModule(new CompatModuleMineTweaker3());
		this.offerModule(new CompatModuleWAILA());

        BuildCraftCompat.config.save();

        for (final CompatModuleBase m : BuildCraftCompat.modules) {
            m.preInit();
        }
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

        for (final CompatModuleBase m : BuildCraftCompat.modules) {
            BCLog.logger.info("Loading compat module " + m.name());
            m.init();
        }

        BuildCraftCompat.config.save();
    }

    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent evt) {
        for (final CompatModuleBase m : BuildCraftCompat.modules) {
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

    public static boolean hasModule(final String module) {
        return BuildCraftCompat.moduleNames.contains(module);
    }

    static {
        modules = new HashSet<CompatModuleBase>();
        moduleNames = new HashSet<String>();
    }
}
