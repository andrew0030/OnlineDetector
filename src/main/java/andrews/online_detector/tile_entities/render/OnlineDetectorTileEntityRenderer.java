package andrews.online_detector.tile_entities.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import andrews.online_detector.objects.blocks.OnlineDetectorBlock;
import andrews.online_detector.tile_entities.OnlineDetectorTileEntity;
import andrews.online_detector.tile_entities.model.EyeModel;
import andrews.online_detector.util.Reference;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

public class OnlineDetectorTileEntityRenderer extends TileEntityRenderer<OnlineDetectorTileEntity>
{
	public static final ResourceLocation EYE_TEXTURE = new ResourceLocation(Reference.MODID, "textures/block/eye.png");
	private static EyeModel eyeModel;
	
	public OnlineDetectorTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
	{
		super(rendererDispatcherIn);
		eyeModel = new EyeModel();
	}

	@Override
	public void render(OnlineDetectorTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
	{
		Direction facing = Direction.NORTH;
	    if(tileEntityIn.hasWorld())
	    {
	         BlockState blockstate = tileEntityIn.getWorld().getBlockState(tileEntityIn.getPos());
	         if(blockstate.getBlock() instanceof OnlineDetectorBlock)
	         {
	        	 facing = blockstate.get(OnlineDetectorBlock.HORIZONTAL_FACING);
	         }
	    }
		
		matrixStackIn.push();
		matrixStackIn.translate(0.5D, 0D, 0.5D);
		// Rotates the Head based on the Blocks facing direction
		switch(facing)
		{
		default:
		case NORTH:
			matrixStackIn.rotate(Vector3f.YN.rotationDegrees(90.0F));
			break;
		case SOUTH:
			matrixStackIn.rotate(Vector3f.YN.rotationDegrees(270.0F));
			break;
		case WEST:
			break;
		case EAST:
			matrixStackIn.rotate(Vector3f.YN.rotationDegrees(180.0F));
		}
		
		if(tileEntityIn.hasWorld())
		{
	         BlockState blockstate = tileEntityIn.getWorld().getBlockState(tileEntityIn.getPos());
	         if(blockstate.getBlock() instanceof OnlineDetectorBlock)
	         {
	        	 renderEye(tileEntityIn.getWorld(), blockstate, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
	         }
		}
		else
		{
			renderEye(tileEntityIn.getWorld(), partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
		}
		
		// Moves the player head to the center of the Blocks side
		matrixStackIn.translate(0D, 0.0625 * 8, 0.0625 * -4);
		renderPlayerFace(tileEntityIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
		matrixStackIn.pop();
	}
	
	/**
	 * Renders the Ender Eye on top of the Online Detector Block
	 */
	private static void renderEye(World world, BlockState stateIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
	{
		matrixStackIn.push();
		matrixStackIn.scale(1.0F, -1.0F, -1.0F);
		
		if(stateIn.get(OnlineDetectorBlock.IS_ACTIVE))
		{
			matrixStackIn.rotate(Vector3f.XP.rotationDegrees((float) Math.cos((Minecraft.getInstance().player.ticksExisted + partialTicks) / 4) * 2));
			matrixStackIn.rotate(Vector3f.ZP.rotationDegrees((float) Math.sin((Minecraft.getInstance().player.ticksExisted + partialTicks) / 4) * 2));
		}
		
		matrixStackIn.translate(0.0D, -1.0D, 0.0D);
		matrixStackIn.translate(0D, 0.0625 * -21, 0D);
		IVertexBuilder builder = bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(EYE_TEXTURE));
		eyeModel.eye.render(matrixStackIn, builder, combinedLightIn, combinedOverlayIn);
		matrixStackIn.pop();
	}
	
	/**
	 * Renders the Ender Eye on top of the Online Detector Block
	 */
	private static void renderEye(World world, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
	{
		matrixStackIn.push();
		matrixStackIn.scale(1.0F, -1.0F, -1.0F);
		matrixStackIn.translate(0.0D, -1.0D, 0.0D);
		matrixStackIn.translate(0D, 0.0625 * -21, 0D);
		IVertexBuilder builder = bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(EYE_TEXTURE));
		eyeModel.eye.render(matrixStackIn, builder, combinedLightIn, combinedOverlayIn);
		matrixStackIn.pop();
	}
	
	/**
	 * Renders the Player face
	 */
	private static void renderPlayerFace(OnlineDetectorTileEntity tileEntityIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
	{
		if(tileEntityIn.getOwnerUUID() != null && tileEntityIn.getOwnerName() != null)
		{
			if(tileEntityIn.getOwnerHead() == null)
				tileEntityIn.setOwnerHead(getCustomHead(tileEntityIn.getOwnerName()));
		}
		// We make sure the stack has been initialized before attempting to render it
		if(tileEntityIn.getOwnerHead() != null)
			Minecraft.getInstance().getItemRenderer().renderItem(tileEntityIn.getOwnerHead(), TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
	}
	
	private static ItemStack getCustomHead(String playerName)
	{	    	
    	ItemStack customHead = new ItemStack(Items.PLAYER_HEAD);
    	customHead.setTag(new CompoundNBT());
    	customHead.getTag().putString("SkullOwner", playerName);     	 
    	return customHead;    	    
    }
}
