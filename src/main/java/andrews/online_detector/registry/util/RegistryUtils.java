package andrews.online_detector.registry.util;

import andrews.online_detector.registry.ODBlocks;
import andrews.online_detector.registry.ODItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class RegistryUtils
{
	/**
	 * Creates a Block
	 */
	public static <B extends Block> RegistryObject<B> createBlock(String name, Supplier<? extends B> supplier, @Nullable CreativeModeTab group)
	{
		RegistryObject<B> block = ODBlocks.BLOCKS.register(name, supplier);
		ODItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(group)));
		return block;
	}
}