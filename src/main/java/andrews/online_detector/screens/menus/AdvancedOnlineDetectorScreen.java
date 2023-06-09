package andrews.online_detector.screens.menus;

import andrews.online_detector.block_entities.AdvancedOnlineDetectorBlockEntity;
import andrews.online_detector.screens.buttons.AvailablePlayerButton;
import andrews.online_detector.screens.buttons.NextPageButton;
import andrews.online_detector.screens.buttons.PreviousPageButton;
import andrews.online_detector.util.Reference;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

public class AdvancedOnlineDetectorScreen extends Screen
{
	private static final ResourceLocation MENU_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/advanced_online_detector_menu.png");
	private final String advancedOnlineDetectorText = Component.translatable("gui.online_detector.advanced_online_detector").getString();
	private final String trackingText = Component.translatable("gui.online_detector.tracking").getString();
	private final String availableText = Component.translatable("gui.online_detector.available").getString();
	private final AdvancedOnlineDetectorBlockEntity advancedOnlineDetectorBlockEntity;
	private final int xSize = 177;
	private final int ySize = 131;
	private int totalPages;
	private int currentPage;
	
	public AdvancedOnlineDetectorScreen(AdvancedOnlineDetectorBlockEntity advancedOnlineDetectorBlockEntity)
	{
		super(Component.literal(""));
		this.advancedOnlineDetectorBlockEntity = advancedOnlineDetectorBlockEntity;
		currentPage = 1;
	}
	
	@Override
	public boolean isPauseScreen()
	{
		return false;
	}
	
	@Override
	protected void init()
	{
		super.init();
		// Values to calculate the relative position
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		// The Buttons in the Gui Menu
		ArrayList<PlayerInfo> playerList = new ArrayList<PlayerInfo>(Minecraft.getInstance().getConnection().getOnlinePlayers());
		totalPages = (int) Math.ceil(playerList.size() / 5.0D);
		int buttonIndex = 0;
		for(int j = 0; j < playerList.size(); j++)
		{
			PlayerInfo playerInfo = playerList.get(buttonIndex);
			this.addRenderableWidget(new AvailablePlayerButton(advancedOnlineDetectorBlockEntity, playerInfo, x + 6, y + 51 + (j * 12) - ((int)Math.floor(j / 5) * 60), this, buttonIndex));
			buttonIndex++;
		}
		// Page control Buttons
		this.addRenderableWidget(new PreviousPageButton(x + 5, y + 113, this));
		this.addRenderableWidget(new NextPageButton(x + 158, y + 113, this));
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
	{
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		graphics.setColor(1F, 1F, 1F, 1F);
		graphics.blit(MENU_TEXTURE, x, y, 0, 0, this.xSize, this.ySize);

		graphics.drawString(this.font, this.advancedOnlineDetectorText, ((this.width / 2) - (this.font.width(this.advancedOnlineDetectorText) / 2)), y + 6, 4210752, false);
		graphics.drawString(this.font, this.trackingText, x + 5, y + 18, 0x000000, false);
		// The name of the Player that is being tracked
		if(this.advancedOnlineDetectorBlockEntity.getOwnerName() != null)
			graphics.drawString(this.font, this.advancedOnlineDetectorBlockEntity.getOwnerName(), ((this.width / 2) - (this.font.width(this.advancedOnlineDetectorBlockEntity.getOwnerName()) / 2)), y + 29, 0xffffff, false);

		graphics.drawString(this.font, this.availableText, x + 5, y + 41, 0x000000, false);
		graphics.drawString(this.font, currentPage + "/" + totalPages, ((this.width / 2) - (this.font.width(currentPage + "/" + totalPages) / 2)), y + 116, 0x000000, false);
		// Renders the Buttons we added in init
		super.render(graphics, mouseX, mouseY, partialTick);
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers)
	{
		super.keyPressed(keyCode, scanCode, modifiers);
		InputConstants.Key mouseKey = InputConstants.getKey(keyCode, scanCode);
		if(this.minecraft.options.keyInventory.matches(keyCode, scanCode))
			this.onClose();
		return true;
	}
	
	/**
	 * Used to open this Gui, because class loading is a little child that screams if it does not like you
	 * @param advancedOnlineDetectorTileEntity - The Advanced Online Detector Tile Entity
	 */
	public static void open(AdvancedOnlineDetectorBlockEntity advancedOnlineDetectorTileEntity)
	{
		Minecraft.getInstance().setScreen(new AdvancedOnlineDetectorScreen(advancedOnlineDetectorTileEntity));
	}
	
	public int getTotalPages()
	{
		return this.totalPages;
	}
	
	public int getCurrentPage()
	{
		return this.currentPage;
	}
	
	public void increasePage()
	{
		this.currentPage++;
	}
	
	public void decreasePage()
	{
		if(this.currentPage > 1)
			this.currentPage--;
	}
}