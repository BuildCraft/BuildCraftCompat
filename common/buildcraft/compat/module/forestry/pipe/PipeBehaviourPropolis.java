package buildcraft.compat.module.forestry.pipe;

import java.io.IOException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import buildcraft.api.core.EnumPipePart;
import buildcraft.api.transport.pipe.IPipe;
import buildcraft.api.transport.pipe.PipeBehaviour;
import buildcraft.api.transport.pipe.PipeEventHandler;
import buildcraft.api.transport.pipe.PipeEventItem;

import buildcraft.lib.cap.CapabilityHelper;

import buildcraft.compat.CompatUtils;
import buildcraft.compat.network.CompatGui;
import buildcraft.compat.network.IGuiCreator;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.GeneticCapabilities;
import forestry.api.genetics.IFilterLogic;
import forestry.api.genetics.IFilterLogic.INetworkHandler;
import forestry.sorting.DefaultFilterRuleType;
import forestry.sorting.gui.ContainerGeneticFilter;
import forestry.sorting.gui.GuiGeneticFilter;
import forestry.sorting.tiles.IFilterContainer;

public class PipeBehaviourPropolis extends PipeBehaviour implements IFilterContainer, INetworkHandler, IGuiCreator {

    private final CapabilityHelper caps = new CapabilityHelper();
    private final IFilterLogic filter = AlleleManager.filterRegistry.createLogic(this, this);

    {
        caps.addCapabilityInstance(GeneticCapabilities.FILTER_LOGIC, filter, EnumPipePart.VALUES);
        caps.addCapabilityInstance(CompatUtils.CAP_GUI_CREATOR, this, EnumPipePart.CENTER);
    }

    public PipeBehaviourPropolis(IPipe pipe) {
        super(pipe);
    }

    public PipeBehaviourPropolis(IPipe pipe, NBTTagCompound nbt) {
        super(pipe, nbt);
        filter.readFromNBT(nbt.getCompoundTag("filter"));
    }

    @Override
    public NBTTagCompound writeToNbt() {
        NBTTagCompound nbt = super.writeToNbt();
        nbt.setTag("filter", filter.writeToNBT(new NBTTagCompound()));
        return nbt;
    }

    @Override
    public void readPayload(PacketBuffer buffer, Side side, MessageContext ctx) throws IOException {
        super.readPayload(buffer, side, ctx);
        if (side == Side.CLIENT) {
            filter.readGuiData(buffer);
        }
    }

    @Override
    public void writePayload(PacketBuffer buffer, Side side) {
        super.writePayload(buffer, side);
        if (side == Side.SERVER) {
            // FIXME: Inefficient to be sending gui updates all the time
            // but fixing this requires proper fixes throughout the net code :/
            filter.writeGuiData(buffer);
        }
    }

    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing) {
        T value = caps.getCapability(capability, facing);
        if (value != null) {
            return value;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public int getTextureIndex(EnumFacing face) {
        return face == null ? 0 : face.ordinal() + 1;
    }

    @Override
    public boolean onPipeActivate(EntityPlayer player, RayTraceResult trace, float hitX, float hitY, float hitZ,
        EnumPipePart part) {
        if (!getWorldObj().isRemote) {
            // TODO: Properly abstract this in to make GUI's a bit more sane!
            CompatGui.FORESTRY_PROPOLIS_PIPE.openGui(player, pipe.getHolder().getPipePos());
            // sendUpdatePacket(ImmutableList.of());
        }
        return true;
    }

    @PipeEventHandler
    public void sideCheck(PipeEventItem.SideCheck event) {
        ItemStack stack = event.stack;
        for (EnumFacing face : EnumFacing.VALUES) {
            if (!filter.isValid(stack, face)) {
                event.disallow(face);
            } else if (filter.getRule(face) == DefaultFilterRuleType.ANYTHING) {
                event.decreasePriority(face);
            }
        }
    }

    // IFilterContainer

    @Override
    public BlockPos getCoordinates() {
        return pipe.getHolder().getPipePos();
    }

    @Override
    public World getWorldObj() {
        return pipe.getHolder().getPipeWorld();
    }

    @Override
    public String getUnlocalizedTitle() {
        return ForestryPipes.pipeItemPropolis.getUnlocalizedName() + ".name";
    }

    @Override
    @Nullable
    public IInventory getBuffer() {
        return null;
    }

    @Override
    public TileEntity getTileEntity() {
        return pipe.getHolder().getPipeTile();
    }

    @Override
    public IFilterLogic getLogic() {
        return filter;
    }

    // INetworkHandler

    @Override
    public void sendToPlayers(IFilterLogic logic, WorldServer server, EntityPlayer currentPlayer) {
        for (EntityPlayer player : server.playerEntities) {
            if (player != currentPlayer && player.openContainer instanceof ContainerGeneticFilter) {
                ContainerGeneticFilter currentContainer = (ContainerGeneticFilter) currentPlayer.openContainer;
                ContainerGeneticFilter otherContainer = (ContainerGeneticFilter) player.openContainer;
                if (otherContainer.hasSameTile(currentContainer)) {
                    otherContainer.setGuiNeedsUpdate(true);
                }
            }
        }
    }

    // IGuiCreator

    @Override
    public Enum<?> getGuiType() {
        return CompatGui.FORESTRY_PROPOLIS_PIPE;
    }

    @Override
    @Nullable
    public Container getServerGuiElement(int data, EntityPlayer player) {
        return new ContainerPropolisPipe(this, player);
    }

    @Override
    @Nullable
    @SideOnly(Side.CLIENT)
    public GuiContainer getClientGuiElement(int data, EntityPlayer player) {
        return new GuiGeneticFilter(this, player.inventory);
    }
}
