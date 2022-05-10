package andrews.online_detector.config.configs;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "Client")
public class ODClientConfig implements ConfigData
{
    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip(count = 0)
    @Comment(value = """
            Whether or not there should be Redstone particles while the Online Detector Block is active.""")
    public boolean shouldShowRedstoneParticles = true;

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip(count = 0)
    @Comment(value = """
            Whether or not there should be Portal particles while the Online Detector Block is active.""")
    public boolean shouldShowPortalParticles = true;
}