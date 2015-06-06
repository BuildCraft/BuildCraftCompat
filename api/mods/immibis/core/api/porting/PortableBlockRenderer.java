package mods.immibis.core.api.porting;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public interface PortableBlockRenderer {
	public boolean renderWorldBlock(RenderBlocks render, IBlockAccess world, int x, int y, int z, Block block, int model);
	public void renderInvBlock(RenderBlocks render, Block block, int meta, int model);
}