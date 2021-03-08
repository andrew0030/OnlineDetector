package andrews.online_detector.registry;

import com.google.common.collect.Sets;

import andrews.online_detector.tile_entities.AdvancedOnlineDetectorTileEntity;
import andrews.online_detector.tile_entities.OnlineDetectorTileEntity;
import andrews.online_detector.tile_entities.render.AdvancedOnlineDetectorTileEntityRenderer;
import andrews.online_detector.tile_entities.render.OnlineDetectorTileEntityRenderer;
import andrews.online_detector.util.Reference;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ODTileEntities
{
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Reference.MODID);
	
	public static final RegistryObject<TileEntityType<OnlineDetectorTileEntity>> ONLINE_DETECTOR = TILE_ENTITY_TYPES.register("online_detector", () -> new TileEntityType<>(OnlineDetectorTileEntity::new, Sets.newHashSet(ODBlocks.ONLINE_DETECTOR.get()), null));
	public static final RegistryObject<TileEntityType<AdvancedOnlineDetectorTileEntity>> ADVANCED_ONLINE_DETECTOR = TILE_ENTITY_TYPES.register("advanced_online_detector", () -> new TileEntityType<>(AdvancedOnlineDetectorTileEntity::new, Sets.newHashSet(ODBlocks.ADVANCED_ONLINE_DETECTOR.get()), null));
	
    @OnlyIn(Dist.CLIENT)
    public static void registerTileRenders()
    {
    	ClientRegistry.bindTileEntityRenderer(ONLINE_DETECTOR.get(), OnlineDetectorTileEntityRenderer::new);
    	ClientRegistry.bindTileEntityRenderer(ADVANCED_ONLINE_DETECTOR.get(), AdvancedOnlineDetectorTileEntityRenderer::new);
    }
}