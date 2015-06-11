package buildcraft.compat;

import java.util.Map;

import com.google.common.collect.MapMaker;

import net.minecraft.tileentity.TileEntity;

import buildcraft.BuildCraftCompat;
import buildcraft.compat.factorization.ChargeEnergyReceiver;
import factorization.api.IChargeConductor;

public class CompatModuleFactorization extends CompatModuleBase {
    public static float CHARGE_PER_RF = 0.13F;
    public static boolean ENABLE_ENERGY = false;
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
        ENABLE_ENERGY = BuildCraftCompat.instance.getConfig().getBoolean("tweaks", "energyBridgeCharge", false, "Allow BC Kinesis Pipes to emit Charge.");
    }

    @Override
    public void init() {

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
