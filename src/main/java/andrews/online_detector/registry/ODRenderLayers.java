package andrews.online_detector.registry;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;

public class ODRenderLayers
{
	private static final RenderType CUTOUT = RenderType.getCutout();
	//May need these in the future.
//	private static final RenderType CUTOUT_MIPPED = RenderType.getCutoutMipped();
//	private static final RenderType TRANSLUSCENT = RenderType.getTranslucent();
	
	public static void setBlockRenderLayers()
	{
		RenderTypeLookup.setRenderLayer(ODBlocks.ONLINE_DETECTOR.get(), CUTOUT);
	}
}