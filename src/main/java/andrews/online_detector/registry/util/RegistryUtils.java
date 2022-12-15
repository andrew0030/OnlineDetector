package andrews.online_detector.registry.util;

import andrews.online_detector.util.Reference;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class RegistryUtils
{
	/**
	 * Creates a Block
	 */
	public static Block createBlock(String name, Block block)
	{
		Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Reference.MODID, name), new BlockItem(block, new Item.Properties()));
		return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Reference.MODID, name), block);
	}
}