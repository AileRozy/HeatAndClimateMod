package defeatedcrow.hac.magic.client;

import defeatedcrow.hac.main.entity.EntityProjBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MagicRedSpitRenderer extends MagicDaggerRenderer {

	private static final ResourceLocation TEX = new ResourceLocation("dcs_climate",
			"textures/entity/magic/dagger_red.png");

	public MagicRedSpitRenderer(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityProjBase entity) {
		return TEX;
	}

	@Override
	protected ResourceLocation getTexture() {
		return TEX;
	}

}
