package andrews.online_detector.screens.buttons;

import andrews.online_detector.screens.menus.AdvancedOnlineDetectorScreen;
import andrews.online_detector.util.Reference;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class NextPageButton extends Button
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/advanced_online_detector_menu.png");
	private static final int buttonWidth = 14;
	private static final int buttonHeight = 14;
	private int u = 0;
	private int v = 169;
	private static AdvancedOnlineDetectorScreen screen;
	
	public NextPageButton(int xPos, int yPos, AdvancedOnlineDetectorScreen onlineDetectorScreen)
	{
		super(xPos, yPos, buttonWidth, buttonHeight, new StringTextComponent(""), (button) -> { handleButtonPress(); });
		screen = onlineDetectorScreen;
	}
	
	@Override
	public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{	
		this.isHovered = false;
		if(mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height || this.isFocused())
			this.isHovered = true;
		
		this.u = 0;
		if(this.isHovered)
			this.u = 14;
		
		this.active = true;
		if(!(screen.getCurrentPage() < screen.getTotalPages()))
		{
			this.active = false;
			this.u = 28;
		}
		
		//Renders the Button
		Minecraft.getInstance().getRenderManager().textureManager.bindTexture(TEXTURE);
		matrixStack.push();
		RenderSystem.enableBlend();
		GuiUtils.drawTexturedModalRect(matrixStack, x, y, u, v, width, height, 0);
		RenderSystem.disableBlend();
		matrixStack.pop();
	}
	
	/**
	 * Gets called when the Button gets pressed
	 */
	private static void handleButtonPress()
	{
		screen.increasePage();
	}
}