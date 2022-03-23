package andrews.online_detector;

import andrews.online_detector.config.ODConfigs;
import andrews.online_detector.network.ODNetwork;
import andrews.online_detector.registry.ODBlocks;
import andrews.online_detector.registry.ODItems;
import andrews.online_detector.registry.ODBlockEntities;
import andrews.online_detector.block_entities.model.EyeModel;
import andrews.online_detector.util.Reference;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value = Reference.MODID)
public class OnlineDetector
{
	public static OnlineDetector instance;

	@SuppressWarnings("deprecation")
	public OnlineDetector()
	{
		instance = this;
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		
		ODItems.ITEMS.register(modEventBus);
		ODBlocks.BLOCKS.register(modEventBus);
		ODBlockEntities.BLOCK_ENTITY_TYPES.register(modEventBus);
		
		DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
		{
			modEventBus.addListener(EventPriority.LOWEST, this::setupClient);
			modEventBus.addListener(this::setupLayers);
		});
		modEventBus.addListener(EventPriority.LOWEST, this::setupCommon);
		
		ODConfigs.registerConfigs();
	}

	void setupCommon(final FMLCommonSetupEvent event)
	{	
		event.enqueueWork(() -> { });
		//Thread Safe Stuff Bellow
		ODNetwork.setupMessages();
		
	}

	void setupClient(final FMLClientSetupEvent event)
	{
		event.enqueueWork(() ->
		{
			ODBlockEntities.registerBlockEntityRenderers();
		});
		// Thread Safe Stuff Bellow
	}

	void setupLayers(final EntityRenderersEvent.RegisterLayerDefinitions event)
	{
		event.registerLayerDefinition(EyeModel.EYE_LAYER_LOCATION, EyeModel::createBodyLayer);
	}
}