package andrews.online_detector.util;

import andrews.online_detector.network.server.MessageServerSelectPlayer;
import andrews.online_detector.network.server.MessageServerSetPlayerHead;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class NetworkUtil
{
	public static void newSelectPlayerMessage(BlockPos pos, UUID uuid, String name)
	{
		FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
		passedData.writeBlockPos(pos);
		passedData.writeUUID(uuid);
		passedData.writeUtf(name);
		ClientPlayNetworking.send(MessageServerSelectPlayer.PACKET_ID, passedData);
	}

	public static void setPlayerHeadMessage(BlockPos pos, ItemStack stack)
	{
		FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
		passedData.writeBlockPos(pos);
		passedData.writeItem(stack);
		ClientPlayNetworking.send(MessageServerSetPlayerHead.PACKET_ID, passedData);
	}
}