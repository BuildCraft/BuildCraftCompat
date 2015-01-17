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
package buildcraft.compat.tfc.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import buildcraft.BuildCraftEnergy;
import buildcraft.compat.tfc.TFCCompat;
import buildcraft.core.CreativeTabBuildCraft;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ISize;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import buildcraft.compat.tfc.PlayerUtils;

public class ItemBuckets extends ItemTerra implements ISize
{
	private IIcon[] icons;

	public ItemBuckets()
	{
		this(CreativeTabBuildCraft.ITEMS);
	}

	public ItemBuckets(CreativeTabBuildCraft creativeTab)
	{
		super();
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(creativeTab.get());
		setUnlocalizedName("Bucket");
		setContainerItem(TFCItems.WoodenBucketEmpty);
		MetaNames = new String[]{"Latex","Zinc","ZincSaltWater","ZincFreshWater","ZincHotWater","Steel","SteelOil","SteelFuel"};
	}

	@Override
	public boolean onUpdate(ItemStack is, World world, int x, int y, int z)
	{
		if(is.getItemDamage() != 4) return true;

		//It will take about 3min for the hot water to cool down and turn into fresh water
		if(TFC_ItemHeat.HasTemp(is))
			is.getTagCompound().setFloat("temperature", is.getTagCompound().getFloat("temperature") - 0.1F);

		if(TFC_ItemHeat.GetTemp(is) < 1)
		{
			TFC_ItemHeat.RemoveTempTag(is);
			is.setItemDamage(3);
		}

		return false;
	}

	@Override
	public ItemStack getContainerItem(ItemStack is)
	{
		if(is.getItemDamage() == 0)
			return new ItemStack(TFCItems.WoodenBucketEmpty);
		else if(is.getItemDamage() == 2 || is.getItemDamage() == 3 || is.getItemDamage() == 4)
			return new ItemStack(TFCCompat.Buckets, 1, 1);
		else if(is.getItemDamage() == 6 || is.getItemDamage() == 7)
			return new ItemStack(TFCCompat.Buckets, 1, 5);

		return null;
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.MEDIUM;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.MEDIUM;
	}

	@Override
	public boolean canStack()
	{
		return false;
	}

	@Override
	public IIcon getIconFromDamage(int meta)
	{
		return icons[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister registerer)
	{
		icons = new IIcon[MetaNames.length];
		for(int i = 0; i < MetaNames.length; i++)
			icons[i] = registerer.registerIcon("BuildCraft|Compat:tfc/" + this.getUnlocalizedName().replace("item.", "") + MetaNames[i]);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		for(int i = 0; i < MetaNames.length; i++)
			list.add(new ItemStack(this, 1, i));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void addSizeInformation(ItemStack is, ISize object, List arraylist)
	{
		if(object.getSize(is) != null && object.getWeight(is) != null)
			arraylist.add("\u2696" + object.getWeight(is).getName() + " \u21F2" + object.getSize(is).getName());
	}

	@SuppressWarnings({ "rawtypes", "unchecked"})
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag)
	{
		ItemBuckets.addSizeInformation(is, this, list);
		if(TFC_ItemHeat.HasTemp(is))
			list.add(TFC_ItemHeat.getHeatColor(TFC_ItemHeat.GetTemp(is), 600));
	}

	@Override
	public ItemStack onItemRightClick(ItemStack bucket, World world, EntityPlayer player)
	{
		MovingObjectPosition mop = PlayerUtils.getTargetBlock(player);
		if (mop != null)
		{
			int X = mop.blockX;
			int Y = mop.blockY;
			int Z = mop.blockZ;
			if (!world.canMineBlock(player, X, Y, Z)) { return bucket; }

			if (mop.typeOfHit == MovingObjectType.BLOCK)
			{
				if (bucket.getItemDamage() == 1 || bucket.getItemDamage() == 5)
				{
					if (!player.canPlayerEdit(X, Y, Z, mop.sideHit, bucket))
						return bucket;

					FillBucketEvent event = new FillBucketEvent(player, bucket, world, mop);
					if (event.isCanceled())
						return bucket;

					Block fluid = world.getBlock(X, Y, Z);
					//Pickup Salt Water
					if(isSaltWater(fluid) && bucket.getItemDamage() == 1)
					{
						if (!player.capabilities.isCreativeMode) world.setBlockToAir(X, Y, Z);
						return new ItemStack(TFCCompat.Buckets, 1, 2);
					}
					//Pickup Fresh Water
					else if(isFreshWater(fluid) && bucket.getItemDamage() == 1)
					{
						if (!player.capabilities.isCreativeMode) world.setBlockToAir(X, Y, Z);
						return new ItemStack(TFCCompat.Buckets, 1, 3);
					}
					//Pickup Hot Water
					else if(isHotWater(fluid) && bucket.getItemDamage() == 1)
					{
						ItemStack hotBucket = new ItemStack(TFCCompat.Buckets, 1, 4);
						NBTTagCompound nbttc = new NBTTagCompound();
						nbttc.setFloat("temperature", 480);
						hotBucket.setTagCompound(nbttc);
						if (!player.capabilities.isCreativeMode) world.setBlockToAir(X, Y, Z);
						return hotBucket;
					}
					//Pickup Oil
					else if(fluid == BuildCraftEnergy.blockOil && bucket.getItemDamage() == 5)
					{
						if (!player.capabilities.isCreativeMode) world.setBlockToAir(X, Y, Z);
						return new ItemStack(TFCCompat.Buckets, 1, 6);
					}
					//Pickup Fuel
					else if(fluid == BuildCraftEnergy.blockFuel && bucket.getItemDamage() == 5)
					{
						if (!player.capabilities.isCreativeMode) world.setBlockToAir(X, Y, Z);
						return new ItemStack(TFCCompat.Buckets, 1, 7);
					}
				}

				if (bucket.getItemDamage() == 2 || bucket.getItemDamage() == 3 || bucket.getItemDamage() == 4)
				{
					if (mop.sideHit == 0) {--Y;}
					else if (mop.sideHit == 1) {++Y;}
					else if (mop.sideHit == 2) {--Z;}
					else if (mop.sideHit == 3) {++Z;}
					else if (mop.sideHit == 4) {--X;}
					else if (mop.sideHit == 5) {++X;}

					if (!player.canPlayerEdit(X, Y, Z, mop.sideHit, bucket))
						return bucket;

					if (world.isAirBlock(X, Y, Z) || !world.getBlock(X, Y, Z).getMaterial().isSolid())
					{
						if (world.provider.isHellWorld && bucket.getItemDamage() == 2)
						{
							world.playSoundEffect(X + 0.5D, Y + 0.5D, Z + 0.5D, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
							for (int i = 0; i < 8; ++i)
								world.spawnParticle("largesmoke", X + Math.random(), Y + Math.random(), Z + Math.random(), 0.0D, 0.0D, 0.0D);
						}
						//Put Salt Water
						else if(bucket.getItemDamage() == 2)
						{
							world.setBlock(X, Y, Z, TFCBlocks.SaltWaterStationary);
							if (player.capabilities.isCreativeMode)
								return bucket;
							return new ItemStack(TFCCompat.Buckets, 1, 1);
						}
						//Put Fresh Water
						else if(bucket.getItemDamage() == 3)
						{
							world.setBlock(X, Y, Z, TFCBlocks.FreshWaterStationary);
							if (player.capabilities.isCreativeMode)
								return bucket;
							return new ItemStack(TFCCompat.Buckets, 1, 1);
						}
						//Put Hot Water
						else if(bucket.getItemDamage() == 4)
						{
							world.setBlock(X, Y, Z, TFCBlocks.HotWaterStationary);
							if (player.capabilities.isCreativeMode)
								return bucket;
							return new ItemStack(TFCCompat.Buckets, 1, 1);
						}
						//Put Oil
						else if (bucket.getItemDamage() == 6)
						{
							world.setBlock(X, Y, Z, BuildCraftEnergy.blockOil);
							if (player.capabilities.isCreativeMode)
								return bucket;
							return new ItemStack(TFCCompat.Buckets, 1, 5);
						}
					}
				}
			}
		}
		return bucket;
	}

	private boolean isSaltWater(Block water)
	{
		return water == TFCBlocks.SaltWater || water == TFCBlocks.SaltWaterStationary;
	}

	private boolean isFreshWater(Block water)
	{
		return water == TFCBlocks.FreshWater || water == TFCBlocks.FreshWaterStationary;
	}

	private boolean isHotWater(Block water)
	{
		return water == TFCBlocks.HotWater || water == TFCBlocks.HotWaterStationary;
	}
}
