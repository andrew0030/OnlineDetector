package andrews.online_detector.block_entities.render;

import andrews.online_detector.block_entities.OnlineDetectorBlockEntity;
import andrews.online_detector.block_entities.model.EyeModel;
import andrews.online_detector.objects.blocks.OnlineDetectorBlock;
import andrews.online_detector.util.Reference;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;


public class OnlineDetectorBlockEntityRenderer implements BlockEntityRenderer<OnlineDetectorBlockEntity>
{
	public static final ResourceLocation EYE_TEXTURE = new ResourceLocation(Reference.MODID, "textures/block/eye.png");
	private static EyeModel eyeModel;
	
	public OnlineDetectorBlockEntityRenderer(BlockEntityRendererProvider.Context context)
	{
		eyeModel = new EyeModel(context.bakeLayer(EyeModel.EYE_LAYER_LOCATION));
	}

	@Override
	public void render(OnlineDetectorBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay)
	{
		Direction facing = Direction.NORTH;
		if(blockEntity.hasLevel())
		{
			BlockState blockstate = blockEntity.getLevel().getBlockState(blockEntity.getBlockPos());
			if(blockstate.getBlock() instanceof OnlineDetectorBlock)
			{
				facing = blockstate.getValue(OnlineDetectorBlock.HORIZONTAL_FACING);
			}
		}

		poseStack.pushPose();
		poseStack.translate(0.5D, 0D, 0.5D);
		// Rotates the Head based on the Blocks facing direction
		switch(facing)
		{
			default:
			case NORTH:
				poseStack.mulPose(Axis.YN.rotationDegrees(90.0F));
				break;
			case SOUTH:
				poseStack.mulPose(Axis.YN.rotationDegrees(270.0F));
				break;
			case WEST:
				break;
			case EAST:
				poseStack.mulPose(Axis.YN.rotationDegrees(180.0F));
		}

		if(blockEntity.hasLevel())
		{
			BlockState blockstate = blockEntity.getLevel().getBlockState(blockEntity.getBlockPos());
			if(blockstate.getBlock() instanceof OnlineDetectorBlock)
			{
				renderEye(blockEntity.getLevel(), blockstate, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
			}
		}
		else
		{
			renderEye(blockEntity.getLevel(), partialTick, poseStack, bufferSource, packedLight, packedOverlay);
		}

		// Moves the player head to the center of the Blocks side
		poseStack.translate(0D, 0.0625 * 8, 0.0625 * -4);
		renderPlayerFace(blockEntity, poseStack, bufferSource, packedLight, packedOverlay);
		poseStack.popPose();
	}
	
	/**
	 * Renders the Ender Eye on top of the Online Detector Block
	 */
	private static void renderEye(Level level, BlockState state, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay)
	{
		poseStack.pushPose();
		poseStack.scale(1.0F, -1.0F, -1.0F);
		
		if(state.getValue(OnlineDetectorBlock.IS_ACTIVE))
		{
			poseStack.mulPose(Axis.XP.rotationDegrees((float) Math.cos((Minecraft.getInstance().player.tickCount + partialTick) / 4) * 2));
			poseStack.mulPose(Axis.ZP.rotationDegrees((float) Math.sin((Minecraft.getInstance().player.tickCount + partialTick) / 4) * 2));
		}
		
		poseStack.translate(0.0D, -1.0D, 0.0D);
		poseStack.translate(0D, 0.0625 * -21, 0D);
		VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.entitySolid(EYE_TEXTURE));
		eyeModel.renderToBuffer(poseStack, vertexconsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
		poseStack.popPose();
	}
	
	/**
	 * Renders the Ender Eye on top of the Online Detector Block
	 */
	private static void renderEye(Level level, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay)
	{
		poseStack.pushPose();
		poseStack.scale(1.0F, -1.0F, -1.0F);
		poseStack.translate(0.0D, -1.0D, 0.0D);
		poseStack.translate(0D, 0.0625 * -21, 0D);
		VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.entitySolid(EYE_TEXTURE));
		eyeModel.renderToBuffer(poseStack, vertexconsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
		poseStack.popPose();
	}
	
	/**
	 * Renders the Player face
	 */
	private static void renderPlayerFace(OnlineDetectorBlockEntity blockEntity, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay)
	{
		if(blockEntity.getOwnerUUID() != null && blockEntity.getOwnerName() != null)
		{
			if(blockEntity.getOwnerHead().getItem() == Items.AIR)
				// This was replaced with a simplified version of the code because the normal Online Detector never changes the player head unless replaced
//				NetworkUtil.setPlayerHeadMessage(tileEntityIn.getPos(), getCustomHead(tileEntityIn.getOwnerName()));
				blockEntity.setOwnerHead(getCustomHead(blockEntity.getOwnerName()));
		}
		int i = (int)blockEntity.getBlockPos().asLong();
		// We make sure the stack has been initialized before attempting to render it
		if(blockEntity.getOwnerHead().getItem() != Items.AIR)
			Minecraft.getInstance().getItemRenderer().renderStatic(blockEntity.getOwnerHead(), ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), i);
	}
	
	private static ItemStack getCustomHead(String playerName)
	{	    	
    	ItemStack customHead = new ItemStack(Items.PLAYER_HEAD);
    	customHead.setTag(new CompoundTag());
    	customHead.getTag().putString("SkullOwner", playerName);     	 
    	return customHead;    	    
    }
}
