package mods.immibis.core.api.porting;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class SidedProxy {
	public static final SidedProxy instance;
	
	public abstract File getMinecraftDir();
	public abstract double getPlayerReach(EntityPlayer ply);
	public abstract EntityPlayer getThePlayer();
	public abstract int getUniqueBlockModelID(String renderClass, boolean b);
	public abstract boolean isOp(GameProfile player);
	public abstract void registerTileEntity(Class<? extends TileEntity> clazz, String id, String rclass);
	public abstract boolean isWorldCurrent(World w);
	public abstract void registerItemRenderer(Item item, String renderClassName);
	public abstract void registerEntityRenderer(Class<? extends Entity> entClass, String renderClassName);
	
	public abstract boolean isDedicatedServer();
	
	public abstract Object createSidedObject(String clientClass, String serverClass);
	
	public boolean isOp(EntityPlayer var1) {
		return isOp(var1.getGameProfile());
	}
	
	static {
		try {
			instance = Class.forName(FMLLaunchHandler.side() == Side.CLIENT ? "mods.immibis.core.porting.ClientProxy142" : "mods.immibis.core.porting.ServerProxy142").asSubclass(SidedProxy.class).getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException
			| IllegalArgumentException | InvocationTargetException
			| NoSuchMethodException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
