package andrews.online_detector.config.configs;

import andrews.online_detector.config.util.ConfigHelper;
import andrews.online_detector.config.util.ConfigHelper.ConfigValueListener;
import net.minecraftforge.common.ForgeConfigSpec;

public class ODCommonConfig
{
	public static class ODCommonConfigValues
	{
		public ConfigValueListener<Integer> onlineCheckFrequency;

		public ODCommonConfigValues(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber)
		{
			builder.comment(" Common Settings for Online Detector")
            .push("Frequency");
			
				onlineCheckFrequency = subscriber.subscribe(builder
					.comment(" How often the Online Detector Block should check if the assigned player is online,"
							+"\r\n this value is measured in ticks (20 ticks are 1 second), it is not recommended to"
							+"\r\n set this value bellow 20")
					.defineInRange("onlineCheckFrequency", 20, 1, 1200));
				
			builder.pop();
		}
	}
}