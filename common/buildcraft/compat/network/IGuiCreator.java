package buildcraft.compat.network;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public interface IGuiCreator {
    Enum<?> getGuiType();

    @Nullable
    @OnlyIn(Dist.CLIENT)
//    GuiContainer getClientGuiElement(int var1, EntityPlayer var2);
    ContainerScreen<?> getClientGuiElement(int var1, PlayerEntity var2);

    @Nullable
//    Container getServerGuiElement(int var1, EntityPlayer var2);
    ContainerScreen getServerGuiElement(int var1, PlayerEntity var2);
}
