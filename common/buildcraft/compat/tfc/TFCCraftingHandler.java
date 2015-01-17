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

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.TFCItems;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class TFCCraftingHandler
{
	@SubscribeEvent
	public void onCrafting(ItemCraftedEvent e)
	{
		Item item = e.crafting.getItem();
		
		if(e.craftMatrix != null)
		{
			if(item == TFCCompat.Rubber)
			{
				for(int i = 0; i < e.craftMatrix.getSizeInventory(); i++)
				{
					if(e.craftMatrix.getStackInSlot(i) == null)
						continue;

					if(e.craftMatrix.getStackInSlot(i).getItem() == TFCCompat.Buckets && e.craftMatrix.getStackInSlot(i).getItemDamage() == 0)
					{
						ItemStack b = new ItemStack(TFCItems.WoodenBucketEmpty);
						if(b != null)
						{
							e.craftMatrix.setInventorySlotContents(i, b);
							e.craftMatrix.getStackInSlot(i).stackSize = 2;
						}
					}

					if(e.craftMatrix.getStackInSlot(i).getItem() == TFCCompat.LatexBowl)
					{
						ItemStack b = new ItemStack(Items.bowl);
						if(b != null)
						{
							e.craftMatrix.setInventorySlotContents(i, b);
							e.craftMatrix.getStackInSlot(i).stackSize = 2;
						}
					}
				}
			}
		}
	}

}
