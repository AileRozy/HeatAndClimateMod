package defeatedcrow.hac.main.item.tool;

import java.util.List;
import java.util.Random;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import defeatedcrow.hac.core.base.ITexturePath;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStem;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemScytheDC extends ItemSword implements ITexturePath {

	private final float attackDam;
	private final String tex;
	public int range;

	public ItemScytheDC(ToolMaterial m, String t) {
		super(m);
		tex = t;
		this.attackDam = 3.0F + m.getAttackDamage();
		this.range = m.getHarvestLevel();
	}

	@Override
	public String getTexPath(int meta, boolean isFull) {
		return "dcs_climate:items/tool/scythe_" + tex;
	}

	@Override
	public boolean canHarvestBlock(IBlockState blockIn) {
		return true;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		stack.damageItem(1, attacker);
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return true;
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

		if (slot == EntityEquipmentSlot.MAINHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER,
					"Weapon modifier", this.attackDam, 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER,
					"Weapon modifier", -2.5D, 0));
		}

		return multimap;
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		Block block = state.getBlock();
		Material material = state.getMaterial();
		return material != Material.PLANTS && material != Material.VINE && material != Material.CORAL && material != Material.LEAVES && material != Material.GOURD ?
				1.0F : 15.0F;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos,
			EntityLivingBase living) {
		// 範囲収穫
		int area = this.range + 1;

		for (int x = -area + 1; x < area; x++) {
			for (int z = -area + 1; z < area; z++) {
				for (int y = -1; y < 2; y++) {
					BlockPos p1 = pos.add(x, y, z);
					IBlockState target = world.getBlockState(p1);
					ItemStack tool = stack.copy();
					int fl = EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.FORTUNE, tool);
					if (living.isSneaking()) {
						tool = new ItemStack(Items.SHEARS);
					}

					if (getDestroySpeed(stack, target) >= 5.0F && !(target.getBlock() instanceof BlockStem)) {

						boolean flag = true;
						if (target.getBlock() instanceof IGrowable) {
							flag = ((IGrowable) target.getBlock()).canGrow(world, p1, target, false);
						}

						if (target.getBlock() instanceof IShearable) {
							flag = !((IShearable) target.getBlock()).isShearable(tool, world, p1);
						}

						if (target.getBlock() == Blocks.PUMPKIN || target.getBlock() == Blocks.MELON_BLOCK) {
							flag = false;
						}

						if (target.getBlock() == Blocks.REEDS) {
							IBlockState under = world.getBlockState(p1.down());
							if (under.getBlock() == Blocks.REEDS) {
								flag = false;
							}
						}

						if (!flag) {
							if (target.getBlock() instanceof IShearable) {
								List<ItemStack> drops = ((IShearable) target.getBlock()).onSheared(tool, world, p1, fl);
								if (!world.isRemote) {
									for (ItemStack i : drops) {
										EntityItem entityitem = new EntityItem(world, p1.getX() + 0.5D, p1
												.getY() + 0.5D, p1.getZ() + 0.5D, i);
										entityitem.setDefaultPickupDelay();
										world.spawnEntity(entityitem);
									}
								}
								target.getBlock().removedByPlayer(target, world, p1, null, false);
							} else if (living != null && living instanceof EntityPlayer) {
								target.getBlock().harvestBlock(world, (EntityPlayer) living, p1, target, null, tool);
								target.getBlock().removedByPlayer(target, world, p1, null, false);
							} else {
								target.getBlock().dropBlockAsItem(world, p1, target, 0);
								target.getBlock().removedByPlayer(target, world, p1, null, false);
							}
						}
					}
				}
			}
		}
		// block.removedByPlayerをつかう
		if (state.getBlockHardness(world, pos) != 0.0D) {
			stack.damageItem(1, living);
		}

		return true;
	}

	// 毛刈り
	@Override
	public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity,
			EnumHand hand) {
		if (entity == null || entity.world.isRemote) {
			return false;
		}
		if (player.isSneaking() && entity instanceof IShearable) {
			IShearable target = (IShearable) entity;
			BlockPos pos = new BlockPos(entity.posX, entity.posY, entity.posZ);
			if (target.isShearable(itemstack, entity.world, pos)) {
				List<ItemStack> drops = target.onSheared(itemstack, entity.world, pos, EnchantmentHelper
						.getEnchantmentLevel(Enchantments.FORTUNE, itemstack));

				Random rand = Item.itemRand;
				for (ItemStack stack : drops) {
					EntityItem ent = entity.entityDropItem(stack, 1.0F);
					ent.motionY += rand.nextFloat() * 0.05F;
					ent.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
					ent.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
				}
				player.world.playSound(player, entity
						.getPosition(), SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.PLAYERS, 1.5F, 1.5F / (player.world.rand
								.nextFloat() * 0.4F + 1.2F) + 0.5F);
				itemstack.damageItem(1, entity);
			}
			return true;
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
		tooltip.add("Destroy the surrounding grass and leaves.");
		tooltip.add("Range: " + (this.range + 1));
		tooltip.add("During sneaking, this will work as scissors.");
	}
}
