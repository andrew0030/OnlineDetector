package andrews.online_detector.block_entities;

import andrews.online_detector.objects.blocks.AdvancedOnlineDetectorBlock;
import andrews.online_detector.registry.ODBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdvancedOnlineDetectorBlockEntity extends OnlineDetectorBlockEntity
{
	public AdvancedOnlineDetectorBlockEntity(BlockPos pos, BlockState state)
	{
		super(ODBlockEntities.ADVANCED_ONLINE_DETECTOR, pos, state);
	}

	public static void tick(Level level, BlockPos pos, BlockState state, AdvancedOnlineDetectorBlockEntity blockEntity)
	{
		// Reduces how often the TileEntity attempts to run the code
		if(level.getGameTime() % (long) updateFrequency == 0L)
		{	
			// We make sure everything happens server side.
			if(!level.isClientSide)
			{
				List<UUID> playerIDs = new ArrayList<>();
				for(Player player : level.getServer().getPlayerList().getPlayers())
				{
					playerIDs.add(player.getUUID());
				}
				
				if(playerIDs.contains(blockEntity.getOwnerUUID()))
				{
					if(!level.getBlockState(pos).getValue(AdvancedOnlineDetectorBlock.IS_ACTIVE))
					{
						level.setBlockAndUpdate(pos, level.getBlockState(pos).setValue(AdvancedOnlineDetectorBlock.IS_ACTIVE, true));
					}
				}
				else
				{
					if(level.getBlockState(pos).getValue(AdvancedOnlineDetectorBlock.IS_ACTIVE))
					{
						level.setBlockAndUpdate(pos, level.getBlockState(pos).setValue(AdvancedOnlineDetectorBlock.IS_ACTIVE, false));
					}
				}
			}
		}
	}
}
