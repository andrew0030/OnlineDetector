package andrews.online_detector.registry;

import andrews.online_detector.objects.blocks.AdvancedOnlineDetectorBlock;
import andrews.online_detector.objects.blocks.OnlineDetectorBlock;
import andrews.online_detector.registry.util.RegistryUtils;
import net.minecraft.world.level.block.Block;

public class ODBlocks
{
	public static final Block ONLINE_DETECTOR = RegistryUtils.createBlock("online_detector", new OnlineDetectorBlock());
	public static final Block ADVANCED_ONLINE_DETECTOR = RegistryUtils.createBlock("advanced_online_detector", new AdvancedOnlineDetectorBlock());

	public static void init() {}
}