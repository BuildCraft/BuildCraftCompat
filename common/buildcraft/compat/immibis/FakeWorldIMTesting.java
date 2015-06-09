package buildcraft.compat.immibis;

import java.io.File;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

public class FakeWorldIMTesting extends World {
	public FakeWorldIMTesting() {
		super(new FakeWorldIMSaveHandler(), "IMTestingFakeWorld", new WorldSettings(0, WorldSettings.GameType.CREATIVE, true, false, WorldType.FLAT), new WorldProvider() {
			@Override
			public String getDimensionName() {
				return "IMTestingFakeWorld";
			}
		}, null);
	}

	@Override
	protected IChunkProvider createChunkProvider() {
		return null;
	}

	@Override
	protected int func_152379_p() {
		return 1;
	}

	@Override
	public Entity getEntityByID(int p_73045_1_) {
		return null;
	}

	private static class FakeWorldIMSaveHandler implements ISaveHandler {

		@Override
		public WorldInfo loadWorldInfo() {
			return null;
		}

		@Override
		public void checkSessionLock() throws MinecraftException {

		}

		@Override
		public IChunkLoader getChunkLoader(WorldProvider p_75763_1_) {
			return null;
		}

		@Override
		public void saveWorldInfoWithPlayer(WorldInfo p_75755_1_, NBTTagCompound p_75755_2_) {

		}

		@Override
		public void saveWorldInfo(WorldInfo p_75761_1_) {

		}

		@Override
		public IPlayerFileData getSaveHandler() {
			return null;
		}

		@Override
		public void flush() {

		}

		@Override
		public File getWorldDirectory() {
			return null;
		}

		@Override
		public File getMapFileFromName(String p_75758_1_) {
			return null;
		}

		@Override
		public String getWorldDirectoryName() {
			return null;
		}
	}
}
