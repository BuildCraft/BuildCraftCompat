package buildcraft.compat.nei;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import buildcraft.BuildCraftCompat;
import buildcraft.BuildCraftMod;
import buildcraft.api.core.BCLog;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.api.recipes.IIntegrationRecipe;
import buildcraft.silicon.gui.ContainerIntegrationTable;
import buildcraft.silicon.gui.GuiIntegrationTable;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.api.API;

public class RecipeHandlerIntegrationTable extends RecipeHandlerBase
{
    private static final HashMap<IIntegrationRecipe, CachedIntegrationTableRecipe> recipeCache = new HashMap<IIntegrationRecipe, CachedIntegrationTableRecipe>();
    private static Class<? extends GuiContainer> guiClass;
    
    @Override
    public void prepare() {
        API.setGuiOffset((Class)(RecipeHandlerIntegrationTable.guiClass = (Class<? extends GuiContainer>)GuiIntegrationTable.class), 5, 18);
    }
    
    public String getRecipeName() {
        return StatCollector.translateToLocal("tile.integrationTableBlock.name");
    }
    
    @Override
    public String getRecipeID() {
        return "buildcraft.integrationTable";
    }
    
    public String getGuiTexture() {
        return "buildcraftsilicon:textures/gui/integration_table.png";
    }
    
    public void loadTransferRects() {
        //this.addTransferRect(89, 7, 6, 72);
    }
    
    public Class<? extends GuiContainer> getGuiClass() {
        return RecipeHandlerIntegrationTable.guiClass;
    }
    
    public int recipiesPerPage() {
        return 2;
    }
    
    public void drawBackground(final int recipe) {
        this.changeToGuiTexture();
        GuiDraw.drawTexturedModalRect(0, 5, 5, 18, 166, 80);
    }
    
    public void drawExtras(final int recipe) {
        GuiDraw.drawStringC(((CachedIntegrationTableRecipe) this.arecipes.get(recipe)).energy + " RF", 108, 10, 8421504, false);
    }
    
    @Override
    public void loadAllRecipes() {
        for (final IIntegrationRecipe recipe : BuildcraftRecipeRegistry.integrationTable.getRecipes()) {
            this.arecipes.add(createRecipe(recipe));
        }
    }

    private CachedIntegrationTableRecipe createRecipe(IIntegrationRecipe recipe) {
        //if (!recipeCache.containsKey(recipe)) {
        //    recipeCache.put(recipe, new CachedIntegrationTableRecipe(recipe));
        //}
        //CachedIntegrationTableRecipe r = recipeCache.get(recipe);
        //r.expectedOutput = null;
        //return r;
        return new CachedIntegrationTableRecipe(recipe);
    }

    @Override
    public void loadCraftingRecipes(final ItemStack result) {
        for (final IIntegrationRecipe recipe : BuildcraftRecipeRegistry.integrationTable.getRecipes()) {
            final CachedIntegrationTableRecipe crecipe = createRecipe(recipe);
            if (!crecipe.outputs.contains(result)) {
                continue;
            }
            //crecipe.expectedOutput = result;
            if (crecipe.getResult() == null) {
                BCLog.logger.warn("IntegrationRecipe with null result detected: " + crecipe.getClass().getName() + ". This is a bug!");
                continue;
            }
            this.arecipes.add(crecipe);
        }
    }
    
    @Override
    public void loadUsageRecipes(final ItemStack ingredient) {
        for (final IIntegrationRecipe recipe : BuildcraftRecipeRegistry.integrationTable.getRecipes()) {
            final CachedIntegrationTableRecipe crecipe = createRecipe(recipe);
            boolean found = false;
            for (PositionedStack s : crecipe.inputs) {
                if (s.contains(ingredient)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                continue;
            }
            crecipe.setIngredientPermutation((Collection)crecipe.inputs, ingredient);
            this.arecipes.add(crecipe);
        }
    }
    
    public class CachedIntegrationTableRecipe extends CachedBaseRecipe {
        public final List<PositionedStack> inputs;
        public final PositionedStack outputs;
        public final int energy;
        public final IIntegrationRecipe recipe;

        public CachedIntegrationTableRecipe(IIntegrationRecipe recipe) {
            List<ItemStack> exampleInputs = recipe.getExampleInput();
            List<List<ItemStack>> expansionExamples = recipe.getExampleExpansions();

            this.recipe = recipe;
            this.energy = recipe.getEnergyCost();
            this.inputs = new ArrayList<PositionedStack>(9);
            this.inputs.add(new PositionedStack(exampleInputs, ContainerIntegrationTable.SLOT_X[0] - 5, ContainerIntegrationTable.SLOT_Y[0] - 13, true));
            int iMax = 0;
            for (ItemStack input : exampleInputs) {
                int v = recipe.getMaximumExpansionCount(input);
                if (v > iMax) {
                    iMax = v;
                }
                if (iMax == 8) {
                    break;
                }
            }
            if (iMax <= 0) {
                iMax = 1;
            }

            for (int i = 0; i < iMax; i++) {
                List<ItemStack> exp = expansionExamples.get(i % expansionExamples.size());
                //List<ItemStack> target = new ArrayList<ItemStack>();
                //target.add(new ItemStack(Blocks.air));
                //target.addAll(exp);
                this.inputs.add(new PositionedStack(exp,
                        ContainerIntegrationTable.SLOT_X[i + 1] - 5, ContainerIntegrationTable.SLOT_Y[i + 1] - 13, true));
            }

            this.outputs = new PositionedStack(recipe.getExampleOutput(), 138 - 5, 49 - 13, true);
        }

        @Override
        public PositionedStack getResult() {
            List<PositionedStack> ingr = this.getCycledIngredients(cycleticks / 20, (List)this.inputs);
            ItemStack input = ingr.get(0).item;
            List<ItemStack> exps = new ArrayList<ItemStack>();
            for (int i = 1; i < ingr.size(); i++) {
                exps.add(ingr.get(i).item);
            }
            ItemStack output = recipe.craft(input, exps, true);
            if (output != null) {
                return new PositionedStack(output, 138 - 5, 49 - 13, true);
            } else {
                return null;
            }
        }

        public List<PositionedStack> getIngredients() {
            return (List<PositionedStack>)this.getCycledIngredients(cycleticks / 20, (List)this.inputs);
        }
    }
}
