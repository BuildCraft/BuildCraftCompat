package mods.immibis.core.api.porting;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;

public abstract class PortableBaseMod {
	public class TickEventServerHandler {
		@SubscribeEvent
		public void tickEventServer(TickEvent.ServerTickEvent event) {
			if(event.phase != Phase.START) return;
			onTickInGame();
		}
	}
	
	public class TickEventClientHandler {
		@SubscribeEvent
		public void tickEventClient(TickEvent.ClientTickEvent event) {
			if(event.phase != Phase.START) return;
			onTickInGame();
		}
	}
	
    public boolean onTickInGame() {return false;}

    public void enableClockTicks(final boolean server) {
    	FMLCommonHandler.instance().bus().register(server
    			? new TickEventServerHandler()
    			: new TickEventClientHandler());
    }
}
