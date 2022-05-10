package andrews.online_detector.util;

import andrews.online_detector.block_entities.model.EyeModel;
import andrews.online_detector.registry.ODBlockEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;

@Environment(EnvType.CLIENT)
public class ODClientInit implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        EntityModelLayerRegistry.registerModelLayer(EyeModel.EYE_LAYER_LOCATION, EyeModel::createBodyLayer);
        ODBlockEntities.registerBlockEntityRenderers();
    }
}