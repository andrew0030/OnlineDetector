package andrews.online_detector.config;

import andrews.online_detector.config.configs.ODClientConfig;
import andrews.online_detector.config.configs.ODClientConfig.ODClientConfigValues;
import andrews.online_detector.config.configs.ODCommonConfig;
import andrews.online_detector.config.configs.ODCommonConfig.ODCommonConfigValues;
import andrews.online_detector.config.util.ConfigHelper;
import andrews.online_detector.util.Reference;
import net.minecraftforge.fml.config.ModConfig;

public class ODConfigs
{
	public static ODClientConfig.ODClientConfigValues ODClientConfig = null;
	public static ODCommonConfig.ODCommonConfigValues ODCommonConfig = null;
	
	public static void registerConfigs()
	{
		ODClientConfig = ConfigHelper.register(ModConfig.Type.CLIENT, ODClientConfigValues::new, createConfigName("client"));
		ODCommonConfig = ConfigHelper.register(ModConfig.Type.COMMON, ODCommonConfigValues::new, createConfigName("common"));
	}
	
	/**
	 * Helper method to make registering Config names easier
	 */
	private static String createConfigName(String name)
	{
		return Reference.MODID + "-" + name + ".toml";
	}
}