package andrews.online_detector.registry;

import andrews.online_detector.objects.blocks.AdvancedOnlineDetectorBlock;
import andrews.online_detector.objects.blocks.OnlineDetectorBlock;
import andrews.online_detector.registry.util.RegistryUtils;
import andrews.online_detector.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ODBlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MODID);
	
	public static final RegistryObject<Block> ONLINE_DETECTOR = RegistryUtils.createBlock("online_detector", () -> new OnlineDetectorBlock(), ItemGroup.REDSTONE);
	public static final RegistryObject<Block> ADVANCED_ONLINE_DETECTOR = RegistryUtils.createBlock("advanced_online_detector", () -> new AdvancedOnlineDetectorBlock(), ItemGroup.REDSTONE);
}
