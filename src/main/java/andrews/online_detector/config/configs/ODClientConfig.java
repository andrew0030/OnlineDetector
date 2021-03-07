package andrews.online_detector.config.configs;

import andrews.online_detector.config.util.ConfigHelper;
import andrews.online_detector.config.util.ConfigHelper.ConfigValueListener;
import net.minecraftforge.common.ForgeConfigSpec;

public class ODClientConfig
{
	public static class ODClientConfigValues
	{
		public ConfigValueListener<Boolean> shouldShowRedstoneParticles;
		public ConfigValueListener<Boolean> shouldShowPortalParticles;

		public ODClientConfigValues(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber)
		{
			builder.comment(" Client Settings for Online Detector")
            .push("Particles");
			
				shouldShowRedstoneParticles = subscriber.subscribe(builder
					.comment(" Whether or not there should be Redstone particles while the Online Detector Block is active.")
					.define("shouldShowRedstoneParticles", true));
				shouldShowPortalParticles = subscriber.subscribe(builder
					.comment(" Whether or not there should be Portal particles while the Online Detector Block is active.")
					.define("shouldShowPortalParticles", true));
				
			builder.pop();
		}
	}
}