package andrews.online_detector.config;

import andrews.online_detector.config.configs.ODClientConfig;
import andrews.online_detector.config.configs.ODCommonConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "online_detector")
public class ODConfig implements ConfigData
{
    @ConfigEntry.Category("common")
    @ConfigEntry.Gui.TransitiveObject
    public andrews.online_detector.config.configs.ODCommonConfig ODCommonConfig = new ODCommonConfig();

    @ConfigEntry.Category("client")
    @ConfigEntry.Gui.TransitiveObject
    public andrews.online_detector.config.configs.ODClientConfig ODClientConfig = new ODClientConfig();
}