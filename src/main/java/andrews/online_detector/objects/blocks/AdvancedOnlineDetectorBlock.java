package andrews.online_detector.objects.blocks;

import andrews.online_detector.screens.menus.AdvancedOnlineDetectorScreen;
import andrews.online_detector.block_entities.AdvancedOnlineDetectorBlockEntity;
import andrews.online_detector.block_entities.OnlineDetectorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class AdvancedOnlineDetectorBlock extends OnlineDetectorBlock
{
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
	{
		return new AdvancedOnlineDetectorBlockEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType)
	{
		return (level1, pos, state1, blockEntity) -> AdvancedOnlineDetectorBlockEntity.tick(level1, pos, state1, (OnlineDetectorBlockEntity) blockEntity);
	}

	// We Overide this here to avoid setting the block placer as the tracking target, for the advanced online detector.
	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
	{
		super.use(state, level, pos, player, hand, hit);
		if(!player.isShiftKeyDown() && level.getBlockEntity(pos) instanceof AdvancedOnlineDetectorBlockEntity advancedOnlineDetectorBlockEntity)
		{
			if(level.isClientSide)
				AdvancedOnlineDetectorScreen.open(advancedOnlineDetectorBlockEntity);
		}
		return InteractionResult.SUCCESS;
	}
}