package buildcraft.compat.amt;

import buildcraft.api.core.*;
import net.minecraft.block.*;
import buildcraft.api.robots.*;
import net.minecraft.world.*;
import mods.defeatedcrow.api.plants.*;
import net.minecraft.inventory.*;
import net.minecraft.nbt.*;

public class AIRobotHarvestAMT extends AIRobot
{
    public BlockIndex blockToBreak;
    private int ticks;
    private Block block;
    
    public AIRobotHarvestAMT(final EntityRobotBase iRobot) {
        super(iRobot);
    }
    
    public AIRobotHarvestAMT(final EntityRobotBase iRobot, final BlockIndex iBlockToBreak) {
        super(iRobot);
        this.blockToBreak = iBlockToBreak;
    }
    
    public void start() {
        this.block = this.robot.worldObj.getBlock(this.blockToBreak.x, this.blockToBreak.y, this.blockToBreak.z);
    }
    
    public void update() {
        if (this.block == null || this.block.isAir((IBlockAccess)this.robot.worldObj, this.blockToBreak.x, this.blockToBreak.y, this.blockToBreak.z)) {
            this.terminate();
        }
        if (!((IRightClickHarvestable)this.block).isHarvestable(this.robot.worldObj, this.blockToBreak.x, this.blockToBreak.y, this.blockToBreak.z)) {
            this.terminate();
        }
        ++this.ticks;
        if (this.ticks < 10) {
            return;
        }
        ((IRightClickHarvestable)this.block).onHarvest(this.robot.worldObj, this.blockToBreak.x, this.blockToBreak.y, this.blockToBreak.z, null, this.robot.getHeldItem());
        this.terminate();
    }
    
    public int getEnergyCost() {
        return 20;
    }
    
    public void writeSelfToNBT(final NBTTagCompound nbt) {
        super.writeSelfToNBT(nbt);
        if (this.blockToBreak != null) {
            final NBTTagCompound sub = new NBTTagCompound();
            this.blockToBreak.writeTo(sub);
            nbt.setTag("blockToBreak", (NBTBase)sub);
        }
    }
    
    public void loadSelfFromNBT(final NBTTagCompound nbt) {
        super.loadSelfFromNBT(nbt);
        if (nbt.hasKey("blockToBreak")) {
            this.blockToBreak = new BlockIndex(nbt.getCompoundTag("blockToBreak"));
        }
    }
}
