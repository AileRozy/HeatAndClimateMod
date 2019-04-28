package defeatedcrow.hac.food.item;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import defeatedcrow.hac.core.ClimateCore;
import defeatedcrow.hac.core.base.FoodEntityBase;
import defeatedcrow.hac.core.base.FoodItemBase;
import defeatedcrow.hac.food.entity.ChocolatePieEntity;
import defeatedcrow.hac.food.entity.CustardPieEntity;
import defeatedcrow.hac.food.entity.FruitPieEntity;
import defeatedcrow.hac.food.entity.MeatPieEntity;
import defeatedcrow.hac.food.entity.MooncakeEntity;
import defeatedcrow.hac.food.entity.SugarPieEntity;
import defeatedcrow.hac.main.util.DCName;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SquarePastryItem extends FoodItemBase {

	public SquarePastryItem(boolean isWolfFood) {
		super(isWolfFood);
	}

	@Override
	public int getMaxMeta() {
		return 11;
	}

	@Override
	public String getTexPath(int meta, boolean f) {
		int i = MathHelper.clamp(0, meta, 1);
		String s = "items/food/pie_" + this.getNameSuffix()[i];
		if (f) {
			s = "textures/" + s;
		}
		return ClimateCore.PACKAGE_ID + ":" + s;
	}

	@Override
	public String[] getNameSuffix() {
		String[] s = {
				"sugar_raw",
				"sugar_baked",
				"meat_raw",
				"meat_baked",
				"choco_raw",
				"choco_baked",
				"fruit_raw",
				"fruit_baked",
				"mooncake_raw",
				"mooncake_baked",
				"cream_raw",
				"cream_baked"
		};
		return s;
	}

	@Override
	public Entity getPlacementEntity(World world, EntityPlayer player, double x, double y, double z, ItemStack item) {
		int i = item.getMetadata();
		FoodEntityBase ret = null;
		switch (i) {
		case 0:
		case 1:
			ret = new SugarPieEntity(world, x, y, z, player);
			break;
		case 2:
		case 3:
			ret = new MeatPieEntity(world, x, y, z, player);
			break;
		case 4:
		case 5:
			ret = new ChocolatePieEntity(world, x, y, z, player);
			break;
		case 6:
		case 7:
			ret = new FruitPieEntity(world, x, y, z, player);
			break;
		case 8:
		case 9:
			ret = new MooncakeEntity(world, x, y, z, player);
			break;
		case 10:
		case 11:
			ret = new CustardPieEntity(world, x, y, z, player);
			break;
		default:
			ret = new SugarPieEntity(world, x, y, z, player);
		}

		if (ret != null && (i & 1) == 0) {
			ret.setRAW(true);
		}
		return ret;
	}

	@Override
	public int getFoodAmo(int meta) {
		switch (meta) {
		case 1:
			return 6;
		case 3:
			return 14;
		case 5:
			return 9;
		case 7:
			return 9;
		case 9:
			return 12;
		case 11:
			return 12;
		default:
			return 0;
		}
	}

	@Override
	public float getSaturation(int meta) {
		return (meta & 1) == 0 ? 0F : 0.9F;
	}

	@Override
	public List<PotionEffect> getPotionEffect(int meta) {
		List<PotionEffect> ret = new ArrayList<PotionEffect>();
		return ret;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation2(ItemStack stack, @Nullable World world, List<String> tooltip) {
		tooltip.add(DCName.PLACEABLE_ENTITY.getLocalizedName());
	}

}
