package andrews.online_detector.network.server;

import andrews.online_detector.block_entities.AdvancedOnlineDetectorBlockEntity;
import andrews.online_detector.util.Reference;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.UUID;

public class MessageServerSelectPlayer
{
	public static ResourceLocation PACKET_ID = new ResourceLocation(Reference.MODID, "select_player_packet");

	public static void registerPacket()
	{
		ServerPlayNetworking.registerGlobalReceiver(PACKET_ID, (minecraftServer, serverPlayer, packetListener, buf, packetSender) ->
		{
			BlockPos pos = buf.readBlockPos();
			UUID uuid = buf.readUUID();
			String name = buf.readUtf(32767);

			minecraftServer.execute(() ->
			{
				if(serverPlayer == null)
					return;

				Level level = serverPlayer.getLevel();
				if(level != null)
				{
					BlockEntity blockEntity = level.getBlockEntity(pos);
					// We make sure the TileEntity is a ChessTileEntity
					if(blockEntity instanceof AdvancedOnlineDetectorBlockEntity advancedOnlineDetectorBlockEntity)
			        {
						advancedOnlineDetectorBlockEntity.setOwnerUUID(uuid);
						advancedOnlineDetectorBlockEntity.setOwnerName(name);
						level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
			        }
				}
			});
		});
	}



//	private final BlockPos pos;
//	private final UUID uuid;
//	private final String name;
//
//	public MessageServerSelectPlayer(BlockPos pos, UUID uuid, String name)
//	{
//		this.pos = pos;
//        this.uuid = uuid;
//        this.name = name;
//    }
//
//	public void serialize(FriendlyByteBuf buf)
//	{
//		buf.writeBlockPos(pos);
//		buf.writeUUID(uuid);
//		buf.writeUtf(name);
//	}
//
//	public static MessageServerSelectPlayer deserialize(FriendlyByteBuf buf)
//	{
//		BlockPos pos = buf.readBlockPos();
//		UUID uuid = buf.readUUID();
//		String name = buf.readUtf(32767);
//		return new MessageServerSelectPlayer(pos, uuid, name);
//	}
//
//	public static void handle(MessageServerSelectPlayer message, Supplier<NetworkEvent.Context> ctx)
//	{
//		NetworkEvent.Context context = ctx.get();
//		Player player = context.getSender();
//		Level level = player.getLevel();
//		BlockPos blockEntityPos = message.pos;
//		UUID uuid = message.uuid;
//		String name = message.name;
//
//		if(context.getDirection().getReceptionSide() == LogicalSide.SERVER)
//		{
//			context.enqueueWork(() ->
//			{
//				if(level != null)
//				{
//					BlockEntity blockEntity = level.getBlockEntity(blockEntityPos);
//					// We make sure the TileEntity is a ChessTileEntity
//					if(blockEntity instanceof AdvancedOnlineDetectorBlockEntity advancedOnlineDetectorBlockEntity)
//			        {
//						advancedOnlineDetectorBlockEntity.setOwnerUUID(uuid);
//						advancedOnlineDetectorBlockEntity.setOwnerName(name);
//						level.sendBlockUpdated(message.pos, level.getBlockState(blockEntityPos), level.getBlockState(blockEntityPos), 2);
//			        }
//				}
//			});
//			context.setPacketHandled(true);
//		}
//	}
}