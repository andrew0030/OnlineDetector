package andrews.online_detector.network.server;

import andrews.online_detector.block_entities.AdvancedOnlineDetectorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class MessageServerSelectPlayer
{
	private final BlockPos pos;
	private final UUID uuid;
	private final String name;
	
	public MessageServerSelectPlayer(BlockPos pos, UUID uuid, String name)
	{
		this.pos = pos;
        this.uuid = uuid;
        this.name = name;
    }
	
	public void serialize(FriendlyByteBuf buf)
	{
		buf.writeBlockPos(pos);
		buf.writeUUID(uuid);
		buf.writeUtf(name);
	}
	
	public static MessageServerSelectPlayer deserialize(FriendlyByteBuf buf)
	{
		BlockPos pos = buf.readBlockPos();
		UUID uuid = buf.readUUID();
		String name = buf.readUtf(32767);
		return new MessageServerSelectPlayer(pos, uuid, name);
	}
	
	public static void handle(MessageServerSelectPlayer message, Supplier<NetworkEvent.Context> ctx)
	{
		NetworkEvent.Context context = ctx.get();
		Player player = context.getSender();
		BlockPos blockEntityPos = message.pos;
		UUID uuid = message.uuid;
		String name = message.name;
		
		if(context.getDirection().getReceptionSide() == LogicalSide.SERVER)
		{
			context.enqueueWork(() ->
			{
				if(player != null)
				{
					Level level = player.level();
					BlockEntity blockEntity = level.getBlockEntity(blockEntityPos);
					// We make sure the TileEntity is an AdvancedOnlineDetectorBlockEntity
					if(blockEntity instanceof AdvancedOnlineDetectorBlockEntity advancedOnlineDetectorBlockEntity)
			        {
						advancedOnlineDetectorBlockEntity.setOwnerUUID(uuid);
						advancedOnlineDetectorBlockEntity.setOwnerName(name);
						level.sendBlockUpdated(message.pos, level.getBlockState(blockEntityPos), level.getBlockState(blockEntityPos), 2);
			        }
				}
			});
			context.setPacketHandled(true);
		}
	}
}