package defeatedcrow.hac.main.item.ores;

import java.util.List;

import javax.annotation.Nullable;

import defeatedcrow.hac.core.ClimateCore;
import defeatedcrow.hac.core.base.DCItem;
import defeatedcrow.hac.core.util.DCUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMiscDust extends DCItem {

	private final int maxMeta;

	private static String[] names = {
		"coal",
		"crystal",
		"lime",
		"wood",
		"presscake",
		"ash",
		"niter",
		"sulfur",
		"garnet",
		"apatite",
		"borax",
		"chrysotile",
		"mica",
		"slate" };

	public ItemMiscDust(int max) {
		super();
		maxMeta = max;
	}

	@Override
	public int getMaxMeta() {
		return maxMeta;
	}

	/*
	 * 0: 炭
	 * 1: 石英
	 * 2: 石灰
	 * 3: 木
	 * 4: 油かす
	 * 5: 灰
	 * 6: 硝石
	 * 7: 硫黄
	 * 8: ガーネット
	 * 9: アパタイト
	 * 10: ホウ砂
	 * 11: 石綿
	 * 12: 雲母
	 * 13: スレート片
	 */
	@Override
	public String[] getNameSuffix() {
		return names;
	}

	@Override
	public String getTexPath(int meta, boolean f) {
		String s = "items/ores/dust_" + names[meta];
		if (f) {
			s = "textures/" + s;
		}
		return ClimateCore.PACKAGE_ID + ":" + s;
	}

	@Override
	public EnumActionResult onItemUse2(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing,
			float hitX, float hitY, float hitZ) {
		ItemStack heldItem = player.getHeldItem(hand);
		if (DCUtil.isEmpty(heldItem))
			return EnumActionResult.FAIL;

		if (heldItem.getItemDamage() == 4 && ItemDye.applyBonemeal(heldItem, world, pos, player, hand)) {
			if (!world.isRemote) {
				world.playEvent(2005, pos, 0);
			}

			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flagIn) {
		if (!DCUtil.isEmpty(stack) && stack.getItemDamage() == 4) {
			tooltip.add(TextFormatting.YELLOW.toString() + TextFormatting.BOLD.toString() + "=== Tips ===");
			tooltip.add(I18n.format("dcs.tip.presscake"));
		}
	}

}
