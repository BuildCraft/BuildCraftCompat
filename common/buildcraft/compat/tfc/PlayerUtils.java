/**
 *  Copyright (C) 2013  emris
 *  https://github.com/emris/BCTFCcrossover
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package buildcraft.compat.tfc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class PlayerUtils
{
	public static MovingObjectPosition getTargetBlock(EntityPlayer P)
	{
		float v1 = 1.0F;
		double v2 = P.prevPosX + (P.posX - P.prevPosX) * v1;
		double v3 = P.prevPosY + (P.posY - P.prevPosY) * v1 + 1.62D - P.yOffset;
		double v4 = P.prevPosZ + (P.posZ - P.prevPosZ) * v1;
		Vec3 v5 = Vec3.createVectorHelper(v2, v3, v4);

		float v6 = P.prevRotationYaw + (P.rotationYaw - P.prevRotationYaw) * v1;
		float v7 = P.prevRotationPitch + (P.rotationPitch - P.prevRotationPitch) * v1;

		float v8 = MathHelper.cos(-v6 * 0.017453292F - (float)Math.PI);
		float v9 = MathHelper.sin(-v6 * 0.017453292F - (float)Math.PI);
		float v10 = -MathHelper.cos(-v7 * 0.017453292F);
		float v11 = MathHelper.sin(-v7 * 0.017453292F);
		float v12 = v8 * v10;
		float v13 = v9 * v10;
		double v14 = 5.0D; 
		Vec3 v15 = v5.addVector(v13 * v14, v11 * v14, v12 * v14);

		//P.sendChatToPlayer("<x" + v2 + ", y" + v3 + ", z" + v4 + ">");

		return P.worldObj.rayTraceBlocks(v5, v15, true);
	}
}
