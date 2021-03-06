package defeatedcrow.hac.main.client.model;

import defeatedcrow.hac.core.client.base.ModelThinBiped;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// ぼうし
@SideOnly(Side.CLIENT)
public class ModelHoodie extends ModelThinBiped {
	ModelRenderer headH;
	ModelRenderer hoodH;
	ModelRenderer bodyH;
	ModelRenderer rightArmH;
	ModelRenderer leftArmH;

	public boolean isSneak = false;
	public boolean isBlocking = false;
	public boolean aimedBow = false;
	public int slot;

	public ModelHoodie(int b) {
		this(0.75F, b);
	}

	public ModelHoodie(float f, int b) {
		this(f, 0.0F, 64, 32, b);
	}

	public ModelHoodie(float f1, float f2, int i3, int i4, int s) {
		super(s);
		slot = s;
		textureWidth = i3;
		textureHeight = i4;
		headH = new ModelRenderer(this, 0, 0);
		headH.addBox(-4F, -8F, -4F, 8, 8, 8, f1);
		headH.setRotationPoint(0F, 0F, 0F);
		headH.setTextureSize(64, 32);
		headH.mirror = true;
		bodyH = new ModelRenderer(this, 0, 16);
		bodyH.addBox(-4F, 0F, -2F, 8, 12, 4, f1);
		bodyH.setRotationPoint(0F, 0F, 0F);
		bodyH.setTextureSize(64, 32);
		bodyH.mirror = true;
		rightArmH = new ModelRenderer(this, 40, 16);
		rightArmH.addBox(-3F, -2F, -2F, 4, 12, 4, f1);
		rightArmH.setRotationPoint(-5F, 2F, 0F);
		rightArmH.setTextureSize(64, 32);
		rightArmH.mirror = true;
		leftArmH = new ModelRenderer(this, 24, 16);
		leftArmH.addBox(-1F, -2F, -2F, 4, 12, 4, f1);
		leftArmH.setRotationPoint(5F, 2F, 0F);
		leftArmH.setTextureSize(64, 32);
		leftArmH.mirror = true;
		hoodH = new ModelRenderer(this, 32, 0);
		hoodH.addBox(-4.5F, -0.5F, -2.5F, 9, 6, 5, f1);
		hoodH.setRotationPoint(0F, 0F, 0F);
		hoodH.setTextureSize(64, 32);
		hoodH.mirror = true;
	}

	@Override
	public void render(Entity ent, float f2, float f3, float f4, float f5, float f6, float f7) {
		this.setRotationAngles(f2, f3, f4, f5, f6, f7, ent);
		GlStateManager.pushMatrix();

		// showModelをここでいじる
		hoodH.showModel = true;
		headH.showModel = true;
		bodyH.showModel = true;
		rightArmH.showModel = true;
		leftArmH.showModel = true;

		if (this.isChild) {
			float f = 2.0F;
			GlStateManager.scale(1.5F / f, 1.5F / f, 1.5F / f);
			GlStateManager.translate(0.0F, 16.0F * f7, 0.0F);
			this.headH.render(f7);
			GlStateManager.popMatrix();
			GlStateManager.pushMatrix();
			GlStateManager.scale(1.0F / f, 1.0F / f, 1.0F / f);
			GlStateManager.translate(0.0F, 24.0F * f7, 0.0F);
			this.bodyH.render(f7);
			this.rightArmH.render(f7);
			this.leftArmH.render(f7);
			this.hoodH.render(f7);
		} else {
			if (ent.isSneaking()) {
				GlStateManager.translate(0.0F, 0.2F, 0.0F);
			}
			this.headH.render(f7);
			this.bodyH.render(f7);
			this.rightArmH.render(f7);
			this.leftArmH.render(f7);
			this.hoodH.render(f7);
		}

		GlStateManager.popMatrix();
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scaleFactor, Entity entity) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);

		setAngle(headH, this.head);
		setAngle(bodyH, this.body);
		setAngle(hoodH, this.body);
		setAngle(rightArmH, this.rightArm);
		setAngle(leftArmH, this.leftArm);
	}

	@Override
	public void setModelAttributes(ModelBase model) {
		super.setModelAttributes(model);

		if (model instanceof ModelBiped) {
			ModelBiped modelbiped = (ModelBiped) model;
			this.leftArmPose = modelbiped.leftArmPose;
			this.rightArmPose = modelbiped.rightArmPose;
			this.isSneak = modelbiped.isSneak;
			this.isChild = modelbiped.isChild;
			this.isRiding = modelbiped.isRiding;
			this.swingProgress = modelbiped.swingProgress;
		}
	}

	@Override
	public void setVisible(boolean invisible) {
		super.setVisible(invisible);
		this.headH.showModel = invisible;
		this.bodyH.showModel = invisible;
		this.rightArmH.showModel = invisible;
		this.leftArmH.showModel = invisible;
		this.hoodH.showModel = invisible;
	}
}
