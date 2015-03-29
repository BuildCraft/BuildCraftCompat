package buildcraft.compat.robots;

import mods.defeatedcrow.api.plants.IRightClickHarvestable;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.Optional;
import buildcraft.BuildCraftCompat;
import buildcraft.api.core.BlockIndex;
import buildcraft.api.robots.AIRobot;
import buildcraft.api.robots.EntityRobotBase;
import buildcraft.compat.amt.AIRobotHarvestAMT;
import buildcraft.robotics.ai.AIRobotBreak;
import buildcraft.robotics.boards.BoardRobotHarvester;

public class BoardRobotHarvesterCompat extends BoardRobotHarvester {
	private static final boolean APPLE_MILK_TEA = BuildCraftCompat.hasModule("AppleMilkTea2");

	public BoardRobotHarvesterCompat(EntityRobotBase iRobot) {
		super(iRobot);
	}

	public BoardRobotHarvesterCompat(EntityRobotBase iRobot, NBTTagCompound nbtTagCompound) {
		super(iRobot, nbtTagCompound);
	}

	@Override
	protected AIRobot getBlockBreakAI() {
		if (APPLE_MILK_TEA) {
			if (isAMTBlock(this.indexStored)) {
				return new AIRobotHarvestAMT(this.robot, this.indexStored);
			} else {
				System.out.println(" NOT AMT BLOCK ");
			}
		}
		return new AIRobotBreak(this.robot, this.indexStored);
	}

	@Optional.Method(modid = "DCsAppleMilk")
	private boolean isAMTBlock(BlockIndex indexStored) {
		if (robot == null) {
			System.out.println("ROBOT IS NULL");
			return false;
		} else if (robot.worldObj == null) {
			System.out.println("ROBOT IS NULLISTIC");
			return false;
		}
		Block block = robot.worldObj.getBlock(indexStored.x, indexStored.y, indexStored.z);
		return block != null && block instanceof IRightClickHarvestable;
	}
}
