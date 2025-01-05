package buildcraft.compat.forestry.pipes.gui;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;

import codechicken.nei.ItemPanel;
import codechicken.nei.LayoutManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import buildcraft.BuildCraftCompat;
import buildcraft.compat.CompatModuleForestry;
import buildcraft.compat.forestry.pipes.EnumFilterType;
import buildcraft.compat.forestry.pipes.PipeItemsPropolis;
import buildcraft.compat.forestry.pipes.PipeLogicPropolis;
import buildcraft.core.lib.gui.GuiBuildCraft;
import buildcraft.core.lib.gui.tooltips.ToolTip;
import buildcraft.core.lib.gui.tooltips.ToolTipLine;
import buildcraft.core.lib.gui.widgets.Widget;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IApiaristTracker;
import forestry.api.apiculture.IBee;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;

/**
 * GuiScreen for propolis pipes.
 *
 * @author SirSengir
 */
public class GuiPropolisPipe extends GuiBuildCraft {

    private static final ResourceLocation texture = new ResourceLocation(
            "buildcraftcompat",
            "textures/gui/propolisPipe.png");
    private final PipeLogicPropolis pipeLogic;

    public GuiPropolisPipe(EntityPlayer player, PipeItemsPropolis pipe) {
        super(new ContainerPropolisPipe(player.inventory, pipe), null, texture);

        pipeLogic = pipe.pipeLogic;
        // Request filter set update if on client
        if (pipe.getWorld().isRemote) {
            pipeLogic.requestFilterSet();
        }

        xSize = 175;
        ySize = 225;

        for (int i = 0; i < 6; i++) {
            container.addWidget(new TypeFilterSlot(8, 18 + i * 18, ForgeDirection.values()[i], pipeLogic));
        }

        IApiaristTracker tracker = CompatModuleForestry.beeRoot
                .getBreedingTracker(player.worldObj, player.getGameProfile());
        for (int i = 0; i < 6; i++) {
            for (int pattern = 0; pattern < 3; pattern++) {
                for (int allele = 0; allele < 2; allele++) {
                    int x = 44 + pattern * 45 + allele * 18;
                    int y = 18 + i * 18;
                    container.addWidget(
                            new SpeciesFilterSlot(
                                    tracker,
                                    x,
                                    y,
                                    ForgeDirection.values()[i],
                                    pattern,
                                    allele,
                                    pipeLogic));
                }
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        String title = StatCollector.translateToLocal("item.buildcraftPipe.pipeitemspropolis.name");
        fontRendererObj.drawString(title, getCenteredOffset(title), 6, 0x303030);
    }

    public boolean handleDragNDrop(int mousex, int mousey, ItemStack draggedStack, int button) {
        int relativeX = mousex - guiLeft;
        int relativeY = mousey - guiTop;
        for (Widget w : getContainer().getWidgets()) {
            if (w.isMouseOver(relativeX, relativeY) && w instanceof SpeciesFilterSlot) {
                return ((SpeciesFilterSlot) w).handleDragNDrop(draggedStack, button);
            }
        }
        return false;
    }

    class TypeFilterSlot extends Widget {

        private final ForgeDirection orientation;
        private final PipeLogicPropolis logic;

        public TypeFilterSlot(int x, int y, ForgeDirection orientation, PipeLogicPropolis logic) {
            super(x, y, 0, 0, 16, 16);
            this.orientation = orientation;
            this.logic = logic;
        }

        public EnumFilterType getType() {
            return logic.getTypeFilter(orientation);
        }

        @SideOnly(Side.CLIENT)
        public void draw(GuiBuildCraft gui, int guiX, int guiY, int mouseX, int mouseY) {
            EnumFilterType type = logic.getTypeFilter(orientation);
            IIcon icon = null;
            if (type != null) {
                icon = type.getIcon();
            }
            if (icon == null) {
                return;
            }

            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);

            gui.bindTexture(TextureMap.locationItemsTexture);

            gui.drawTexturedModelRectFromIcon(guiX + x, guiY + y, icon, w, h);
        }

        private final ToolTip toolTip = new ToolTip() {

            @Override
            public void refresh() {
                refreshTooltip();
            }
        };

        private void refreshTooltip() {
            toolTip.clear();
            EnumFilterType type = logic.getTypeFilter(orientation);
            String filterName = StatCollector
                    .translateToLocal("for.gui.pipe.filter." + type.toString().toLowerCase(Locale.ROOT));
            toolTip.add(new ToolTipLine(filterName));
        }

        @SideOnly(Side.CLIENT)
        @Override
        public ToolTip getToolTip() {
            return toolTip;
        }

        @Override
        public boolean handleMouseClick(int mouseX, int mouseY, int mouseButton) {
            EnumFilterType change = getType();
            if (change == null || isShiftKeyDown()) {
                change = EnumFilterType.CLOSED;
            } else {
                EnumFilterType[] values = EnumFilterType.values();
                int newTypeOrdinal = change.ordinal() + (mouseButton == 0 ? 1 : -1);
                if (newTypeOrdinal < 0) {
                    newTypeOrdinal = values.length - 1;
                } else if (newTypeOrdinal >= values.length) {
                    newTypeOrdinal = 0;
                }
                change = values[newTypeOrdinal];
            }
            pipeLogic.setTypeFilter(orientation, change);
            return true;
        }

    }

    class SpeciesFilterSlot extends Widget {

        private final IApiaristTracker tracker;
        private final ForgeDirection orientation;
        private final PipeLogicPropolis logic;
        private final int pattern;
        private final int allele;

        public SpeciesFilterSlot(IApiaristTracker tracker, int x, int y, ForgeDirection orientation, int pattern,
                int allele, PipeLogicPropolis logic) {
            super(x, y, 0, 0, 16, 16);
            this.tracker = tracker;
            this.orientation = orientation;
            this.pattern = pattern;
            this.allele = allele;
            this.logic = logic;
        }

        public IAlleleSpecies getSpecies() {
            return logic.getSpeciesFilter(orientation, pattern, allele);
        }

        public boolean isDefined() {
            IAlleleSpecies species = logic.getSpeciesFilter(orientation, pattern, allele);
            return species != null;
        }

        @SideOnly(Side.CLIENT)
        public void draw(GuiBuildCraft gui, int guiX, int guiY, int mouseX, int mouseY) {
            if (!isDefined()) {
                return;
            }

            IAlleleSpecies species = logic.getSpeciesFilter(orientation, pattern, allele);
            GL11.glDisable(GL11.GL_LIGHTING);

            gui.bindTexture(TextureMap.locationItemsTexture);

            for (int i = 0; i < 3; ++i) {

                IAlleleBeeSpecies beeSpecies = (IAlleleBeeSpecies) species;

                IIcon icon = beeSpecies.getIcon(EnumBeeType.DRONE, i);
                int color = beeSpecies.getIconColour(i);

                float colorR = (color >> 16 & 255) / 255.0F;
                float colorG = (color >> 8 & 255) / 255.0F;
                float colorB = (color & 255) / 255.0F;

                GL11.glColor4f(colorR, colorG, colorB, 1.0F);
                gui.drawTexturedModelRectFromIcon(guiX + x, guiY + y, icon, w, h);

            }
            GL11.glEnable(GL11.GL_LIGHTING);
        }

        private final ToolTip toolTip = new ToolTip() {

            @Override
            public void refresh() {
                refreshTooltip();
            }
        };

        private void refreshTooltip() {
            toolTip.clear();
            IAlleleSpecies species = logic.getSpeciesFilter(orientation, pattern, allele);
            if (species != null) {
                String speciesName = species.getName();
                toolTip.add(new ToolTipLine(speciesName));
            }
        }

        @Override
        public ToolTip getToolTip() {
            return toolTip;
        }

        @Override
        public boolean handleMouseClick(int mouseX, int mouseY, int mouseButton) {
            if (BuildCraftCompat.isLoaded("NotEnoughItems") && isNEIDragInProgress()) {
                return false;
            }
            IAlleleSpecies change = getSpecies();
            if (isShiftKeyDown()) {
                change = null;
            } else if (change == null) {
                for (Entry<String, IAllele> entry : AlleleManager.alleleRegistry.getRegisteredAlleles().entrySet()) {
                    if (!(entry.getValue() instanceof IAlleleBeeSpecies)) {
                        continue;
                    }

                    change = (IAlleleBeeSpecies) entry.getValue();

                    // pick first species on left button, last species on right button
                    if (mouseButton == 0) {
                        break;
                    }
                }
            } else {
                change = null;

                Iterator<Entry<String, IAllele>> it = AlleleManager.alleleRegistry.getRegisteredAlleles().entrySet()
                        .iterator();
                IAlleleBeeSpecies previous = null;
                boolean found = false;
                while (it.hasNext()) {
                    Entry<String, IAllele> entry = it.next();
                    if (!(entry.getValue() instanceof IAlleleBeeSpecies)) {
                        continue;
                    }

                    IAlleleBeeSpecies current = (IAlleleBeeSpecies) entry.getValue();
                    if (found) {
                        // left mouse button, use next
                        change = current;
                        break;
                    }

                    if (current.getUID().equals(getSpecies().getUID())) {
                        found = true;
                        if (mouseButton == 0) {
                            continue;
                        } else {
                            // right mouse button, use previous
                            change = previous;
                            break;
                        }
                    }

                    previous = current;
                }
            }

            pipeLogic.setSpeciesFilter(orientation, pattern, allele, change);
            return true;
        }

        private boolean isNEIDragInProgress() {
            ItemPanel itemPanel = null;
            try {
                itemPanel = (ItemPanel) Class.forName("codechicken.nei.ItemPanels").getField("itemPanel").get(null);
            } catch (Exception e) {
                try {
                    itemPanel = (ItemPanel) Class.forName("codechicken.nei.LayoutManager").getField("itemPanel").get(null);
                } catch (Exception ee) {
                    // pass
                }
            }
            // drag is handled by #handleDragNDrop
            return itemPanel != null && itemPanel.draggedStack != null;
        }

        public boolean handleDragNDrop(ItemStack draggedStack, int button) {
            IBee member = BeeManager.beeRoot.getMember(draggedStack);
            if (member != null) {
                IAlleleBeeSpecies speciesDragged = member.getGenome().getPrimary();
                pipeLogic.setSpeciesFilter(orientation, pattern, allele, speciesDragged);
                return true;
            }
            return false;
        }
    }
}
