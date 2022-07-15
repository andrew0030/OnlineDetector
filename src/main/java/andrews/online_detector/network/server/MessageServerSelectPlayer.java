package andrews.online_detector.network.server;

import java.util.UUID;
import java.util.function.Supplier;

import andrews.online_detector.tile_entities.AdvancedOnlineDetectorTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageServerSelectPlayer
{
	private BlockPos pos;
	private UUID uuid;
	private String name;
	
	public MessageServerSelectPlayer(BlockPos pos, UUID uuid, String name)
	{
		this.pos = pos;
        this.uuid = uuid;
        this.name = name;
    }
	
	public void serialize(PacketBuffer buf)
	{
		buf.writeBlockPos(pos);
		buf.writeUniqueId(uuid);
		buf.writeString(name);
	}
	
	public static MessageServerSelectPlayer deserialize(PacketBuffer buf)
	{
		BlockPos pos = buf.readBlockPos();
		UUID uuid = buf.readUniqueId();
		String name = buf.readString(32767);
		return new MessageServerSelectPlayer(pos, uuid, name);
	}
	
	public static void handle(MessageServerSelectPlayer message, Supplier<NetworkEvent.Context> ctx)
	{
		NetworkEvent.Context context = ctx.get();
		PlayerEntity player = context.getSender();
		World world = player.getEntityWorld();
		BlockPos tileEntityPos = message.pos;
		UUID uuid = message.uuid;
		String name = message.name;
		
		if(context.getDirection().getReceptionSide() == LogicalSide.SERVER)
		{
			context.enqueueWork(() ->
			{
				if(world != null)
				{
					TileEntity tileentity = world.getTileEntity(tileEntityPos);
					// We make sure the TileEntity is an AdvancedOnlineDetectorTileEntity
					if(tileentity instanceof AdvancedOnlineDetectorTileEntity)
			        {
						AdvancedOnlineDetectorTileEntity advancedOnlineDetectorTileEntity = (AdvancedOnlineDetectorTileEntity)tileentity;
						advancedOnlineDetectorTileEntity.setOwnerUUID(uuid);
						advancedOnlineDetectorTileEntity.setOwnerName(name);
						world.notifyBlockUpdate(message.pos, world.getBlockState(tileEntityPos), world.getBlockState(tileEntityPos), 2);
			        }
				}
			});
			context.setPacketHandled(true);
		}
	}
}