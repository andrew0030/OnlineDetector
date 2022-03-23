package andrews.online_detector.registry;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;

public class ODRenderLayers
{
	private static final RenderType CUTOUT = RenderType.cutout();
	//May need these in the future.
//	private static final RenderType CUTOUT_MIPPED = RenderType.getCutoutMipped();
//	private static final RenderType TRANSLUSCENT = RenderType.getTranslucent();
	
	public static void setBlockRenderLayers()
	{
		ItemBlockRenderTypes.setRenderLayer(ODBlocks.ONLINE_DETECTOR.get(), CUTOUT);
	}
}