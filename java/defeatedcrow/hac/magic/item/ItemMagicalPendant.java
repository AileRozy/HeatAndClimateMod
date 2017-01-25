package defeatedcrow.hac.magic.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import defeatedcrow.hac.api.damage.DamageSourceClimate;
import defeatedcrow.hac.api.magic.CharmType;
import defeatedcrow.hac.api.magic.IJewelCharm;
import defeatedcrow.hac.core.ClimateCore;
import defeatedcrow.hac.core.base.DCItem;
import defeatedcrow.hac.core.util.DCPotion;
import defeatedcrow.hac.main.MainInit;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * インベントリの最上段に入れていると効果のあるアクセサリー類。
 * ペンダントは常時系or防御系効果を持ち、耐久値の概念がない。
 */
public class ItemMagicalPendant extends DCItem implements IJewelCharm {

	private final int maxMeta;

	private static String[] names = {
			"chal_blue", /* 水耐性 */
			"chal_red", /* 火耐性 */
			"chal_white", /* ダメージ軽減 */
			"crystal", /* 悪性ポーション */
			"sapphire", /* 辞書閲覧機能 */
			"malachite", /* 常時暗視 */
			"celestite", /* 落下耐性 */
			"clam", /* 死亡時ワープ */
			"lapis", /* 範囲回収 */
			"diamond", /* 採掘速度増加 */
			"schorl", /* 加速 */
			"serpentine", /* 透明化 */
			"olivine", /* EXP増加 */
			"almandine"
			/* ノックバック防止 */ };

	public ItemMagicalPendant(int max) {
		super();
		maxMeta = max;
		this.setMaxStackSize(1);
	}

	@Override
	public int getMaxMeta() {
		return maxMeta;
	}

	@Override
	public String[] getNameSuffix() {
		return names;
	}

	@Override
	public String getTexPath(int meta, boolean f) {
		String s = "items/equip/pendant_" + names[meta];
		if (f) {
			s = "textures/" + s;
		}
		return ClimateCore.PACKAGE_ID + ":" + s;
	}

	@Override
	public CharmType getType(int meta) {
		switch (meta) {
		case 0:
		case 1:
		case 2:
			return CharmType.DEFFENCE;
		case 7:
		case 9:
			return CharmType.SPECIAL;
		case 8:
			return CharmType.TOOL;
		case 12:
			return CharmType.ATTACK;
		default:
			return CharmType.CONSTANT;
		}
	}

	@Override
	public float reduceDamage(DamageSource source, ItemStack charm) {
		int meta = charm.getMetadata();
		switch (meta) {
		case 0:
			return source == DamageSourceClimate.climateColdDamage ? 2.0F : 0F;
		case 1:
			return source == DamageSourceClimate.climateHeatDamage ? 2.0F : 0F;
		case 2:
			return 1.0F;
		case 10:
			return source == DamageSource.lightningBolt ? 5.0F : 0F;
		default:
			return 0F;
		}
	}

	@Override
	public float increaceDamage(EntityLivingBase target, ItemStack charm) {
		return 1.0F;
	}

	@Override
	public boolean onAttacking(EntityPlayer player, EntityLivingBase target, DamageSource source, float damage,
			ItemStack charm) {
		int meta = charm.getMetadata();
		if (meta == 12 && target != null && !player.worldObj.isRemote) {
			int r = 2 + itemRand.nextInt(3);
			EntityXPOrb orb = new EntityXPOrb(player.worldObj, target.posX, target.posY, target.posZ, r);
			player.worldObj.spawnEntityInWorld(orb);
		}
		return false;
	}

	@Override
	public boolean onDiffence(DamageSource source, EntityLivingBase target, float damage, ItemStack charm) {
		return false;
	}

	@Override
	public boolean onToolUsing(EntityPlayer player, BlockPos pos, IBlockState state, ItemStack charm) {
		int meta = charm.getMetadata();
		if (player.isSneaking() && !player.worldObj.isRemote && state != null) {
			if (meta == 8) {
				AxisAlignedBB aabb = new AxisAlignedBB((double) pos.getX() - 5, (double) pos.getY() - 2,
						(double) pos.getZ() - 5, (double) pos.getX() + 5, (double) pos.getY() + 3,
						(double) pos.getZ() + 5);
				List<EntityItem> drops = player.worldObj.getEntitiesWithinAABB(EntityItem.class, aabb);
				for (EntityItem drop : drops) {
					drop.setPosition(player.posX, player.posY + 0.5D, player.posZ);
				}
			}
		}
		return false;
	}

	@Override
	public void constantEffect(EntityPlayer player, ItemStack charm) {
		int meta = charm.getMetadata();

		if (meta == 3) {
			List<PotionEffect> removes = new ArrayList<PotionEffect>();
			Collection<PotionEffect> target = player.getActivePotionEffects();
			for (PotionEffect check : target) {
				Potion p = check.getPotion();
				if (p != null && p.isBadEffect()) {
					removes.add(check);
				}
			}
			for (PotionEffect ret : removes) {
				player.removePotionEffect(ret.getPotion());
			}
		}

		PotionEffect eff = null;
		switch (meta) {
		case 0:
			eff = new PotionEffect(DCPotion.water_breath, 205, 0);
			break;
		case 1:
			eff = new PotionEffect(DCPotion.fire_reg, 205, 0);
			break;
		case 5:
			eff = new PotionEffect(DCPotion.night_vision, 605, 0);
			break;
		case 6:
			eff = new PotionEffect(DCPotion.jump, 205, 0);
			break;
		case 10:
			eff = new PotionEffect(DCPotion.speed, 205, 0);
			break;
		case 11:
			eff = new PotionEffect(DCPotion.invisible, 205, 0);
			break;
		case 13:
			eff = new PotionEffect(MainInit.heavyboots, 205, 1);
		}

		if (eff != null) {
			player.addPotionEffect(eff);
		}
	}

	@Override
	public boolean onUsing(EntityPlayer player, ItemStack charm) {
		return false;
	}

	@Override
	public boolean isActive(ItemStack charm) {
		return true;
	}

	@Override
	public void setActive(ItemStack charm, boolean flag) {}

	@Override
	public ItemStack consumeCharmItem(ItemStack stack) {
		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
		String s = "";
		int meta = stack.getMetadata();
		if (ClimateCore.proxy.isShiftKeyDown()) {
			tooltip.add(TextFormatting.YELLOW.toString() + I18n.translateToLocal("dcs.comment.pendant." + meta));
		} else {
			tooltip.add(TextFormatting.YELLOW.toString() + I18n.translateToLocal("dcs.tip.pendant." + meta));
			tooltip.add(TextFormatting.RESET.toString() + I18n.translateToLocal("dcs.tip.shift"));
		}
	}
}
