package buildcraft.compat;

import net.minecraftforge.fml.loading.FMLLoader;

public abstract class BCCompatProxy {
    private static BCCompatProxy proxy = null;

    public static BCCompatProxy getProxy() {
        if (proxy == null) {
            switch (FMLLoader.getDist()) {
                case CLIENT:
                    proxy = new BCCompatProxy.ClientProxy();
                    break;
                case DEDICATED_SERVER:
                    proxy = new BCCompatProxy.ServerProxy();
                    break;
            }
        }
        return proxy;
    }

    public void fmlPreInit() {
    }

    public void fmlInit() {
    }

    public void fmlPostInit() {
    }

    public static class ServerProxy extends BCCompatProxy {

    }

    public static class ClientProxy extends BCCompatProxy {
        @Override
        public void fmlPreInit() {
            super.fmlPreInit();
            BCCompatModels.fmlPreInit();
        }
    }
}
