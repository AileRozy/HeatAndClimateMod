package defeatedcrow.hac.food.item;

import java.util.List;

import javax.annotation.Nullable;

import defeatedcrow.hac.core.ClimateCore;
import defeatedcrow.hac.core.base.FoodEntityBase;
import defeatedcrow.hac.core.base.FoodItemBase;
import defeatedcrow.hac.food.entity.PancakeEntity;
import defeatedcrow.hac.food.entity.PitaBreadEntity;
import defeatedcrow.hac.food.entity.PizzaTomatoEntity;
import defeatedcrow.hac.food.entity.RoundBreadCreamEntity;
import defeatedcrow.hac.food.entity.RoundBreadEntity;
import defeatedcrow.hac.food.entity.SquareBreadEntity;
import defeatedcrow.hac.food.entity.ToastBreadEntity;
import defeatedcrow.hac.food.entity.ToastFrenchEntity;
import defeatedcrow.hac.food.entity.ToastGarlicEntity;
import defeatedcrow.hac.main.util.DCName;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RoundBreadItem extends FoodItemBase {

	public RoundBreadItem(boolean isWolfFood) {
		super(isWolfFood);
	}

	@Override
	public int getMaxMeta() {
		return 15;
	}

	@Override
	public String getTexPath(int meta, boolean f) {
		int i = MathHelper.clamp(0, meta, 15);
		String s = "items/food/" + this.getNameSuffix()[i];
		if (f) {
			s = "textures/" + s;
		}
		return ClimateCore.PACKAGE_ID + ":" + s;
	}

	@Override
	public String[] getNameSuffix() {
		String[] s = {
				"roundbread_raw",
				"roundbread_baked",
				"squarebread_raw",
				"squarebread_baked",
				"butter_toast_raw",
				"butter_toast_baked",
				"pizza_tomato_raw",
				"pizza_tomato_baked",
				"roundbread_cream",
				"french_toast",
				"garlic_toast_raw",
				"garlic_toast_baked",
				"pita_bread_raw",
				"pita_bread_baked",
				"pancake_raw",
				"pancake_baked"
		};
		return s;
	}

	@Override
	public Entity getPlacementEntity(World world, EntityPlayer player, double x, double y, double z, ItemStack item) {
		int i = item.getMetadata();
		FoodEntityBase ret = new RoundBreadEntity(world, x, y, z, player);
		if (i == 2 || i == 3) {
			ret = new SquareBreadEntity(world, x, y, z, player);
			if (i == 2) {
				((SquareBreadEntity) ret).setMOLD(true);
			}
		}
		if (i == 4 || i == 5) {
			ret = new ToastBreadEntity(world, x, y, z, player);
		}
		if (i == 6 || i == 7) {
			ret = new PizzaTomatoEntity(world, x, y, z, player);
		}
		if (i == 8) {
			ret = new RoundBreadCreamEntity(world, x, y, z, player);
		}
		if (i == 9) {
			ret = new ToastFrenchEntity(world, x, y, z, player);
		}
		if (i == 10 || i == 11) {
			ret = new ToastGarlicEntity(world, x, y, z, player);
		}
		if (i == 12 || i == 13) {
			ret = new PitaBreadEntity(world, x, y, z, player);
		}
		if (i == 14 || i == 15) {
			ret = new PancakeEntity(world, x, y, z, player);
		}

		if ((i & 1) == 0) {
			ret.setRAW(true);
		}
		return ret;
	}

	@Override
	public int getFoodAmo(int meta) {
		switch (meta) {
		case 0:
		case 2:
		case 4:
		case 6:
		case 12:
			return 0;
		case 1:
		case 3:
		case 13:
			return 6;
		case 11:
		case 15:
			return 7;
		case 5:
		case 10:
			return 6;
		case 7:
			return 10;
		case 8:
		case 9:
			return 8;
		}
		return 0;
	}

	@Override
	public float getSaturation(int meta) {
		if (meta == 8)
			return 0.5F;
		return (meta & 1) == 0 ? 0F : 0.5F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation2(ItemStack stack, @Nullable World world, List<String> tooltip) {
		tooltip.add(DCName.PLACEABLE_ENTITY.getLocalizedName());
	}

}
