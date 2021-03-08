package andrews.online_detector.objects.blocks;

import andrews.online_detector.screens.menus.AdvancedOnlineDetectorScreen;
import andrews.online_detector.tile_entities.AdvancedOnlineDetectorTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class AdvancedOnlineDetectorBlock extends OnlineDetectorBlock
{	
	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new AdvancedOnlineDetectorTileEntity();
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		if(worldIn.getTileEntity(pos) instanceof AdvancedOnlineDetectorTileEntity)
		{
			AdvancedOnlineDetectorTileEntity advancedOnlineDetectorTileEntity = (AdvancedOnlineDetectorTileEntity) worldIn.getTileEntity(pos);
			if(worldIn.isRemote)
				AdvancedOnlineDetectorScreen.open(advancedOnlineDetectorTileEntity);
		}
		return ActionResultType.SUCCESS;
	}
}
