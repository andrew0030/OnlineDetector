package andrews.online_detector.network;

import andrews.online_detector.network.server.MessageServerSelectPlayer;
import andrews.online_detector.network.server.MessageServerSetPlayerHead;

public class ODNetwork
{
	public static void registerNetworkMessages()
	{
		MessageServerSelectPlayer.registerPacket();
		MessageServerSetPlayerHead.registerPacket();
	}
}