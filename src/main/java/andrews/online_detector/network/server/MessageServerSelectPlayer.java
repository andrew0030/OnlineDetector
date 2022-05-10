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
}