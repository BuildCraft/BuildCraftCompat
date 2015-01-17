package buildcraft.compat.tfc.pipes.transport;

import net.minecraft.item.Item;
import net.minecraftforge.common.util.ForgeDirection;
import buildcraft.api.core.IIconProvider;
import buildcraft.transport.pipes.PipeItemsQuartz;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import buildcraft.compat.tfc.TFCCompat;
import buildcraft.compat.tfc.PipeIconProvider;

public class SterlingSilverPipeItem extends PipeItemsQuartz
{
	public SterlingSilverPipeItem(Item item)
	{
		super(item);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIconProvider getIconProvider()
	{
		return TFCCompat.instance.pipeIconProvider;
	}

	@Override
	public int getIconIndex(ForgeDirection direction)
	{
		return PipeIconProvider.TYPE.PipeSterlingSilver.ordinal();
	}
}
