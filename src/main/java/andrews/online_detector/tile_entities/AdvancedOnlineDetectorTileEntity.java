package andrews.online_detector.tile_entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import andrews.online_detector.objects.blocks.AdvancedOnlineDetectorBlock;
import andrews.online_detector.registry.ODTileEntities;
import net.minecraft.entity.player.PlayerEntity;

public class AdvancedOnlineDetectorTileEntity extends OnlineDetectorTileEntity
{
	public AdvancedOnlineDetectorTileEntity()
	{
		super(ODTileEntities.ADVANCED_ONLINE_DETECTOR.get());
	}
	
	@Override
	public void tick()
	{
		// Reduces how often the TileEntity attempts to run the code
		if(this.world.getGameTime() % Long.valueOf(this.updateFrequency) == 0L)
		{	
			// We make sure everything happens server side.
			if(!this.getWorld().isRemote)
			{
				List<UUID> playerIDs = new ArrayList<>();
				for(PlayerEntity player : this.getWorld().getServer().getPlayerList().getPlayers())
				{
					playerIDs.add(player.getUniqueID());
				}
				
				if(playerIDs.contains(this.ownerID))
				{
					if(this.getWorld().getBlockState(this.getPos()).get(AdvancedOnlineDetectorBlock.IS_ACTIVE) == false)
					{
						world.setBlockState(this.getPos(), world.getBlockState(this.getPos()).with(AdvancedOnlineDetectorBlock.IS_ACTIVE, true));
					}
				}
				else
				{
					if(this.getWorld().getBlockState(this.getPos()).get(AdvancedOnlineDetectorBlock.IS_ACTIVE) == true)
					{
						world.setBlockState(this.getPos(), world.getBlockState(this.getPos()).with(AdvancedOnlineDetectorBlock.IS_ACTIVE, false));
					}
				}
			}
		}
	}
}
