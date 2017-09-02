//package buildcraft.compat;
//
//import buildcraft.api.core.BCLog;
//import buildcraft.api.crops.CropManager;
//import buildcraft.compat.agricraft.CropHandlerAgriCraft;
//import com.infinityraider.agricraft.api.API;
//import com.infinityraider.agricraft.api.v1.APIv1;
//import net.minecraftforge.fml.common.Loader;
//
//public class CompatModuleAgriCraft extends CompatModuleBase {
//
//	@Override
//	public String name() {
//		return "AgriCraft";
//	}
//
//	@Override
//	public boolean canLoad() {
//		return Loader.isModLoaded("agricraft");
//	}
//
//	@Override
//	public void init() {
//		if (API.getAPI(1).getVersion() != 1) {
//			BCLog.logger.error(String.format("Invalid AgriCraft API version %d! BuildCraft compatibility disabled.", API.getAPI(1).getVersion()));
//		} else {
//			CropManager.registerHandler(new CropHandlerAgriCraft());
//		}
//	}
//}
