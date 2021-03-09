package andrews.online_detector.screens.buttons;

import java.util.UUID;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import andrews.online_detector.screens.menus.AdvancedOnlineDetectorScreen;
import andrews.online_detector.tile_entities.AdvancedOnlineDetectorTileEntity;
import andrews.online_detector.util.NetworkUtil;
import andrews.online_detector.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class AvailablePlayerButton extends Button
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/advanced_online_detector_menu.png");
	private static final int buttonWidth = 165;
	private static final int buttonHeight = 12;
	private final FontRenderer fontRenderer;
	private int u = 0;
	private int v = 131;
	private NetworkPlayerInfo playerInfo;
	private AdvancedOnlineDetectorTileEntity advancedOnlineDetectorTileEntity;
	private AdvancedOnlineDetectorScreen screen;
	private int buttonIndex;
	
	public AvailablePlayerButton(AdvancedOnlineDetectorTileEntity advancedOnlineDetectorTileEntity, NetworkPlayerInfo playerInfo, int xPos, int yPos, AdvancedOnlineDetectorScreen screen, int buttonIndex) 
	{
		super(xPos, yPos, buttonWidth, buttonHeight, new StringTextComponent(""), null);
		this.fontRenderer = Minecraft.getInstance().fontRenderer;
		this.advancedOnlineDetectorTileEntity = advancedOnlineDetectorTileEntity;
		this.playerInfo = playerInfo;
		this.screen = screen;
		this.buttonIndex = buttonIndex;
	}
	
	@Override
	public void onPress()
	{
		if(Minecraft.getInstance().world.getPlayerByUuid(playerInfo.getGameProfile().getId()) != null)
		{
			UUID uuid = playerInfo.getGameProfile().getId();
			String name = Minecraft.getInstance().world.getPlayerByUuid(playerInfo.getGameProfile().getId()).getName().getString();
			NetworkUtil.newSelectPlayerMessage(advancedOnlineDetectorTileEntity.getPos(), uuid, name);
			NetworkUtil.setPlayerHeadMessage(advancedOnlineDetectorTileEntity.getPos(), new ItemStack(Items.AIR));
		}
		Minecraft.getInstance().player.closeScreen();
	}
	
	@Override
	public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		this.isHovered = false;
		if(mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height || this.isFocused())
			this.isHovered = true;
		
		this.v = 131;
		if(this.isHovered)
			this.v = 143;
		
		this.active = true;
		if(buttonIndex < (screen.getCurrentPage() * 5) - 5 || buttonIndex >= screen.getCurrentPage() * 5)
		{
			this.active = false;
			this.v = 244;
		}
		
		//Renders the Button
		Minecraft.getInstance().getRenderManager().textureManager.bindTexture(TEXTURE);
		matrixStack.push();
		RenderSystem.enableBlend();
		GuiUtils.drawTexturedModalRect(matrixStack, x, y, u, v, width, height, 0);
		RenderSystem.disableBlend();
		matrixStack.pop();
		if(!(buttonIndex < (screen.getCurrentPage() * 5) - 5 || buttonIndex >= screen.getCurrentPage() * 5))
		{
			this.fontRenderer.drawString(matrixStack, Minecraft.getInstance().world.getPlayerByUuid(playerInfo.getGameProfile().getId()).getName().getString(), x + 12, y + 2, 0x000000);
			Minecraft.getInstance().getTextureManager().bindTexture(playerInfo.getLocationSkin());
			AbstractGui.blit(matrixStack, x + 2, y + 2, 8, 8, 8.0F, 8, 8, 8, 64, 64);
		}
	}
}