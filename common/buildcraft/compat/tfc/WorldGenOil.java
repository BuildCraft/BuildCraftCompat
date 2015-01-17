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

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import buildcraft.BuildCraftEnergy;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.WorldGen.DataLayer;
import com.bioxx.tfc.WorldGen.TFCWorldChunkManager;

import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenOil implements IWorldGenerator
{
	public WorldGenOil() {}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		chunkX *= 16;
		chunkZ *= 16;

		if(world.getWorldChunkManager() instanceof TFCWorldChunkManager)
		{
			Block oilRockLayers = TFCBlocks.StoneSed; /*spawn only in sedimentary stone*/

			DataLayer rockLayer1 = TFC_Climate.getCacheManager(world).getRockLayerAt(chunkX, chunkZ, 0);
			DataLayer rockLayer2 = TFC_Climate.getCacheManager(world).getRockLayerAt(chunkX, chunkZ, 1);
			DataLayer rockLayer3 = TFC_Climate.getCacheManager(world).getRockLayerAt(chunkX, chunkZ, 2);

			if(rockLayer1.block == oilRockLayers)
			{
				if(random.nextInt(100) == 0)
				{
					genShallowOil(world, random, chunkX, chunkZ, rockLayer2);
				}
				else if(random.nextInt(300) == 0)
				{
					genDeepOil(world, random, chunkX, chunkZ, rockLayer3);
				}
				else if (random.nextInt(600) == 0)
				{
					genBottomOil(world, random, chunkX, chunkZ);
				}
			}
		}
	}

	private static void genShallowOil(World world, Random rand, int chunkX, int chunkZ, DataLayer rockLayer2)
	{
		int radius = rand.nextInt(7) + 3; // Size of the Oil pool
		int height = rand.nextInt(6); // Height of the Oil pool
		int chunkY = 250; // Initialize chunkY

		for(int l = 250; l > 50; l--) // Find the y where layer1 and layer2 meet
		{
			if(world.getBlock(chunkX, l, chunkZ) == rockLayer2.block && world.getBlockMetadata(chunkX, l, chunkZ) == rockLayer2.data2)
			{
				chunkY = l;
				break;
			}
		}

		if(chunkY < 130)
		{
			genOil(world, chunkX, chunkZ, chunkY, radius, height);

			/**
			 * Generate Oil tunnel to the surface
			 */
			if(rand.nextInt(100) < 50)
				genTunnel(world, rand, chunkX, chunkZ, chunkY, radius);
		}
	}

	private static void genDeepOil(World world, Random rand, int chunkX, int chunkZ, DataLayer rockLayer3)
	{
		int radius = rand.nextInt(10) + 10; // Size of the Oil pool
		int height = rand.nextInt(6) + 2; // Height of the Oil pool
		int chunkY = 250; // Initialize chunkY

		for(int l = 250; l > 50; l++) // Find the y where layer2 and layer3 meet
		{
			if(world.getBlock(chunkX, l, chunkZ) == rockLayer3.block && world.getBlockMetadata(chunkX, l, chunkZ) == rockLayer3.data2)
			{
				chunkY = l;
				break;
			}
		}

		if(chunkY < 90)
		{
			genOil(world, chunkX, chunkZ, chunkY, radius, height);
		}
	}

	private static void genBottomOil(World world, Random rand, int chunkX, int chunkZ)
	{
		int radius = rand.nextInt(10) + 15; // Size of the Oil pool
		int height = rand.nextInt(10) + 5; // Height of the Oil pool
		int chunkY = 2 + height; // Initialize chunkY
		genOil(world, chunkX, chunkZ, chunkY, radius, height);
	}

	private static void genOil(World world, int chunkX, int chunkZ, int chunkY, int radius, int height)
	{
		for(int xCoord = chunkX - radius; xCoord <= chunkX + radius; xCoord++)
		{
			for(int zCoord = chunkZ - radius; zCoord <= chunkZ + radius; zCoord++)
			{
				int x = xCoord - chunkX;
				int z = zCoord - chunkZ;
				if (x * x + z * z <= radius * radius)
				{
					for(int yCoord = chunkY - height; yCoord <= chunkY; yCoord++)
					{
						if(world.getBlock(xCoord, yCoord, zCoord) != Blocks.bedrock)
						{
							world.setBlock(xCoord, yCoord, zCoord, BuildCraftEnergy.blockOil, 0, 0x2);
							//System.out.println("OIL: "+xCoord+":"+yCoord+":"+zCoord+"::"+height);
						}
					}
				}
			}
		}
	}

	private static void genTunnel(World world, Random rand, int chunkX, int chunkZ, int chunkY, int radius)
	{
		int dir = rand.nextInt(3);
		boolean makeTunnel = true;
		float upChance = 35;
		int xCoord = chunkX;
		int yCoord = chunkY;
		int zCoord = chunkZ;

		switch(dir)
		{
			case 0: xCoord = chunkX + radius; break;
			case 1: xCoord = chunkX - radius; break;
			case 2: zCoord = chunkZ + radius; break;
			case 3: zCoord = chunkZ - radius; break;
		}

		while(makeTunnel)
		{
			if(world.isAirBlock(xCoord, yCoord + 1, zCoord)) makeTunnel = false;

			if(rand.nextFloat() < upChance / 100F)
			{
				yCoord++;
			}
			else
			{
				switch(dir)
				{
					case 0: xCoord++; break;
					case 1: xCoord--; break;
					case 2: zCoord++; break;
					case 3: zCoord--; break;
				}
			}

			world.setBlock(xCoord, yCoord, zCoord, BuildCraftEnergy.blockOil, 0, 0x2);
		}
	}

}
