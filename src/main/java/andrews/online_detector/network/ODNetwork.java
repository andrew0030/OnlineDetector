package andrews.online_detector.network;

import andrews.online_detector.network.server.MessageServerSelectPlayer;
import andrews.online_detector.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class ODNetwork
{
public static final String NETWORK_PROTOCOL = "1";
	
	public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(Reference.MODID, "net"))
		.networkProtocolVersion(() -> NETWORK_PROTOCOL)
		.clientAcceptedVersions(NETWORK_PROTOCOL::equals)
		.serverAcceptedVersions(NETWORK_PROTOCOL::equals)
		.simpleChannel();
	
	/**
	 * Used to set up all the Messages
	 */
	public static void setupMessages()
	{
		int id = -1;
		//Client Messages
		
		//Server Messages
		CHANNEL.messageBuilder(MessageServerSelectPlayer.class, id++)
		.encoder(MessageServerSelectPlayer::serialize)
		.decoder(MessageServerSelectPlayer::deserialize)
		.consumer(MessageServerSelectPlayer::handle)
		.add();
	}
}