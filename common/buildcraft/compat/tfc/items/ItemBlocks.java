package buildcraft.compat.tfc.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import buildcraft.core.CreativeTabBuildCraft;

public class ItemBlocks extends ItemBlock
{
	public ItemBlocks(Block b)
	{
		this(b, CreativeTabBuildCraft.BLOCKS);
	}

	public ItemBlocks(Block b, CreativeTabBuildCraft creativeTab)
	{
		super(b);
		setCreativeTab(creativeTab.get());
	}

}
