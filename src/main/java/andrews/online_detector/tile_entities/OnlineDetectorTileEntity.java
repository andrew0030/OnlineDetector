package andrews.online_detector.tile_entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import andrews.online_detector.config.ODConfigs;
import andrews.online_detector.objects.blocks.OnlineDetectorBlock;
import andrews.online_detector.registry.ODTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.util.Constants.NBT;

public class OnlineDetectorTileEntity extends TileEntity implements ITickableTileEntity
{
	protected UUID ownerID;
	protected String ownerName;
	protected int updateFrequency;
	protected ItemStack ownerHead = new ItemStack(Items.AIR);
	
	public OnlineDetectorTileEntity()
	{
		super(ODTileEntities.ONLINE_DETECTOR.get());
		this.updateFrequency = ODConfigs.ODCommonConfig.onlineCheckFrequency.get();
	}
	
	public OnlineDetectorTileEntity(TileEntityType<?> tileEntityTypeIn)
	{
		super(tileEntityTypeIn);
		this.updateFrequency = ODConfigs.ODCommonConfig.onlineCheckFrequency.get();
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
					if(this.getWorld().getBlockState(this.getPos()).get(OnlineDetectorBlock.IS_ACTIVE) == false)
					{
						world.setBlockState(this.getPos(), world.getBlockState(this.getPos()).with(OnlineDetectorBlock.IS_ACTIVE, true));
					}
				}
				else
				{
					if(this.getWorld().getBlockState(this.getPos()).get(OnlineDetectorBlock.IS_ACTIVE) == true)
					{
						world.setBlockState(this.getPos(), world.getBlockState(this.getPos()).with(OnlineDetectorBlock.IS_ACTIVE, false));
					}
				}
			}
		}
	}
	
	//Used to synchronize the TileEntity with the client onBlockUpdate
	@Override
	public SUpdateTileEntityPacket getUpdatePacket()
	{
		CompoundNBT compound = new CompoundNBT();
		this.saveToNBT(compound);
		return new SUpdateTileEntityPacket(this.getPos(), -1, compound);
	}
	
	//Used to synchronize the TileEntity with the client onBlockUpdate
	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
	{
		CompoundNBT compound = pkt.getNbtCompound();
	    this.loadFromNBT(compound);
	}
	
	//Used to synchronize the TileEntity with the client when the chunk it is in is loaded
	@Override
	public CompoundNBT getUpdateTag()
	{
		return this.write(new CompoundNBT());
	}
	
	//Used to synchronize the TileEntity with the client when the chunk it is in is loaded
	@Override
	public void handleUpdateTag(BlockState state, CompoundNBT compound)
	{
		this.read(state, compound);
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		super.write(compound);
		return this.saveToNBT(compound);
	}
	
	@Override
	public void read(BlockState state, CompoundNBT compound)
	{
		super.read(state, compound);
		this.loadFromNBT(compound);
	}
	
	/**
	 * Used to load data from the NBT
	 */
	private CompoundNBT saveToNBT(CompoundNBT compound)
	{
		CompoundNBT onlineDetectorNBT = new CompoundNBT();
		if(this.ownerID != null)
			onlineDetectorNBT.putUniqueId("OwnerID", this.ownerID);
		if(this.ownerName != null)
			onlineDetectorNBT.putString("OwnerName", this.ownerName);
		if(this.ownerHead != null)
			onlineDetectorNBT.put("OwnerHead", this.ownerHead.write(new CompoundNBT()));
		compound.put("OnlineDetectorValues", onlineDetectorNBT);
		return compound;
	}
	
	/**
	 * Used to load data from the NBT
	 */
	private void loadFromNBT(CompoundNBT compound)
	{
		CompoundNBT onlineDetectorNBT = compound.getCompound("OnlineDetectorValues");
		if(onlineDetectorNBT.contains("OwnerID"))
			this.ownerID = onlineDetectorNBT.getUniqueId("OwnerID");
		if(onlineDetectorNBT.contains("OwnerName", NBT.TAG_STRING))
			this.ownerName = onlineDetectorNBT.getString("OwnerName");
		if(onlineDetectorNBT.contains("OwnerHead"))
			this.ownerHead = ItemStack.read(onlineDetectorNBT.getCompound("OwnerHead"));
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