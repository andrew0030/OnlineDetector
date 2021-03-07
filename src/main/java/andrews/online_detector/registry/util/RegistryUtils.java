package andrews.online_detector.registry.util;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import andrews.online_detector.registry.ODBlocks;
import andrews.online_detector.registry.ODItems;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;

public class RegistryUtils
{
	/**
	 * Creates a Block
	 */
	public static <B extends Block> RegistryObject<B> createBlock(String name, Supplier<? extends B> supplier, @Nullable ItemGroup group)
	{
		RegistryObject<B> block = ODBlocks.BLOCKS.register(name, supplier);
		ODItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().group(group)));
		return block;
	}
}
