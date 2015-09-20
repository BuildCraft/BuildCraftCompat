package buildcraft.compat.immibis;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.WorldServer;

import buildcraft.api.blueprints.BuildingPermission;
import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.api.blueprints.MappingRegistry;
import buildcraft.api.blueprints.Schematic;
import buildcraft.api.blueprints.SchematicTile;
import buildcraft.api.blueprints.Translation;
import buildcraft.api.core.BCLog;
import buildcraft.api.core.BlockIndex;
import buildcraft.api.core.IInvSlot;
import buildcraft.api.core.JavaTools;
import buildcraft.core.blueprints.SchematicRegistry;
import buildcraft.core.proxy.CoreProxy;
import mods.immibis.microblocks.api.EnumPosition;
import mods.immibis.microblocks.api.Part;
import static mods.immibis.microblocks.api.EnumPosition.Centre;
import static mods.immibis.microblocks.api.EnumPosition.CornerNXNYNZ;
import static mods.immibis.microblocks.api.EnumPosition.CornerNXNYPZ;
import static mods.immibis.microblocks.api.EnumPosition.CornerNXPYNZ;
import static mods.immibis.microblocks.api.EnumPosition.CornerNXPYPZ;
import static mods.immibis.microblocks.api.EnumPosition.CornerPXNYNZ;
import static mods.immibis.microblocks.api.EnumPosition.CornerPXNYPZ;
import static mods.immibis.microblocks.api.EnumPosition.CornerPXPYNZ;
import static mods.immibis.microblocks.api.EnumPosition.CornerPXPYPZ;
import static mods.immibis.microblocks.api.EnumPosition.EdgeNXNY;
import static mods.immibis.microblocks.api.EnumPosition.EdgeNXNZ;
import static mods.immibis.microblocks.api.EnumPosition.EdgeNXPY;
import static mods.immibis.microblocks.api.EnumPosition.EdgeNXPZ;
import static mods.immibis.microblocks.api.EnumPosition.EdgeNYNZ;
import static mods.immibis.microblocks.api.EnumPosition.EdgeNYPZ;
import static mods.immibis.microblocks.api.EnumPosition.EdgePXNY;
import static mods.immibis.microblocks.api.EnumPosition.EdgePXNZ;
import static mods.immibis.microblocks.api.EnumPosition.EdgePXPY;
import static mods.immibis.microblocks.api.EnumPosition.EdgePXPZ;
import static mods.immibis.microblocks.api.EnumPosition.EdgePYNZ;
import static mods.immibis.microblocks.api.EnumPosition.EdgePYPZ;
import static mods.immibis.microblocks.api.EnumPosition.FaceNX;
import static mods.immibis.microblocks.api.EnumPosition.FaceNY;
import static mods.immibis.microblocks.api.EnumPosition.FaceNZ;
import static mods.immibis.microblocks.api.EnumPosition.FacePX;
import static mods.immibis.microblocks.api.EnumPosition.FacePY;
import static mods.immibis.microblocks.api.EnumPosition.FacePZ;
import static mods.immibis.microblocks.api.EnumPosition.PostX;
import static mods.immibis.microblocks.api.EnumPosition.PostY;
import static mods.immibis.microblocks.api.EnumPosition.PostZ;

public class SchematicTileMicroblocks extends SchematicTile {
	private final SchematicTile wrapped;

	public SchematicTileMicroblocks(SchematicRegistry.SchematicConstructor c) {
		SchematicTile t = null;
		try {
			 t = (SchematicTile) c.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		wrapped = t;
	}

	private static final EnumPosition[] shiftMatrix = {
		Centre,
		FaceNZ, FacePZ, FaceNY, FacePY, FacePX, FaceNX,
		EdgeNYNZ, EdgePYNZ,
		EdgeNYPZ, EdgePYPZ,
		EdgePXNZ, EdgeNXNZ,
		EdgePXPZ, EdgeNXPZ,
		EdgePXNY, EdgeNXNY,
		EdgePXPY, EdgeNXPY,
		CornerPXNYNZ, CornerNXNYNZ,
		CornerPXPYNZ, CornerNXPYNZ,
		CornerPXNYPZ, CornerNXNYPZ,
		CornerPXPYPZ, CornerNXPYPZ,
		PostZ, PostY, PostX
	};

	static {
		for(int a = 0; a < shiftMatrix.length; a++) {
			int b = a;
			for (int i = 0; i < 4; i++) {
				b = shiftMatrix[b].ordinal();
			}
			if (a != b) {
				BCLog.logger.error("[IMB Rotation Mapping] Location " + a + " returns to " + b + " instead! This should NOT happen!");
			}
		}
	}

	@Override
	public ItemStack useItem(IBuilderContext context, ItemStack req, IInvSlot slot) {
		return wrapped.useItem(context, req, slot);
	}

	@Override
	public void translateToBlueprint(Translation transform) {
		wrapped.translateToBlueprint(transform);
		tileNBT = wrapped.tileNBT;
	}

	@Override
	public void translateToWorld(Translation transform) {
		wrapped.translateToWorld(transform);
		tileNBT = wrapped.tileNBT;
	}

	@Override
	public void idsToBlueprint(MappingRegistry registry) {
		wrapped.idsToBlueprint(registry);
	}

	@Override
	public void idsToWorld(MappingRegistry registry) {
		wrapped.idsToWorld(registry);
	}

	@Override
	public void initializeFromObjectAt(IBuilderContext context, int x, int y, int z) {
		wrapped.block = block;
		wrapped.meta = meta;
		wrapped.defaultPermission = defaultPermission;
		wrapped.storedRequirements = storedRequirements;
		wrapped.tileNBT = tileNBT;

		wrapped.initializeFromObjectAt(context, x, y, z);

		block = wrapped.block;
		meta = wrapped.meta;
		defaultPermission = wrapped.defaultPermission;
		storedRequirements = wrapped.storedRequirements;
		tileNBT = wrapped.tileNBT;
	}

	@Override
	public void placeInWorld(IBuilderContext context, int x, int y, int z, LinkedList<ItemStack> stacks) {
		wrapped.placeInWorld(context, x, y, z, stacks);
	}

	@Override
	public void getRequirementsForPlacement(IBuilderContext context, LinkedList<ItemStack> requirements) {
		wrapped.getRequirementsForPlacement(context, requirements);
	}

	@Override
	public int getEnergyRequirement(LinkedList<ItemStack> stacksUsed) {
		return wrapped.getEnergyRequirement(stacksUsed);
	}

	@Override
	public LinkedList<ItemStack> getStacksToDisplay(LinkedList<ItemStack> stackConsumed) {
		return wrapped.getStacksToDisplay(stackConsumed);
	}

	@Override
	public Schematic.BuildingStage getBuildStage() {
		return wrapped.getBuildStage();
	}

	@Override
	public boolean isAlreadyBuilt(IBuilderContext context, int x, int y, int z) {
		return wrapped.isAlreadyBuilt(context, x, y, z);
	}

	@Override
	public boolean doNotBuild() {
		return wrapped.doNotBuild();
	}

	@Override
	public boolean doNotUse() {
		return wrapped.doNotUse();
	}

	@Override
	public BuildingPermission getBuildingPermission() {
		return wrapped.getBuildingPermission();
	}

	@Override
	public void postProcessing(IBuilderContext context, int x, int y, int z) {
		wrapped.postProcessing(context, x, y, z);
	}

	@Override
	public void writeSchematicToNBT(NBTTagCompound nbt, MappingRegistry registry) {
		wrapped.writeSchematicToNBT(nbt, registry);
	}

	@Override
	public void readSchematicFromNBT(NBTTagCompound nbt, MappingRegistry registry) {
		wrapped.readSchematicFromNBT(nbt, registry);

		block = wrapped.block;
		meta = wrapped.meta;
		defaultPermission = wrapped.defaultPermission;
		storedRequirements = wrapped.storedRequirements;
		tileNBT = wrapped.tileNBT;
	}

	@Override
	public int buildTime() {
		return wrapped.buildTime();
	}

	@Override
	public Set<BlockIndex> getPrerequisiteBlocks(IBuilderContext context) {
		return wrapped.getPrerequisiteBlocks(context);
	}

	@Override
	public void storeRequirements(IBuilderContext context, int x, int y, int z) {
		wrapped.storeRequirements(context, x, y, z);
		storeMicroblockRequirements(context, x, y, z);

		storedRequirements = wrapped.storedRequirements;
	}

	protected void storeMicroblockRequirements(IBuilderContext context, int x, int y, int z) {
		NBTTagList list = getICMP(wrapped.tileNBT);
		if (list != null) {
			ArrayList rqs = new ArrayList();
			EntityPlayer fakePlayer = CoreProxy.proxy.getBuildCraftPlayer((WorldServer) context.world()).get();

			for (int i = 0; i < list.tagCount(); i++) {
				Part part = Part.readFromNBT(list.getCompoundTagAt(i));
				rqs.add(part.type.getDroppedStack(part, fakePlayer));
				System.out.println(part.type.getDroppedStack(part, fakePlayer).toString());
			}

			wrapped.storedRequirements = (ItemStack[]) JavaTools.concat(wrapped.storedRequirements, rqs.toArray(new ItemStack[rqs.size()]));
		}
	}

	@Override
	public void rotateLeft(IBuilderContext context) {
		wrapped.rotateLeft(context);

		block = wrapped.block;
		meta = wrapped.meta;
		tileNBT = wrapped.tileNBT;

		NBTTagList list = getICMP(tileNBT);
		if (list != null) {
			for (int i = 0; i < list.tagCount(); i++) {
				NBTTagCompound part = list.getCompoundTagAt(i);
				part.setByte("pos", (byte) shiftMatrix[part.getByte("pos")].ordinal());
			}
		}
	}

	private NBTTagList getICMP(NBTTagCompound nbtInit) {
		if (nbtInit == null) {
			return null;
		}

		NBTTagCompound nbt = nbtInit;

		if (nbt.hasKey("ImmibisCoreMicroblocks")) {
			nbt = nbt.getCompoundTag("ImmibisCoreMicroblocks");
		}

		return nbt.getTagList("ICMP", 10);
	}
}
