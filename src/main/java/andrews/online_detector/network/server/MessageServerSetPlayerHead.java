package andrews.online_detector.network.server;

import andrews.online_detector.block_entities.AdvancedOnlineDetectorBlockEntity;
import andrews.online_detector.util.Reference;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.UUID;

public class MessageServerSetPlayerHead
{
	public static ResourceLocation PACKET_ID = new ResourceLocation(Reference.MODID, "set_player_head_packet");

	public static void registerPacket()
	{
		ServerPlayNetworking.registerGlobalReceiver(PACKET_ID, (minecraftServer, serverPlayer, packetListener, buf, packetSender) ->
		{
			BlockPos pos = buf.readBlockPos();
			ItemStack stack = buf.readItem();

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
						advancedOnlineDetectorBlockEntity.setOwnerHead(stack);
						level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
			        }
				}
			});
		});
	}

//	private final BlockPos pos;
//	private final ItemStack stack;
//
//	public MessageServerSetPlayerHead(BlockPos pos, ItemStack stack)
//	{
//		this.pos = pos;
//        this.stack = stack;
//    }
//
//	public void serialize(FriendlyByteBuf buf)
//	{
//		buf.writeBlockPos(pos);
//		buf.writeItemStack(stack, false);
//	}
//
//	public static MessageServerSetPlayerHead deserialize(FriendlyByteBuf buf)
//	{
//		BlockPos pos = buf.readBlockPos();
//		ItemStack stack = buf.readItem();
//		return new MessageServerSetPlayerHead(pos, stack);
//	}
//
//	public static void handle(MessageServerSetPlayerHead message, Supplier<NetworkEvent.Context> ctx)
//	{
//		NetworkEvent.Context context = ctx.get();
//		Player player = context.getSender();
//		Level level = player.getLevel();
//		BlockPos blockEntityPos = message.pos;
//		ItemStack stack = message.stack;
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
//						advancedOnlineDetectorBlockEntity.setOwnerHead(stack);
//						level.sendBlockUpdated(message.pos, level.getBlockState(blockEntityPos), level.getBlockState(blockEntityPos), 2);
//			        }
//				}
//			});
//			context.setPacketHandled(true);
//		}
//	}
}