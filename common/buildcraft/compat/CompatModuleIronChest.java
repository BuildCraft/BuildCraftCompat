//package buildcraft.compat;
//
//import net.minecraftforge.fml.common.Loader;
//
//import buildcraft.compat.ironchests.SchematicIronChest;
//
//public class CompatModuleIronChest extends CompatModuleBase
//{
//    @Override
//    public String name() {
//        return "IronChest";
//    }
//
//    @Override
//    public boolean canLoad() {
//        return Loader.isModLoaded("IronChest") && Loader.isModLoaded("BuildCraft|Builders");
//    }
//
//    @Override
//    public void init() {
//        CompatUtils.registerSchematic("ironchest:BlockIronChest", SchematicIronChest.class, new Object[0]);
//    }
//}
