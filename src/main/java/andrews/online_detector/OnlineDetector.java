package andrews.online_detector;

import andrews.online_detector.network.ODNetwork;
import net.fabricmc.api.ModInitializer;

public class OnlineDetector implements ModInitializer
{
	@Override
	public void onInitialize()
	{
		// Registers the Network Messages
		ODNetwork.registerNetworkMessages();
	}

//	public OnlineDetector()
//	{
//		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
//
//		ODItems.ITEMS.register(modEventBus);
//		ODBlocks.BLOCKS.register(modEventBus);
//		ODBlockEntities.BLOCK_ENTITY_TYPES.register(modEventBus);
//
//		DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
//		{
//			modEventBus.addListener(EventPriority.LOWEST, this::setupClient);
//			modEventBus.addListener(this::setupLayers);
//		});
//		modEventBus.addListener(EventPriority.LOWEST, this::setupCommon);
//
//		ODConfigs.registerConfigs();
//	}
//
//	void setupCommon(final FMLCommonSetupEvent event)
//	{
//		event.enqueueWork(() -> { });
//		//Thread Safe Stuff Bellow
//		ODNetwork.setupMessages();
//
//	}
//
//	void setupClient(final FMLClientSetupEvent event)
//	{
//		event.enqueueWork(() ->
//		{
//			ODBlockEntities.registerBlockEntityRenderers();
//		});
//		// Thread Safe Stuff Bellow
//	}
//
//	void setupLayers(final EntityRenderersEvent.RegisterLayerDefinitions event)
//	{
//		event.registerLayerDefinition(EyeModel.EYE_LAYER_LOCATION, EyeModel::createBodyLayer);
//	}
}