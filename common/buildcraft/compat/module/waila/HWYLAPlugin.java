package buildcraft.compat.module.waila;

import buildcraft.lib.block.BlockBCTile_Neptune;
import buildcraft.lib.tile.TileBC_Neptune;
import snownee.jade.api.*;

@WailaPlugin
public class HWYLAPlugin implements IWailaPlugin {
    // Calen: in 1.18.2 we should use Block or TE class instead of interface here, it's too difficult to get all matched if allowing some BC modules absent,
    // so just use TileBC_Neptune and BlockBCTile_Neptune
    @Override
    public void register(IWailaCommonRegistration registrar) {
        IServerDataProvider<BlockAccessor> autoCraftNbtProvider = new AutoCraftDataProvider.NBTProvider();
        IServerDataProvider<BlockAccessor> laserTargetNbtProvider = new LaserTargetDataProvider.NBTProvider();
        IServerDataProvider<BlockAccessor> assemblyCraftNbtProvider = new AssemblyCraftDataProvider.NBTProvider();
        IServerDataProvider<BlockAccessor> mjStorageNbtProvider = new MjStorageDataProvider.NBTProvider();

        registrar.registerBlockDataProvider(autoCraftNbtProvider, TileBC_Neptune.class);
        registrar.registerBlockDataProvider(laserTargetNbtProvider, TileBC_Neptune.class);
        registrar.registerBlockDataProvider(assemblyCraftNbtProvider, TileBC_Neptune.class);
        registrar.registerBlockDataProvider(mjStorageNbtProvider, TileBC_Neptune.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registrar) {
//        IComponentProvider autoCraftBodyProvider = new AutoCraftDataProvider.BodyProvider();
//        IComponentProvider laserTargetBodyProvider = new LaserTargetDataProvider.BodyProvider();
//        IComponentProvider assemblyCraftBodyProvider = new AssemblyCraftDataProvider.BodyProvider();
        IBlockComponentProvider autoCraftBodyProvider = new AutoCraftDataProvider.BodyProvider();
        IBlockComponentProvider laserTargetBodyProvider = new LaserTargetDataProvider.BodyProvider();
        IBlockComponentProvider assemblyCraftBodyProvider = new AssemblyCraftDataProvider.BodyProvider();
        IBlockComponentProvider mjStorageBodyProvider = new MjStorageDataProvider.BodyProvider();

//        registrar.registerComponentProvider(autoCraftBodyProvider, TooltipPosition.BODY, BlockBCTile_Neptune.class);
//        registrar.registerComponentProvider(laserTargetBodyProvider, TooltipPosition.BODY, BlockBCTile_Neptune.class);
//        registrar.registerComponentProvider(assemblyCraftBodyProvider, TooltipPosition.BODY, BlockBCTile_Neptune.class);
        registrar.registerBlockComponent(autoCraftBodyProvider, BlockBCTile_Neptune.class);
        registrar.registerBlockComponent(laserTargetBodyProvider, BlockBCTile_Neptune.class);
        registrar.registerBlockComponent(assemblyCraftBodyProvider, BlockBCTile_Neptune.class);
        registrar.registerBlockComponent(mjStorageBodyProvider, BlockBCTile_Neptune.class);
    }
}
