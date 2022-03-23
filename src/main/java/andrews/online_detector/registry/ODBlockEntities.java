package andrews.online_detector.registry;

import andrews.online_detector.block_entities.AdvancedOnlineDetectorBlockEntity;
import andrews.online_detector.block_entities.OnlineDetectorBlockEntity;
import andrews.online_detector.block_entities.render.AdvancedOnlineDetectorBlockEntityRenderer;
import andrews.online_detector.block_entities.render.OnlineDetectorBlockEntityRenderer;
import andrews.online_detector.util.Reference;
import com.google.common.collect.Sets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ODBlockEntities
{
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Reference.MODID);

	public static final RegistryObject<BlockEntityType<OnlineDetectorBlockEntity>> ONLINE_DETECTOR = BLOCK_ENTITY_TYPES.register("online_detector", () -> new BlockEntityType<>(OnlineDetectorBlockEntity::new, Sets.newHashSet(ODBlocks.ONLINE_DETECTOR.get()), null));
	public static final RegistryObject<BlockEntityType<AdvancedOnlineDetectorBlockEntity>> ADVANCED_ONLINE_DETECTOR = BLOCK_ENTITY_TYPES.register("advanced_online_detector", () -> new BlockEntityType<>(AdvancedOnlineDetectorBlockEntity::new, Sets.newHashSet(ODBlocks.ADVANCED_ONLINE_DETECTOR.get()), null));

    public static void registerBlockEntityRenderers()
    {
		BlockEntityRenderers.register(ONLINE_DETECTOR.get(), OnlineDetectorBlockEntityRenderer::new);
		BlockEntityRenderers.register(ADVANCED_ONLINE_DETECTOR.get(), AdvancedOnlineDetectorBlockEntityRenderer::new);
    }
}