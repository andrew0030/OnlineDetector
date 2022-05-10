package andrews.online_detector.block_entities;

import andrews.online_detector.OnlineDetector;
import andrews.online_detector.config.ODConfig;
import andrews.online_detector.objects.blocks.OnlineDetectorBlock;
import andrews.online_detector.registry.ODBlockEntities;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OnlineDetectorBlockEntity extends BlockEntity
{
	protected UUID ownerID;
	protected String ownerName;
	protected static int updateFrequency;
	protected ItemStack ownerHead = new ItemStack(Items.AIR);
	
	public OnlineDetectorBlockEntity(BlockPos pos, BlockState state)
	{
		super(ODBlockEntities.ONLINE_DETECTOR, pos, state);
		updateFrequency = OnlineDetector.OD_CONFIG.ODCommonConfig.onlineCheckFrequency;
	}
	
	public OnlineDetectorBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state)
	{
		super(blockEntityType, pos, state);
		updateFrequency = OnlineDetector.OD_CONFIG.ODCommonConfig.onlineCheckFrequency;
	}

	public static void tick(Level level, BlockPos pos, BlockState state, OnlineDetectorBlockEntity blockEntity)
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
					if(!level.getBlockState(pos).getValue(OnlineDetectorBlock.IS_ACTIVE))
					{
						level.setBlockAndUpdate(pos, level.getBlockState(pos).setValue(OnlineDetectorBlock.IS_ACTIVE, true));
					}
				}
				else
				{
					if(level.getBlockState(pos).getValue(OnlineDetectorBlock.IS_ACTIVE))
					{
						level.setBlockAndUpdate(pos, level.getBlockState(pos).setValue(OnlineDetectorBlock.IS_ACTIVE, false));
					}
				}
			}
		}
	}

	// Used to synchronize the TileEntity with the client when the chunk it is in is loaded
	@Override
	public CompoundTag getUpdateTag()
	{
		CompoundTag compound = new CompoundTag();
		this.saveToNBT(compound);
		return compound;
	}

	// Used to synchronize the TileEntity with the client when the chunk it is in is loaded
	@Nullable
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket()
	{
		// Will get tag from #getUpdateTag
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	protected void saveAdditional(CompoundTag compound)
	{
		super.saveAdditional(compound);
		this.saveToNBT(compound);
	}

	@Override
	public void load(CompoundTag compound)
	{
		super.load(compound);
		this.loadFromNBT(compound);
	}

	/**
	 * Used to save data to the NBT
	 */
	private void saveToNBT(CompoundTag compound)
	{
		CompoundTag onlineDetectorNBT = new CompoundTag();
		if(this.ownerID != null)
			onlineDetectorNBT.putUUID("OwnerID", this.ownerID);
		if(this.ownerName != null)
			onlineDetectorNBT.putString("OwnerName", this.ownerName);
		if(this.ownerHead != null)
			onlineDetectorNBT.put("OwnerHead", this.ownerHead.save(new CompoundTag()));
		compound.put("OnlineDetectorValues", onlineDetectorNBT);
	}
	
	/**
	 * Used to load data from the NBT
	 */
	private void loadFromNBT(CompoundTag compound)
	{
		CompoundTag onlineDetectorNBT = compound.getCompound("OnlineDetectorValues");
		if(onlineDetectorNBT.contains("OwnerID"))
			this.ownerID = onlineDetectorNBT.getUUID("OwnerID");
		if(onlineDetectorNBT.contains("OwnerName", Tag.TAG_STRING))
			this.ownerName = onlineDetectorNBT.getString("OwnerName");
		if(onlineDetectorNBT.contains("OwnerHead"))
			this.ownerHead = ItemStack.of(onlineDetectorNBT.getCompound("OwnerHead"));
	}
	
	/**
	 * @return - The UUID of the Player this OnlineDetector was assigned to.
	 */
	public UUID getOwnerUUID()
	{
		return this.ownerID;
	}
	
	/**
	 * Sets the owner of this OnlineDetector
	 * @param uuid - The UUID of the player that will be assigned as the owner of this OnlineDetector
	 */
	public void setOwnerUUID(UUID uuid)
	{
		this.ownerID = uuid;
	}
	
	/**
	 * @return - The Name of the Player this OnlineDetector was assigned to.
	 */
	public String getOwnerName()
	{
		return this.ownerName;
	}
	
	/**
	 * Sets the owner name of this OnlineDetector
	 * @param name - The name of the player that will be assigned as the owner of this OnlineDetector
	 */
	public void setOwnerName(String name)
	{
		this.ownerName = name;
	}
	
	public ItemStack getOwnerHead()
	{
		return this.ownerHead;
	}
	
	public void setOwnerHead(ItemStack stack)
	{
		this.ownerHead = stack;
	}
}