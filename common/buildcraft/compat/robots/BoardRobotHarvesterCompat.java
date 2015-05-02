package buildcraft.compat.robots;

import buildcraft.BuildCraftCompat;
import cpw.mods.fml.common.Optional;
import net.minecraft.block.Block;
import mods.defeatedcrow.api.plants.IRightClickHarvestable;
import buildcraft.api.core.BlockIndex;
import buildcraft.robotics.ai.AIRobotBreak;
import buildcraft.compat.amt.AIRobotHarvestAMT;
import buildcraft.api.robots.AIRobot;
import net.minecraft.nbt.NBTTagCompound;
import buildcraft.api.robots.EntityRobotBase;
import buildcraft.robotics.boards.BoardRobotHarvester;

public class BoardRobotHarvesterCompat extends BoardRobotHarvester
{
    private static final boolean APPLE_MILK_TEA;
    
    public BoardRobotHarvesterCompat(final EntityRobotBase iRobot) {
        super(iRobot);
    }
    
    public BoardRobotHarvesterCompat(final EntityRobotBase iRobot, final NBTTagCompound nbtTagCompound) {
        super(iRobot, nbtTagCompound);
    }
    
    protected AIRobot getBlockBreakAI() {
        if (BoardRobotHarvesterCompat.APPLE_MILK_TEA) {
            if (this.isAMTBlock(this.blockFound)) {
                return new AIRobotHarvestAMT(this.robot, this.blockFound);
            }
            System.out.println(" NOT AMT BLOCK ");
        }
        return (AIRobot)new AIRobotBreak(this.robot, this.blockFound);
    }
    
    @Optional.Method(modid = "DCsAppleMilk")
    private boolean isAMTBlock(final BlockIndex indexStored) {
        if (this.robot == null) {
            System.out.println("ROBOT IS NULL");
            return false;
        }
        if (this.robot.worldObj == null) {
            System.out.println("ROBOT IS NULLISTIC");
            return false;
        }
        final Block block = this.robot.worldObj.getBlock(indexStored.x, indexStored.y, indexStored.z);
        return block != null && block instanceof IRightClickHarvestable;
    }
    
    static {
        APPLE_MILK_TEA = BuildCraftCompat.hasModule("AppleMilkTea2");
    }
}
