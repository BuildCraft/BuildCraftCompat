package buildcraft.compat.waila;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import buildcraft.api.robots.EntityRobotBase;
import buildcraft.robotics.EntityRobot;
import buildcraft.robotics.ai.AIRobotShutdown;
import buildcraft.robotics.ai.AIRobotSleep;

public class EntityRobotDataProvider implements IWailaEntityProvider {
	@Override
	public Entity getWailaOverride(IWailaEntityAccessor accessor, IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaHead(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
		currenttip.clear();
		EntityRobot robot = (EntityRobot) entity;

		int energy = robot.getEnergy();
		int pct = energy * 100 / EntityRobotBase.MAX_ENERGY;
		String enInfo = pct + "% Charged";
		if (energy == 0) {
			enInfo = "No Charge";
		}

		enInfo = (pct >= 80 ? EnumChatFormatting.GREEN :
				(pct >= 50 ? EnumChatFormatting.YELLOW :
				(pct >= 30 ? EnumChatFormatting.GOLD :
				(pct >= 20 ? EnumChatFormatting.RED : EnumChatFormatting.DARK_RED)))) + enInfo;
		currenttip.add(enInfo);

		try {
			if (accessor != null && accessor.getNBTData() != null) {
				if (accessor.getNBTData().getBoolean("r_sleep")) {
					currenttip.add(EnumChatFormatting.ITALIC + "Inactive");
				} else if (accessor.getNBTData().getBoolean("r_shutdown")) {
					currenttip.add(EnumChatFormatting.ITALIC + "Dead");
				} else if (accessor.getNBTData().getBoolean("r_invalid")) {
					currenttip.add(EnumChatFormatting.ITALIC + "Invalid");
				}
			}
		} catch (Exception e) {

		}

		return currenttip;
	}

	@Override
	public List<String> getWailaTail(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, Entity ent, NBTTagCompound tag, World world) {
		if (ent != null && ent instanceof EntityRobot) {
			if (((EntityRobot) ent).mainAI != null) {
				tag.setBoolean("r_sleep", ((EntityRobot) ent).mainAI.getActiveAI() instanceof AIRobotSleep);
				tag.setBoolean("r_shutdown", ((EntityRobot) ent).mainAI.getActiveAI() instanceof AIRobotShutdown);
			} else {
				tag.setBoolean("r_invalid", true);
			}
		}
		return tag;
	}
}
