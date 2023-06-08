package andrews.online_detector.events;

import andrews.online_detector.registry.ODBlocks;
import andrews.online_detector.util.Reference;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeTabEvents
{
    @SubscribeEvent
    public static void addItemsToTabs(BuildCreativeModeTabContentsEvent event)
    {
        if(event.getTabKey().equals(CreativeModeTabs.REDSTONE_BLOCKS))
        {
            event.accept(ODBlocks.ONLINE_DETECTOR);
            event.accept(ODBlocks.ADVANCED_ONLINE_DETECTOR);
        }
    }
}