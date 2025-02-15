package buildcraft.compat.module.rei;

import com.mojang.blaze3d.systems.RenderSystem;
import me.shedaniel.math.Point;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ReiUtils {
    public static final int PADDING = 5;

    public static void drawAnimation(GuiGraphics graphics, Point lu, ResourceLocation rl, int ticksPerCycle, int x, int y, int u, int v, int fullWidth, int fullHeight, ReiUtils.StartPosition startPosition) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(770, 771, 1, 0);
        RenderSystem.blendFunc(770, 771);
        final double width_double = fullWidth;
        final double height_double = fullHeight;
        if (startPosition == StartPosition.LEFT) {
            int width = Mth.ceil((System.currentTimeMillis() / (ticksPerCycle * 50 / width_double) % fullWidth));
            graphics.blit(
                    rl,
                    lu.getX() + x, lu.getY() + y,
                    u, v,
                    width, fullHeight
            );
        } else if (startPosition == StartPosition.RIGHT) {
            int width = Mth.ceil((System.currentTimeMillis() / (ticksPerCycle * 50 / width_double) % fullWidth));
            graphics.blit(
                    rl,
                    lu.getX() + x + fullWidth - width, lu.getY() + y,
                    u + fullWidth - width, v,
                    width, fullHeight
            );
        } else if (startPosition == StartPosition.TOP) {
            int height = Mth.ceil((System.currentTimeMillis() / (ticksPerCycle * 50 / height_double) % fullHeight));
            graphics.blit(
                    rl,
                    lu.getX() + x, lu.getY() + y,
                    u, v,
                    fullWidth, height
            );
        } else if (startPosition == StartPosition.BOTTOM) {
            int height = Mth.ceil((System.currentTimeMillis() / (ticksPerCycle * 50 / height_double) % fullHeight));
            graphics.blit(
                    rl,
                    lu.getX() + x, lu.getY() + y + fullHeight - height,
                    u, v + fullHeight - height,
                    fullWidth, height
            );
        }
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static enum StartPosition {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM;
    }
}
