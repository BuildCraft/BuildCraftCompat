package buildcraft.compat.nei;

import java.awt.Rectangle;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.EnumChatFormatting;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;

public class PositionedStackAdv extends PositionedStack {
    
    private List<String> tooltip = new ArrayList<String>();
    
    public PositionedStackAdv(Object object, int x, int y) {
        super(object, x, y);
    }
    
    public PositionedStackAdv(Object object, int x, int y, List<String> tooltip) {
        super(object, x, y);
        this.addToTooltip(tooltip);
    }
    
    public Rectangle getRect() {
        return new Rectangle(this.relx - 1, this.rely - 1, 18, 18);
    }
    
    public List<String> handleTooltip(GuiRecipe guiRecipe, List<String> currenttip) {
        if (!this.tooltip.isEmpty()) {
            for (String tip : this.tooltip) {
                currenttip.add(tip);
            }
        }
        return currenttip;
    }
    
    public PositionedStackAdv addToTooltip(List<String> lines) {
        for (String tip : lines) {
            this.tooltip.add(tip);
        }
        return this;
    }
    
    public PositionedStackAdv addToTooltip(String line) {
        this.tooltip.add(line);
        return this;
    }
}
