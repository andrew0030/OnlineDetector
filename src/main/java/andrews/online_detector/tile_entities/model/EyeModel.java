package andrews.online_detector.tile_entities.model;

import net.minecraft.client.renderer.model.ModelRenderer;

public class EyeModel
{
	public final ModelRenderer eye;

	public EyeModel()
	{
		int[] textureSize = {32, 32};
		eye = new ModelRenderer(textureSize[0], textureSize[1], 0, 0);
		eye.setRotationPoint(0.0F, 24.0F, 0.0F);
		eye.setTextureOffset(0, 0).addBox(-4.0F, -3.0F, -4.0F, 8.0F, 3.0F, 8.0F, 0.0F, false);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}