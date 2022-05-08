package andrews.online_detector.registry.util;

import andrews.online_detector.util.Reference;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;

public class RegistryUtils
{
	/**
	 * Creates a Block
	 */
	public static Block createBlock(String name, Block block, @Nullable CreativeModeTab group)
	{
		Registry.register(Registry.ITEM, new ResourceLocation(Reference.MODID, name), new BlockItem(block, new Item.Properties().tab(group)));
		return Registry.register(Registry.BLOCK, new ResourceLocation(Reference.MODID, name), block);
	}
}