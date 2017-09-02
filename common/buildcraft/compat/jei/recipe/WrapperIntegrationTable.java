//package buildcraft.compat.jei.recipe;
//
//import java.awt.Color;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.google.common.collect.ImmutableList;
//
//import net.minecraft.client.Minecraft;
//import net.minecraft.util.ResourceLocation;
//
//import net.minecraftforge.fluids.FluidStack;
//
//import buildcraft.api.recipes.IIntegrationRecipe;
//
//import javax.annotation.Nonnull;
//import mezz.jei.api.IGuiHelper;
//import mezz.jei.api.gui.IDrawableAnimated;
//import mezz.jei.api.gui.IDrawableStatic;
//import mezz.jei.api.recipe.IRecipeWrapper;
//
//public class WrapperIntegrationTable implements IRecipeWrapper {
//    private final IIntegrationRecipe recipe;
//    @Nonnull
//    private final IDrawableAnimated progressBar;
//	private final List inputs, outputs;
//
//    public WrapperIntegrationTable(@Nonnull IGuiHelper guiHelper, IIntegrationRecipe recipe) {
//        this.recipe = recipe;
//
//		List<Object> inputs = new ArrayList<>();
//		inputs.add(recipe.getExampleInput());
//		inputs.addAll(recipe.getExampleExpansions());
//		this.inputs = ImmutableList.copyOf(inputs);
//		this.outputs = ImmutableList.of(recipe.getExampleOutput());
//
//		ResourceLocation backgroundLocation = new ResourceLocation("buildcraftsilicon", "textures/gui/integration_table.png");
//		IDrawableStatic progressDrawable = guiHelper.createDrawable(backgroundLocation, 176, 17, 4, 69, 0, 0, 0, 0);
//		progressBar = guiHelper.createAnimatedDrawable(progressDrawable, recipe.getEnergyCost() / 720, IDrawableAnimated.StartDirection.BOTTOM, false);
//    }
//
//    @Override
//    public List getInputs() {
//        return inputs;
//    }
//
//    @Override
//    public List getOutputs() {
//        return outputs;
//    }
//
//    @Override
//    public List<FluidStack> getFluidInputs() {
//        return null;
//    }
//
//    @Override
//    public List<FluidStack> getFluidOutputs() {
//        return null;
//    }
//
//    @Override
//    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight) {}
//
//    @Override
//    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
//        minecraft.fontRendererObj.drawString(recipe.getEnergyCost() + " RF", 80, 52, Color.gray.getRGB());
//    }
//
//    @Override
//    public void drawAnimations(Minecraft minecraft, int recipeWidth, int recipeHeight) {
//        progressBar.draw(minecraft, 156, 1);
//    }
//
//    @Override
//    public List<String> getTooltipStrings(int mouseX, int mouseY) {
//        return null;
//    }
//
//    @Override
//    public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
//        return false;
//    }
//}
