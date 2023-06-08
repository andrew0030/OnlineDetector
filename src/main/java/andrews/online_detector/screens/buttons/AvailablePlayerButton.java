package andrews.online_detector.screens.buttons;

import andrews.online_detector.block_entities.AdvancedOnlineDetectorBlockEntity;
import andrews.online_detector.screens.menus.AdvancedOnlineDetectorScreen;
import andrews.online_detector.util.NetworkUtil;
import andrews.online_detector.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.UUID;

public class AvailablePlayerButton extends Button
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/advanced_online_detector_menu.png");
	private static final int buttonWidth = 165;
	private static final int buttonHeight = 12;
	private final Font fontRenderer;
	private int u = 0;
	private int v = 131;
	private final PlayerInfo playerInfo;
	private final AdvancedOnlineDetectorBlockEntity advancedOnlineDetectorBlockEntity;
	private final AdvancedOnlineDetectorScreen screen;
	private final int buttonIndex;
	
	public AvailablePlayerButton(AdvancedOnlineDetectorBlockEntity advancedOnlineDetectorBlockEntity, PlayerInfo playerInfo, int xPos, int yPos, AdvancedOnlineDetectorScreen screen, int buttonIndex)
	{
		super(xPos, yPos, buttonWidth, buttonHeight, Component.literal(""), (button) -> {}, DEFAULT_NARRATION);
		this.fontRenderer = Minecraft.getInstance().font;
		this.advancedOnlineDetectorBlockEntity = advancedOnlineDetectorBlockEntity;
		this.playerInfo = playerInfo;
		this.screen = screen;
		this.buttonIndex = buttonIndex;
	}
	
	@Override
	public void onPress()
	{
		if(playerInfo.getProfile().getId() != null)
		{
			UUID uuid = playerInfo.getProfile().getId();
			String name = playerInfo.getProfile().getName();
			NetworkUtil.newSelectPlayerMessage(advancedOnlineDetectorBlockEntity.getBlockPos(), uuid, name);
			NetworkUtil.setPlayerHeadMessage(advancedOnlineDetectorBlockEntity.getBlockPos(), new ItemStack(Items.AIR));
		}
		Minecraft.getInstance().player.clientSideCloseContainer();
	}

	@Override
	public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
	{
		this.isHovered = mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height || this.isFocused();

		this.v = 131;
		if(this.isHovered)
			this.v = 143;

		this.active = true;
		if(buttonIndex < (screen.getCurrentPage() * 5) - 5 || buttonIndex >= screen.getCurrentPage() * 5)
		{
			this.active = false;
			this.v = 244;
		}

		graphics.setColor(1F, 1F, 1F, 1F);
		graphics.blit(TEXTURE, x, y, u, v, width, height);
		if(!(buttonIndex < (screen.getCurrentPage() * 5) - 5 || buttonIndex >= screen.getCurrentPage() * 5))
		{
			graphics.drawString(this.fontRenderer, playerInfo.getProfile().getName(), x + 12, y + 2, 0x000000, false);
			graphics.pose().pushPose();
			graphics.pose().translate(x + 1, y + 1, 0);
			graphics.pose().scale(1.25F, 1.25F, 1.0F);
			graphics.setColor(1F, 1F, 1F, 1F);
			graphics.blit(playerInfo.getSkinLocation(), 0, 0, 8, 8, 8.0F, 8, 8, 8, 64, 64);
			graphics.pose().popPose();
		}
	}
}