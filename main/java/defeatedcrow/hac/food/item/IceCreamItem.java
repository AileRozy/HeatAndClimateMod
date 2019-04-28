package defeatedcrow.hac.food.item;

import java.util.List;

import javax.annotation.Nullable;

import defeatedcrow.hac.core.ClimateCore;
import defeatedcrow.hac.core.base.FoodEntityBase;
import defeatedcrow.hac.core.base.FoodItemBase;
import defeatedcrow.hac.food.entity.IceCreamBerryEntity;
import defeatedcrow.hac.food.entity.IceCreamCookieEntity;
import defeatedcrow.hac.food.entity.IceCreamEntity;
import defeatedcrow.hac.food.entity.IceCreamKinakoEntity;
import defeatedcrow.hac.food.entity.IceCreamLemonEntity;
import defeatedcrow.hac.main.util.DCName;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class IceCreamItem extends FoodItemBase {

	public IceCreamItem(boolean isWolfFood) {
		super(isWolfFood);
	}

	@Override
	public int getMaxMeta() {
		return 4;
	}

	@Override
	public String getTexPath(int meta, boolean f) {
		int i = MathHelper.clamp(0, meta, 4);
		String s = "items/food/icecream";
		s += "_" + this.getNameSuffix()[i];
		if (f) {
			s = "textures/" + s;
		}
		return ClimateCore.PACKAGE_ID + ":" + s;
	}

	@Override
	public String[] getNameSuffix() {
		String[] s = {
				"milk",
				"kinako",
				"berry",
				"lemon",
				"cookie"
		};
		return s;
	}

	@Override
	public Entity getPlacementEntity(World world, EntityPlayer player, double x, double y, double z, ItemStack item) {
		int i = item.getMetadata();
		FoodEntityBase ret = new IceCreamEntity(world, x, y, z, player);
		if (i == 1) {
			ret = new IceCreamKinakoEntity(world, x, y, z, player);
		}
		if (i == 2) {
			ret = new IceCreamBerryEntity(world, x, y, z, player);
		}
		if (i == 3) {
			ret = new IceCreamLemonEntity(world, x, y, z, player);
		}
		if (i == 4) {
			ret = new IceCreamCookieEntity(world, x, y, z, player);
		}
		return ret;
	}

	@Override
	public List<PotionEffect> getPotionEffect(int meta) {
		List<PotionEffect> ret = super.getPotionEffect(meta);
		ret.add(new PotionEffect(MobEffects.FIRE_RESISTANCE, MathHelper.ceil(600), 0));
		return ret;
	}

	@Override
	public int getFoodAmo(int meta) {
		return meta == 0 ? 3 : 4;
	}

	@Override
	public float getSaturation(int meta) {
		return 0.15F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation2(ItemStack stack, @Nullable World world, List<String> tooltip) {
		tooltip.add(DCName.PLACEABLE_ENTITY.getLocalizedName());
		PotionEffect eff = new PotionEffect(MobEffects.FIRE_RESISTANCE, MathHelper.ceil(600));
		String effName = I18n.format(MobEffects.FIRE_RESISTANCE.getName());
		effName += " (" + StringUtils.ticksToElapsedTime(600) + ")";
		tooltip.add(TextFormatting.AQUA.toString() + effName);
	}

}
