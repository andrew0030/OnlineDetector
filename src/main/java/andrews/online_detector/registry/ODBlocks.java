package andrews.online_detector.registry;

import andrews.online_detector.objects.blocks.AdvancedOnlineDetectorBlock;
import andrews.online_detector.objects.blocks.OnlineDetectorBlock;
import andrews.online_detector.registry.util.RegistryUtils;
import andrews.online_detector.util.Reference;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ODBlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MODID);
	
	public static final RegistryObject<Block> ONLINE_DETECTOR = RegistryUtils.createBlock("online_detector", OnlineDetectorBlock::new);
	public static final RegistryObject<Block> ADVANCED_ONLINE_DETECTOR = RegistryUtils.createBlock("advanced_online_detector", AdvancedOnlineDetectorBlock::new);
}