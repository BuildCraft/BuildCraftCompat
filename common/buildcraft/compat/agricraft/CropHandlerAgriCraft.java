//package buildcraft.compat.agricraft;
//
//import java.util.List;
//
//import com.infinityraider.agricraft.api.API;
//import com.infinityraider.agricraft.api.v1.APIv1;
//import com.infinityraider.agricraft.api.v1.ICrop;
//import net.minecraft.block.Block;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.ItemStack;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.BlockPos;
//import net.minecraft.util.EnumFacing;
//import net.minecraft.world.IBlockAccess;
//import net.minecraft.world.World;
//import net.minecraft.world.WorldServer;
//
//import net.minecraftforge.common.IPlantable;
//
//import buildcraft.api.core.BuildCraftAPI;
//import buildcraft.api.crops.ICropHandler;
//
//public class CropHandlerAgriCraft implements ICropHandler {
//	private final APIv1 api;
//
//	public CropHandlerAgriCraft() {
//		api = (APIv1) API.getAPI(1);
//	}
//
//	@Override
//	public boolean isSeed(ItemStack stack) {
//		return stack.getItem() instanceof IPlantable;
//	}
//
//	@Override
//	public boolean canSustainPlant(World world, ItemStack seed, BlockPos pos) {
//		ICrop crop = api.getCrop(world, pos);
//		return crop != null && crop.canPlant();
//	}
//
//	@Override
//	public boolean plantCrop(World world, EntityPlayer player, ItemStack seed, BlockPos pos) {
//		player.setCurrentItemOrArmor(0, seed);
//		IBlockState state = world.getBlockState(pos);
//		boolean result = state.getBlock().onBlockActivated(world, pos, state, player,
//				EnumFacing.UP, 0.5f, 0.5f, 0.5f);
//		player.setCurrentItemOrArmor(0, null);
//		return result;
//
//	}
//
//	@Override
//	public boolean isMature(IBlockAccess blockAccess, IBlockState state, BlockPos pos) {
//		ICrop crop = null;
//
//		if (blockAccess instanceof World) {
//			crop = api.getCrop((World) blockAccess, pos);
//		} else {
//			// HACK: Time to do something that's not API-bound.
//			TileEntity tileEntity = blockAccess.getTileEntity(pos);
//			if (tileEntity instanceof ICrop) {
//				crop = (ICrop) tileEntity;
//			}
//		}
//
//		return crop != null && crop.isMature();
//	}
//
//	@Override
//	public boolean harvestCrop(World world, BlockPos pos, List<ItemStack> drops) {
//		ICrop crop = api.getCrop(world, pos);
//		if (crop != null) {
//			EntityPlayer player = BuildCraftAPI.proxy.getBuildCraftPlayer((WorldServer) world).get();
//			return crop.harvest(player);
//		} else {
//			return false;
//		}
//	}
//
//}
