package defeatedcrow.hac.main.block.ores;

import defeatedcrow.hac.api.climate.DCAirflow;
import defeatedcrow.hac.api.climate.DCHeatTier;
import defeatedcrow.hac.api.climate.DCHumidity;
import defeatedcrow.hac.api.climate.IClimate;
import defeatedcrow.hac.api.placeable.IRapidCollectables;
import defeatedcrow.hac.core.ClimateCore;
import defeatedcrow.hac.core.DCLogger;
import defeatedcrow.hac.core.base.DCSimpleBlock;
import defeatedcrow.hac.core.base.ITexturePath;
import defeatedcrow.hac.core.util.DCUtil;
import defeatedcrow.hac.main.api.IHeatTreatment;
import defeatedcrow.hac.main.api.MainAPIManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDusts2 extends DCSimpleBlock implements ITexturePath, IRapidCollectables {

	public BlockDusts2(Material m, String s, int max) {
		super(m, s, max, true);
		this.setTickRandomly(true);
		this.setHardness(1.5F);
		this.setResistance(15.0F);
	}

	@Override
	public int tickRate(World world) {
		return 40;
	}

	@Override
	public boolean onClimateChange(World world, BlockPos pos, IBlockState state, IClimate clm) {
		if (clm != null) {
			DCHeatTier heat = clm.getHeat();
			DCHumidity hum = clm.getHumidity();
			DCAirflow air = clm.getAirflow();
			int meta = this.damageDropped(state);
			ItemStack check = new ItemStack(this, 1, meta);
			IHeatTreatment recipe = MainAPIManager.heatTreatmentRegister.getRecipe(check);
			if (recipe != null) {
				ItemStack output = recipe.getCurrentOutput(check, clm).getResult();
				if (!DCUtil.isEmpty(output) && output.getItem() instanceof ItemBlock) {
					Block ret = ((ItemBlock) output.getItem()).getBlock();
					IBlockState retS = ret.getStateFromMeta(output.getMetadata());
					if (world.setBlockState(pos, retS, 2)) {
						world.notifyNeighborsOfStateChange(pos, ret, true);

						// 効果音
						if (playSEOnChanging(meta)) {
							world.playSound(null, pos, getSE(meta), SoundCategory.BLOCKS, 0.8F, 2.0F);
							DCLogger.debugLog("Heat Treatment: " + output.getDisplayName());
						}
						return true;
					}
				}
			}
		}
		return super.onClimateChange(world, pos, state, clm);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		super.onBlockAdded(world, pos, state);
		world.scheduleUpdate(pos, this, this.tickRate(world));
	}

	private static String[] names = { "dirt", "crystal", "toolsteel", "mangalloy" };

	@Override
	public String[] getNameSuffix() {
		return names;
	}

	@Override
	public String getTexPath(int meta, boolean f) {
		if (meta >= names.length) {
			meta = names.length - 1;
		}
		String s = "blocks/ores/dustblock_" + names[meta];
		if (f) {
			s = "textures/" + s;
		}
		return ClimateCore.PACKAGE_ID + ":" + s;
	}

	/* IRapidCollectables */

	@Override
	public String getCollectableTool() {
		return "shovel";
	}

	@Override
	public boolean isCollectable(ItemStack item) {
		return !DCUtil.isEmpty(item) && item.getItem() instanceof ItemSpade;
	}

	@Override
	public int getCollectArea(ItemStack item) {
		return 1;
	}

	@Override
	public boolean doCollect(World world, BlockPos pos, IBlockState state, EntityPlayer player, ItemStack tool) {
		NonNullList<ItemStack> list = NonNullList.create();
		this.getDrops(list, world, pos, state, 0);
		for (ItemStack item : list) {
			double x = player.posX;
			double y = player.posY + 0.25D;
			double z = player.posZ;
			EntityItem drop = new EntityItem(world, x, y, z, item);
			world.spawnEntity(drop);
		}
		world.setBlockToAir(pos);
		return true;
	}
}
