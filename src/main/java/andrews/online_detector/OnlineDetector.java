package andrews.online_detector;

import andrews.online_detector.config.ODConfigs;
import andrews.online_detector.network.ODNetwork;
import andrews.online_detector.registry.ODBlocks;
import andrews.online_detector.registry.ODItems;
import andrews.online_detector.registry.ODTileEntities;
import andrews.online_detector.util.Reference;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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
	@SuppressWarnings("deprecation")
	public OnlineDetector()
	{
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		
		ODItems.ITEMS.register(modEventBus);
		ODBlocks.BLOCKS.register(modEventBus);
		ODTileEntities.TILE_ENTITY_TYPES.register(modEventBus);
		
		DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
		{
			modEventBus.addListener(EventPriority.LOWEST, this::setupClient);
		});
		modEventBus.addListener(EventPriority.LOWEST, this::setupCommon);
		
		ODConfigs.registerConfigs();
	}
	
	void setupCommon(final FMLCommonSetupEvent event)
	{
		ODNetwork.setupMessages();
	}
	
	@OnlyIn(Dist.CLIENT)
	void setupClient(final FMLClientSetupEvent event)
	{
		event.enqueueWork(ODTileEntities::registerTileRenders);
	}
}