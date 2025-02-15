package buildcraft.compat.module.forge;

import buildcraft.api.core.EnumPipePart;
import buildcraft.api.enums.EnumPowerStage;
import buildcraft.api.mj.IMjConnector;
import buildcraft.api.mj.MjAPI;
import buildcraft.compat.BCCompatBlocks;
import buildcraft.lib.engine.EngineConnector;
import buildcraft.lib.engine.TileEngineBase_BC8;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;

public class TileEngineFe extends TileEngineBase_BC8 {
    private static final int MJ_CAP = 10_000;
    private static final int FE_CAP = convert_mMJ_2_FE(MJ_CAP * MjAPI.MJ);

    private final IEnergyStorage feStorage = new IEnergyStorage() {
        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            int received_FE = Math.min(convert_mMJ_2_FE(TileEngineFe.this.getMaxPower() - TileEngineFe.this.getEnergyStored()), maxReceive);
            if (!simulate) {
                addPower(convert_FE_2_mMJ(received_FE));
            }
            return received_FE;
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            return 0;
        }

        @Override
        public int getEnergyStored() {
            return convert_mMJ_2_FE(TileEngineFe.this.getEnergyStored());
        }

        @Override
        public int getMaxEnergyStored() {
            return FE_CAP;
        }

        @Override
        public boolean canExtract() {
            return false;
        }

        @Override
        public boolean canReceive() {
            return true;
        }
    };

    public TileEngineFe(BlockPos pos, BlockState blockState) {
        super(BCCompatBlocks.engineFeTile.get(), pos, blockState);
        caps.addCapabilityInstance(ForgeCapabilities.ENERGY, this.feStorage, EnumPipePart.VALUES);
    }

    @NotNull
    @Override
    protected IMjConnector createConnector() {
        return new EngineConnector(false);
    }

    @Override
    public boolean isBurning() {
        return isRedstonePowered;
    }

    @Override
    public long getMaxPower() {
        return MJ_CAP * MjAPI.MJ;
    }

    @Override
    public long maxPowerReceived() {
        return 2_000 * MjAPI.MJ;
    }

    @Override
    public long maxPowerExtracted() {
        return 1_000 * MjAPI.MJ;
    }

    @Override
    public float explosionRange() {
        return 0;
    }

    @Override
    public long getCurrentOutput() {
        return 256 * MjAPI.MJ;
    }

    @Override
    protected EnumPowerStage computePowerStage() {
        return EnumPowerStage.RED;
    }

    @Override
    protected void burn() {
    }

    public static int convert_mMJ_2_FE(long mMJ) {
        return (int) Math.min(Integer.MAX_VALUE, mMJ * 16L / MjAPI.MJ);
    }

    public static long convert_FE_2_mMJ(int fe) {
        return fe * MjAPI.MJ / 16L;
    }
}
