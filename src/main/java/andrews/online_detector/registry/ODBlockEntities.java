package andrews.online_detector.registry;

import andrews.online_detector.block_entities.AdvancedOnlineDetectorBlockEntity;
import andrews.online_detector.block_entities.OnlineDetectorBlockEntity;
import andrews.online_detector.block_entities.render.AdvancedOnlineDetectorBlockEntityRenderer;
import andrews.online_detector.block_entities.render.OnlineDetectorBlockEntityRenderer;
import andrews.online_detector.util.Reference;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ODBlockEntities
{
	public static final BlockEntityType<OnlineDetectorBlockEntity> ONLINE_DETECTOR = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(Reference.MODID, "online_detector"), FabricBlockEntityTypeBuilder.create(OnlineDetectorBlockEntity::new, ODBlocks.ONLINE_DETECTOR).build(null));
	public static final BlockEntityType<AdvancedOnlineDetectorBlockEntity> ADVANCED_ONLINE_DETECTOR = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(Reference.MODID, "advanced_online_detector"), FabricBlockEntityTypeBuilder.create(AdvancedOnlineDetectorBlockEntity::new, ODBlocks.ADVANCED_ONLINE_DETECTOR).build(null));

	public static void init() {}

	public static void registerBlockEntityRenderers()
    {
		BlockEntityRenderers.register(ONLINE_DETECTOR, OnlineDetectorBlockEntityRenderer::new);
		BlockEntityRenderers.register(ADVANCED_ONLINE_DETECTOR, AdvancedOnlineDetectorBlockEntityRenderer::new);
    }
}