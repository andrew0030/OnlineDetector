package andrews.online_detector.events;

import andrews.online_detector.registry.ODBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;

public class CreativeTabEvents
{
    public static void init()
    {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.REDSTONE_BLOCKS).register(entries -> {
            entries.accept(ODBlocks.ONLINE_DETECTOR);
            entries.accept(ODBlocks.ADVANCED_ONLINE_DETECTOR);
        });
    }
}