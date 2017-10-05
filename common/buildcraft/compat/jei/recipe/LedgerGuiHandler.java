//package buildcraft.compat.jei;
//
//import java.awt.Rectangle;
//import java.util.ArrayList;
//import java.util.List;
//
//import buildcraft.core.lib.gui.GuiBuildCraft;
//import buildcraft.core.lib.gui.Ledger;
//
//import mezz.jei.api.gui.IAdvancedGuiHandler;
//
//public class LedgerGuiHandler implements IAdvancedGuiHandler<GuiBuildCraft> {
//    @Override
//    public Class<GuiBuildCraft> getGuiContainerClass() {
//        return GuiBuildCraft.class;
//    }
//
//    @Override
//    public List<Rectangle> getGuiExtraAreas(GuiBuildCraft gui) {
//        List<Rectangle> rectangles = new ArrayList<>();
//        int guiLeft = (gui.width - gui.xSize()) / 2;
//        int guiTop = (gui.height - gui.ySize()) / 2;
//
//        int yPos = 8;
//        for (Ledger l : gui.ledgerManager.getAll()) {
//            if (l.isVisible()) {
//                rectangles.add(new Rectangle(guiLeft + gui.xSize(), guiTop + yPos, l.getWidth(), l.getHeight()));
//                yPos += l.getHeight();
//            }
//        }
//
//        return rectangles;
//    }
//}
