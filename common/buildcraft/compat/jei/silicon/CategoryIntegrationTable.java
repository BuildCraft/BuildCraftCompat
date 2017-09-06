package buildcraft.compat.jei.silicon;

import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import buildcraft.api.BCModules;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;

public class CategoryIntegrationTable extends BlankRecipeCategory<WrapperIntegrationTable> {
    public static final String UID = "buildcraft-compat:silicon.integration";

    protected final ResourceLocation backgroundLocation;
    private final IDrawable background;
    private WrapperIntegrationTable wrapper = null;

    public CategoryIntegrationTable(IGuiHelper guiHelper) {
        backgroundLocation = new ResourceLocation("buildcraftsilicon", "textures/gui/integration_table.png");
        background = guiHelper.createDrawable(backgroundLocation, 17, 22, 153, 71, 0, 0, 9, 0);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return "Integration Table";
    }

    @Override
    public String getModName() {
        return BCModules.SILICON.name();
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, WrapperIntegrationTable recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
        int inventoryIndex = 0;
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                int slotIndex = ((x == 1) && (y == 1)) ? 0 : (x + y * 3 + 1);
                if (inputs.size() > slotIndex) {
                    guiItemStacks.init(inventoryIndex, true, 19 + x * 25, 24 + y * 25);
                    guiItemStacks.set(inventoryIndex, inputs.get(slotIndex));
                    inventoryIndex++;
                }
                // this.addSlotToContainer(new SlotBase(x == 1 && y == 1 ? tile.invTarget : tile.invToIntegrate, indexes[x + y * 3], 19 + x * 25, 24 + y * 25));
            }
        }

//        for (int i = 0; i < wrapper.getInputs().size(); i++) {
//            int x = ContainerIntegrationTable.SLOT_X[i] - 9;
//            int y = ContainerIntegrationTable.SLOT_Y[i] - 23;
//            guiItemStacks.init(i, true, x, y);
//            guiItemStacks.set(i, (List) wrapper.getInputs().get(i));
//        }

        guiItemStacks.init(inventoryIndex, false, 129, 26);
        guiItemStacks.set(inventoryIndex, ingredients.getOutputs(ItemStack.class).get(0));
    }
}
