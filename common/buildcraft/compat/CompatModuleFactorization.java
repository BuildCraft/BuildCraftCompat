package buildcraft.compat;

import java.util.Map;

import com.google.common.collect.MapMaker;

import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;

import buildcraft.BuildCraftCompat;
import buildcraft.api.blueprints.SchematicTile;
import buildcraft.compat.factorization.ChargeEnergyReceiver;
import buildcraft.compat.factorization.SchematicFZBarrel;
import buildcraft.compat.factorization.SchematicFZBase;
import factorization.api.IChargeConductor;

public class CompatModuleFactorization extends CompatModuleBase {
    public static float CHARGE_PER_RF = 0.13F;
    public static boolean ENABLE_ENERGY = false;
    public static boolean ENABLE_SERVO_RAILS = false;
    private static final Map<TileEntity, Object> energyProviders = new MapMaker().weakValues().makeMap();

    @Override
    public String name() {
        return "factorization";
    }
    
    @Override
    public boolean canLoad() {
        return super.canLoad();
    }

    @Override
    public void preInit() {
        //ENABLE_ENERGY = BuildCraftCompat.instance.getConfig().getBoolean("tweaks", "energyBridgeCharge", false, "Allow BC Kinesis Pipes to emit Charge.");
        ENABLE_SERVO_RAILS = BuildCraftCompat.instance.getConfig().getBoolean("builderServoRails", "balance", true, "Should Builders place (pre-configured) Servo Rails?");
    }

    @Override
    public void init() {
        if (Loader.isModLoaded("BuildCraft|Builders")) {
            initBuilders();
        }
    }

    @Optional.Method(modid = "BuildCraft|Builders")
    private void initBuilders() {
        CompatUtils.registerSchematic("factorization:FzBlock", 1, SchematicFZBase.class); // Comp.Crafter
        CompatUtils.registerSchematic("factorization:FzBlock", 2, SchematicFZBarrel.class); // Barrels
        CompatUtils.registerSchematic("factorization:FzBlock", 3, SchematicFZBase.class); // Sockets
        CompatUtils.registerSchematic("factorization:FzBlock", 4, SchematicTile.class); // Magical Lamp
        CompatUtils.registerSchematic("factorization:FzBlock", 6, SchematicFZBase.class); // Battery
        CompatUtils.registerSchematic("factorization:FzBlock", 7, SchematicFZBase.class); // Machine
        CompatUtils.registerSchematic("factorization:FzBlock", 8, SchematicFZBase.class); // Slag Furnace
        CompatUtils.registerSchematic("factorization:FzBlock", 9, SchematicFZBase.class); // Cable
    }

    public static Object getEnergyProvider(TileEntity tile) {
        if (ENABLE_ENERGY && tile instanceof IChargeConductor) {
            if (!energyProviders.containsKey(tile)) {
                energyProviders.put(tile, new ChargeEnergyReceiver(tile));
            }
            return energyProviders.get(tile);
        }
        return null;
    }
}
