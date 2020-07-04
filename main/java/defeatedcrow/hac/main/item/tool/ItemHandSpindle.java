package defeatedcrow.hac.main.item.tool;

import defeatedcrow.hac.core.ClimateCore;
import defeatedcrow.hac.core.base.DCItem;
import defeatedcrow.hac.core.util.DCUtil;
import net.minecraft.item.ItemStack;

public class ItemHandSpindle extends DCItem {

	public ItemHandSpindle() {
		super();
		this.setMaxStackSize(1);
		this.setHasSubtypes(false);
		this.setFull3D();
	}

	@Override
	public int getMaxMeta() {
		return 0;
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getTexPath(int meta, boolean f) {
		String s = "items/tool/hand_spindle";
		if (f) {
			s = "textures/" + s;
		}
		return ClimateCore.PACKAGE_ID + ":" + s;
	}

	@Override
	public String[] getNameSuffix() {
		String[] s = { "normal" };
		return s;
	}

	/* crafting tool */
	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		if (!DCUtil.isEmpty(stack) && stack.getItem() == this) {
			ItemStack copy = stack.copy();
			return copy;
		}
		return stack;
	}

	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}

}
