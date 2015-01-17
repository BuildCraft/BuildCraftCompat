package buildcraft.compat.tfc;

import net.minecraft.item.ItemStack;

import com.bioxx.tfc.api.Crafting.AnvilRecipe;
import com.bioxx.tfc.api.Crafting.AnvilReq;

public class MultiItemAnvilRecipe extends AnvilRecipe
{
	ItemStack result;

	public MultiItemAnvilRecipe(ItemStack in, ItemStack in2, String p, AnvilReq req, ItemStack result)
	{
		super(in, in2, p, req, result);
		this.result = result;
	}

	@Override
	public ItemStack getCraftingResult()
	{
		return result.copy();
	}
}
