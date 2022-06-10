package andrews.online_detector.screens.buttons;

import andrews.online_detector.block_entities.AdvancedOnlineDetectorBlockEntity;
import andrews.online_detector.screens.menus.AdvancedOnlineDetectorScreen;
import andrews.online_detector.util.Reference;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;


public class NextPageButton extends Button
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/advanced_online_detector_menu.png");
	private static final int buttonWidth = 14;
	private static final int buttonHeight = 14;
	private int u = 0;
	private int v = 169;
	private static AdvancedOnlineDetectorScreen screen;
	
	public NextPageButton(AdvancedOnlineDetectorBlockEntity advancedOnlineDetectorTileEntity1, int xPos, int yPos, AdvancedOnlineDetectorScreen onlineDetectorScreen)
	{
		super(xPos, yPos, buttonWidth, buttonHeight, Component.literal(""), (button) -> { handleButtonPress(); });
		screen = onlineDetectorScreen;
	}

	@Override
	public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float pPartialTick)
	{
		this.isHovered = mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height || this.isFocused();

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
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture(0, TEXTURE);
		poseStack.pushPose();
		RenderSystem.enableBlend();
		this.blit(poseStack, x, y, u, v, width, height);
		RenderSystem.disableBlend();
		poseStack.popPose();
	}
	
	/**
	 * Gets called when the Button gets pressed
	 */
	private static void handleButtonPress()
	{
		screen.increasePage();
	}
}