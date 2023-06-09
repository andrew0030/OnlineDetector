package andrews.online_detector;

import andrews.online_detector.config.ODConfig;
import andrews.online_detector.events.CreativeTabEvents;
import andrews.online_detector.network.ODNetwork;
import andrews.online_detector.registry.ODBlockEntities;
import andrews.online_detector.registry.ODBlocks;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class OnlineDetector implements ModInitializer
{
	public static ODConfig OD_CONFIG;

	@Override
	public void onInitialize()
	{
		AutoConfig.register(ODConfig.class, JanksonConfigSerializer::new);
		OD_CONFIG = AutoConfig.getConfigHolder(ODConfig.class).getConfig();

		ODBlocks.init();
		ODBlockEntities.init();

		CreativeTabEvents.init();

		ODNetwork.registerNetworkMessages();
	}
}