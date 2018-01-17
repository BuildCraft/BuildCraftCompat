package buildcraft.compat.network;

import javax.annotation.Nullable;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** A creator that can */
// TODO: Move this into bc lib and make it more useful!
public interface IGuiCreator {
    Enum<?> getGuiType();

    /** @param data The extra 24 bits that are unused by the byte ID. */
    @Nullable
    @SideOnly(Side.CLIENT)
    GuiContainer getClientGuiElement(int data, EntityPlayer player);

    /** @param data The extra 24 bits that are unused by the byte ID. */
    @Nullable
    Container getServerGuiElement(int data, EntityPlayer player);
}
