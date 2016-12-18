package defeatedcrow.hac.main.item.food;

import defeatedcrow.hac.core.ClimateCore;
import defeatedcrow.hac.core.base.DCFoodItem;

public class ItemDCFoods extends DCFoodItem {

	private final int maxMeta;

	private static String[] names = {
			"baked_apple",
			"boiled_egg",
			"jerky" };

	public ItemDCFoods(int max, boolean f) {
		super(f);
		maxMeta = max;
	}

	@Override
	public int getMaxMeta() {
		return maxMeta;
	}

	/*
	 * 0: 焼きリンゴ
	 * 1: ゆで卵
	 * 2: ジャーキー
	 */
	@Override
	public String[] getNameSuffix() {
		return names;
	}

	@Override
	public String getTexPath(int meta, boolean f) {
		String s = "items/food/" + names[meta];
		if (f) {
			s = "textures/" + s;
		}
		return ClimateCore.PACKAGE_ID + ":" + s;
	}

	@Override
	public int getFoodAmo(int meta) {
		switch (meta) {
		case 0:
			return 4;
		case 1:
			return 6;
		case 2:
			return 6;
		default:
			return 2;
		}
	}

	@Override
	public float getSaturation(int meta) {
		return 0.6F;
	}

}