package andrews.online_detector.config.configs;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "Common")
public class ODCommonConfig implements ConfigData
{
    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip(count = 0)
    @ConfigEntry.BoundedDiscrete(min = 1, max = 1200)
    @Comment(value = """
            How often the Online Detector Block should check if the assigned player is online,
            this value is measured in ticks (20 ticks are 1 second), it is not recommended to
            set this value bellow 20""")
    public int onlineCheckFrequency = 20;
}