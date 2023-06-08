package andrews.online_detector.screens.buttons;

import andrews.online_detector.screens.menus.AdvancedOnlineDetectorScreen;
import andrews.online_detector.util.Reference;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class PreviousPageButton extends Button
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/advanced_online_detector_menu.png");
	private static final int buttonWidth = 14;
	private static final int buttonHeight = 14;
	private int u = 0;
	private int v = 155;
	private static AdvancedOnlineDetectorScreen screen;
	
	public PreviousPageButton(int xPos, int yPos, AdvancedOnlineDetectorScreen onlineDetectorScreen)
	{
		super(xPos, yPos, buttonWidth, buttonHeight, Component.literal(""), (button) -> { handleButtonPress(); }, DEFAULT_NARRATION);
		screen = onlineDetectorScreen;
	}

	@Override
	public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
	{
		this.isHovered = mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height || this.isFocused();

		this.u = 0;
		if(this.isHovered)
			this.u = 14;

		this.active = true;
		if(!(screen.getCurrentPage() > 1))
		{
			this.active = false;
			this.u = 28;
		}

		//Renders the Button
		graphics.blit(TEXTURE, x, y, u, v, width, height);
	}

	/**
	 * Gets called when the Button gets pressed
	 */
	private static void handleButtonPress()
	{
		screen.decreasePage();
	}
}