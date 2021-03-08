package andrews.online_detector.util;

import java.util.UUID;

import andrews.online_detector.network.ODNetwork;
import andrews.online_detector.network.server.MessageServerSelectPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class NetworkUtil
{
	@OnlyIn(Dist.CLIENT)
	public static void newSelectPlayerMessage(BlockPos pos, UUID uuid, String name)
	{
		ODNetwork.CHANNEL.sendToServer(new MessageServerSelectPlayer(pos, uuid, name));
	}
}