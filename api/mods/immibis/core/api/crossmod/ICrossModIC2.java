package mods.immibis.core.api.crossmod;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.IBlockAccess;

public interface ICrossModIC2 {
	/**
	 * Returns the IC2 wrench item, or null.
	 */
	public Item getWrenchItem();
	
	/**
	 * Returns true if r is a shaped IC2 recipe.
	 */
	public boolean isShapedRecipe(IRecipe r);
	
	/**
	 * Returns true if r is a shapeless IC2 recipe.
	 */
	public boolean isShapelessRecipe(IRecipe r);
	
	/**
	 * Returns the inputs of a shaped IC2 recipe.
	 * 
	 * @param r A shaped IC2 recipe.
	 * @throws ClassCastException If R is not a shaped IC2 recipe.
	 * @return An array of 9 arrays of ItemStack, where each outer array corresponds to a crafting grid position
	 *         and each inner array holds the list of acceptable items in that position.
	 *         If the inner array is empty (length 0) no item is in that position.
	 */
	public ItemStack[][] getShapedRecipeInputs(IRecipe r) throws ClassCastException;
	
	/**
	 * Returns the inputs of a shapeless IC2 recipe.
	 * 
	 * @param r A shaped IC2 recipe.
	 * @throws ClassCastException If R is not a shapeless IC2 recipe.
	 * @return An array of arrays of ItemStack, where each outer array corresponds to a crafting grid position
	 *         and each inner array holds the list of acceptable items in that position.
	 *         Because the recipe is shapeless, the order of positions doesn't matter.
	 */
	public ItemStack[][] getShapelessRecipeInputs(IRecipe r) throws ClassCastException;
	
	/**
	 * Fires an EnergyTileLoadEvent.
	 * It is safe to call this on client tiles - it will do nothing.
	 * @throws ClassCastException if tile does not implement IEnergyTile.
	 */
	public void addEnetTile(Object tile) throws ClassCastException;
	
	/**
	 * Fires an EnergyTileUnloadEvent.
	 * It is safe to call this on client tiles - it will do nothing.
	 * @throws ClassCastException if tile does not implement IEnergyTile.
	 */
	public void removeEnetTile(Object tile) throws ClassCastException;

	/**
	 * Returns true if the item stores IC2 power.
	 */
	public boolean isElectricItem(ItemStack item);

	/**
	 * Returns amount discharged.
	 */
	public int dischargeElectricItem(ItemStack stack, int amount, int tier, boolean ignoreRateLimit, boolean simulate);

	/**
	 * Returns true if the given block is a reactor chamber.
	 */
	public boolean isReactorChamber(IBlockAccess world, int i, int j, int k);

	/**
	 * Adds a block to the explosion whitelist.
	 */
	public void addExplosionWhitelist(Block block);

	/**
	 * Returns an IC2 item, or null.
	 */
	public ItemStack getItem(String name);
}
