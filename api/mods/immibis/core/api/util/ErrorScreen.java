package mods.immibis.core.api.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiErrorScreen;
import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.CustomModLoadingErrorDisplayException;
import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ErrorScreen {
	@SideOnly(Side.CLIENT)
	private static class ErrorScreenException extends CustomModLoadingErrorDisplayException {
		private static final long serialVersionUID = 1L;
		
		public String[] message;
		
	    public ErrorScreenException(String[] message) {
	    	this.message = message;
	    }
	    
	    @Override
		public void initGui(GuiErrorScreen errorScreen, FontRenderer fontRenderer) {
		}

		@Override
		public void drawScreen(GuiErrorScreen errorScreen, FontRenderer fontRenderer, int mouseRelX, int mouseRelY, float tickTime) {
			// drawGradientRect(0, 0, errorScreen.width, errorScreen.height, 0xFF402020, 0xFF501010);
			drawGradientRect(0, 0, errorScreen.width, errorScreen.height, 0xFF402020, 0xFFFF1010);
			
			int spacing = fontRenderer.FONT_HEIGHT + fontRenderer.FONT_HEIGHT/2;
			
			int y = errorScreen.height/2 - message.length*spacing/2;
			for(String line : message) {
				errorScreen.drawCenteredString(fontRenderer, line, errorScreen.width/2, y, 0xFFFFFF);
				y += spacing;
			}
		}
		
		protected void drawGradientRect(int par1, int par2, int par3, int par4, int par5, int par6)
	    {
			double zLevel = 0;
			
	        float f = (float)(par5 >> 24 & 255) / 255.0F;
	        float f1 = (float)(par5 >> 16 & 255) / 255.0F;
	        float f2 = (float)(par5 >> 8 & 255) / 255.0F;
	        float f3 = (float)(par5 & 255) / 255.0F;
	        float f4 = (float)(par6 >> 24 & 255) / 255.0F;
	        float f5 = (float)(par6 >> 16 & 255) / 255.0F;
	        float f6 = (float)(par6 >> 8 & 255) / 255.0F;
	        float f7 = (float)(par6 & 255) / 255.0F;
	        GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glDisable(GL11.GL_ALPHA_TEST);
	        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	        GL11.glShadeModel(GL11.GL_SMOOTH);
	        Tessellator tessellator = Tessellator.instance;
	        tessellator.startDrawingQuads();
	        tessellator.setColorRGBA_F(f1, f2, f3, f);
	        tessellator.addVertex((double)par3, (double)par2, (double)zLevel);
	        tessellator.addVertex((double)par1, (double)par2, (double)zLevel);
	        tessellator.setColorRGBA_F(f5, f6, f7, f4);
	        tessellator.addVertex((double)par1, (double)par4, (double)zLevel);
	        tessellator.addVertex((double)par3, (double)par4, (double)zLevel);
	        tessellator.draw();
	        GL11.glShadeModel(GL11.GL_FLAT);
	        GL11.glDisable(GL11.GL_BLEND);
	        GL11.glEnable(GL11.GL_ALPHA_TEST);
	        GL11.glEnable(GL11.GL_TEXTURE_2D);
	    }
	}
	
	@SideOnly(Side.CLIENT)
	private static void displayFatalErrorClient(final String... msg) {
		throw new ErrorScreenException(msg);
	}
	
	private static void sleepForever() {
		while(true) {
			try {
				Thread.sleep(10000);
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
				return;
			}
		}
	}
	
	public static void displayFatalError(final String... msg) {
		if(FMLLaunchHandler.side() == Side.CLIENT)
			displayFatalErrorClient(msg);
		
		else {
			// only logging is shown in the dedicated server GUI
			Logger l = Logger.getLogger("Minecraft-Server");
			l.log(Level.SEVERE, "");
			for(String line : msg)
				l.log(Level.SEVERE, line);
			
			System.err.println();
			for(String line : msg)
				System.err.println(line);
			
			sleepForever();
		}
	}
}
