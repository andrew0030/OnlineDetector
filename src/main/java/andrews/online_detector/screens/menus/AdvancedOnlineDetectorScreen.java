package andrews.online_detector.screens.menus;

import andrews.online_detector.screens.buttons.AvailablePlayerButton;
import andrews.online_detector.screens.buttons.NextPageButton;
import andrews.online_detector.screens.buttons.PreviousPageButton;
import andrews.online_detector.tile_entities.AdvancedOnlineDetectorTileEntity;
import andrews.online_detector.util.Reference;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;

public class AdvancedOnlineDetectorScreen extends Screen
{
	private static final ResourceLocation MENU_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/advanced_online_detector_menu.png");
	private final String advancedOnlineDetectorText = new TranslationTextComponent("gui.online_detector.advanced_online_detector").getString();
	private final String trackingText = new TranslationTextComponent("gui.online_detector.tracking").getString();
	private final String availableText = new TranslationTextComponent("gui.online_detector.available").getString();
	private AdvancedOnlineDetectorTileEntity advancedOnlineDetectorTileEntity;
	private final int xSize = 177;
	private final int ySize = 131;
	private int totalPages;
	private int currentPage;
	
	public AdvancedOnlineDetectorScreen(AdvancedOnlineDetectorTileEntity advancedOnlineDetectorTileEntity)
	{
		super(new StringTextComponent(""));
		this.advancedOnlineDetectorTileEntity = advancedOnlineDetectorTileEntity;
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
		ArrayList<NetworkPlayerInfo> playerList = new ArrayList<NetworkPlayerInfo>(Minecraft.getInstance().getConnection().getPlayerInfoMap());
		totalPages = (int) Math.ceil(playerList.size() / 5.0D);
		int buttonIndex = 0;
		for(int j = 0; j < playerList.size(); j++)
		{	
			NetworkPlayerInfo playerInfo = playerList.get(buttonIndex);
			this.addButton(new AvailablePlayerButton(advancedOnlineDetectorTileEntity, playerInfo, x + 6, y + 51 + (j * 12) - ((int)Math.floor(j / 5) * 60), this, buttonIndex));
			buttonIndex++;
		}
		// Page control Buttons
		this.addButton(new PreviousPageButton(x + 5, y + 113, this));
		this.addButton(new NextPageButton(x + 158, y + 113, this));
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		
		this.minecraft.getTextureManager().bindTexture(MENU_TEXTURE);
		this.blit(matrixStack, x, y, 0, 0, this.xSize, this.ySize);
//		this.blit(matrixStack, x, y + 15, 0, 198, 3, 26);
		
		this.font.drawString(matrixStack, this.advancedOnlineDetectorText, ((this.width / 2) - (this.font.getStringWidth(this.advancedOnlineDetectorText) / 2)), y + 6, 4210752);
		this.font.drawString(matrixStack, this.trackingText, x + 5, y + 18, 0x000000);
		// The name of the Player that is being tracked
		if(this.advancedOnlineDetectorTileEntity.getOwnerName() != null)
			this.font.drawString(matrixStack, this.advancedOnlineDetectorTileEntity.getOwnerName(), ((this.width / 2) - (this.font.getStringWidth(this.advancedOnlineDetectorTileEntity.getOwnerName()) / 2)), y + 29, 0xffffff);
		
		this.font.drawString(matrixStack, this.availableText, x + 5, y + 41, 0x000000);
		this.font.drawString(matrixStack, currentPage + "/" + totalPages, ((this.width / 2) - (this.font.getStringWidth(currentPage + "/" + totalPages) / 2)), y + 116, 0x000000);
		// Renders the Buttons we added in init
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers)
	{
		super.keyPressed(keyCode, scanCode, modifiers);
		InputMappings.Input mouseKey = InputMappings.getInputByCode(keyCode, scanCode);
		if(this.minecraft.gameSettings.keyBindInventory.isActiveAndMatches(mouseKey))
			this.closeScreen();
		return true;
	}
	
	/**
	 * Used to open this Gui, because class loading is a little child that screams if it does not like you
	 * @param advancedOnlineDetectorTileEntity - The Advanced Online Detector Tile Entity
	 */
	public static void open(AdvancedOnlineDetectorTileEntity advancedOnlineDetectorTileEntity)
	{
		Minecraft.getInstance().displayGuiScreen(new AdvancedOnlineDetectorScreen(advancedOnlineDetectorTileEntity));
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