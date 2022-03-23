package andrews.online_detector.util;

import java.util.UUID;

import andrews.online_detector.network.ODNetwork;
import andrews.online_detector.network.server.MessageServerSelectPlayer;
import andrews.online_detector.network.server.MessageServerSetPlayerHead;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class NetworkUtil
{
	@OnlyIn(Dist.CLIENT)
	public static void newSelectPlayerMessage(BlockPos pos, UUID uuid, String name)
	{
		ODNetwork.CHANNEL.sendToServer(new MessageServerSelectPlayer(pos, uuid, name));
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void setPlayerHeadMessage(BlockPos pos, ItemStack stack)
	{
		ODNetwork.CHANNEL.sendToServer(new MessageServerSetPlayerHead(pos, stack));
	}
}