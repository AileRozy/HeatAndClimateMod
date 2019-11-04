package defeatedcrow.hac.main.client.entity;

import defeatedcrow.hac.core.client.base.DCFoodModelBase;
import defeatedcrow.hac.magic.client.ModelDagger;
import defeatedcrow.hac.main.entity.EntityBulletDC;
import defeatedcrow.hac.main.entity.EntityBulletDC.BulletType;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BoltRenderer extends Render<EntityBulletDC> {

	private static final ResourceLocation BOLT_TEX = new ResourceLocation("dcs_climate",
			"textures/entity/bullet_bolt.png");
	private static final ResourceLocation BULLET_TEX = new ResourceLocation("dcs_climate",
			"textures/entity/bullet_iron.png");
	private static final ResourceLocation ARROW_TEX = new ResourceLocation("dcs_climate",
			"textures/entity/bullet_arrow.png");
	private static final ModelDagger MODEL = new ModelDagger(false);

	public BoltRenderer(RenderManager renderManager) {
		super(renderManager);
		this.shadowSize = 0.15F;
		this.shadowOpaque = 0.75F;
	}

	@Override
	public void doRender(EntityBulletDC entity, double x, double y, double z, float yaw, float partialTicks) {
		if (entity.getBulletType() != BulletType.SHOTGUN) {
			float height = entity.height * 0.5F;
			DCFoodModelBase model = this.getEntityModel();
			ResourceLocation tex = this.getTexture();

			GlStateManager.pushMatrix();
			GlStateManager.translate((float) x, (float) y, (float) z);
			GlStateManager.scale(-1.0F, -1.0F, 1.0F);
			this.bindTexture(tex);

			float rotX = -(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks);
			float rotY =
					180.0F - (entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks);
			float rotZ = 0.0F;

			GlStateManager.rotate(rotY, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(rotX, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(rotZ, 0.0F, 0.0F, 1.0F);

			model.render(0.0625F, null);

			GlStateManager.popMatrix();
		}
		super.doRender(entity, x, y, z, yaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBulletDC entity) {
		if (entity.getBulletType() == BulletType.BOLT)
			return BOLT_TEX;
		if (entity.getBulletType() == BulletType.ARROW)
			return ARROW_TEX;
		return BULLET_TEX;
	}

	protected ResourceLocation getTexture() {
		return BULLET_TEX;
	}

	protected DCFoodModelBase getEntityModel() {
		return MODEL;
	}
}
