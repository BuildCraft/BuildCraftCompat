package buildcraft.compat.amt;

import mods.defeatedcrow.api.plants.IRightClickHarvestable;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import buildcraft.api.core.BlockIndex;
import buildcraft.api.robots.AIRobot;
import buildcraft.api.robots.EntityRobotBase;

public class AIRobotHarvestAMT extends AIRobot {
	public BlockIndex blockToBreak;
	private int ticks;
	private Block block;

	public AIRobotHarvestAMT(EntityRobotBase iRobot) {
		super(iRobot);
	}

	public AIRobotHarvestAMT(EntityRobotBase iRobot, BlockIndex iBlockToBreak) {
		super(iRobot);
		this.blockToBreak = iBlockToBreak;
	}

	public void start() {
		this.block = this.robot.worldObj.getBlock(this.blockToBreak.x, this.blockToBreak.y, this.blockToBreak.z);
	}

	public void update() {
		if(this.block == null || this.block.isAir(this.robot.worldObj, this.blockToBreak.x, this.blockToBreak.y, this.blockToBreak.z)) {
			this.terminate();
		}

		if (!(((IRightClickHarvestable) block).isHarvestable(this.robot.worldObj, this.blockToBreak.x, this.blockToBreak.y, this.blockToBreak.z))) {
			this.terminate();
		}

		ticks++;
		if (ticks < 10) {
			return;
		}

		((IRightClickHarvestable) block).onHarvest(this.robot.worldObj, this.blockToBreak.x, this.blockToBreak.y, this.blockToBreak.z, null, robot.getHeldItem());
		this.terminate();
	}

	public int getEnergyCost() {
		return 20;
	}

	public void writeSelfToNBT(NBTTagCompound nbt) {
		super.writeSelfToNBT(nbt);
		if(this.blockToBreak != null) {
			NBTTagCompound sub = new NBTTagCompound();
			this.blockToBreak.writeTo(sub);
			nbt.setTag("blockToBreak", sub);
		}

	}

	public void loadSelfFromNBT(NBTTagCompound nbt) {
		super.loadSelfFromNBT(nbt);
		if(nbt.hasKey("blockToBreak")) {
			this.blockToBreak = new BlockIndex(nbt.getCompoundTag("blockToBreak"));
		}
	}
}
