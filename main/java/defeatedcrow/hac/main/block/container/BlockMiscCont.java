package defeatedcrow.hac.main.block.container;

import java.util.ArrayList;
import java.util.List;

import defeatedcrow.hac.api.placeable.IRapidCollectables;
import defeatedcrow.hac.core.base.DCSimpleBlock;
import defeatedcrow.hac.core.base.ITexturePath;
import defeatedcrow.hac.core.util.DCUtil;
import defeatedcrow.hac.main.api.ICompressionRecipe;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMiscCont extends DCSimpleBlock implements ITexturePath, IRapidCollectables, ICompressionRecipe {

	public BlockMiscCont(Material m, String s, int max) {
		super(m, s, max, false);
		this.setTickRandomly(true);
		this.setHardness(0.5F);
		this.setResistance(5.0F);
	}

	@Override
	public String[] getNameSuffix() {
		String[] name = { "clay", "fish", "leather", "fur", "feather" };
		return name;
	}

	@Override
	public Object getInputDic(int i) {
		switch (i) {
		case 0:
			return new ItemStack(Items.CLAY_BALL);
		case 1:
			return new ItemStack(Items.FISH);
		case 2:
			return "leather";
		case 3:
			return "rabbithide";
		case 4:
			return "feather";
		}
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack getOutputItem(int i) {
		if (i >= 0 && i < containedItem().length) {
			return containedItem()[i];
		}
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack[] containedItem() {
		ItemStack[] ret = new ItemStack[5];
		ret[0] = new ItemStack(Items.CLAY_BALL, 8);
		ret[1] = new ItemStack(Items.FISH, 8);
		ret[2] = new ItemStack(Items.LEATHER, 8);
		ret[3] = new ItemStack(Items.RABBIT_HIDE, 8);
		ret[4] = new ItemStack(Items.FEATHER, 8);

		return ret;
	}

	@Override
	public String getTexture(int meta, int side, boolean face) {
		int m = meta & 15;
		if (m > 4)
			m = 4;
		String b = "dcs_climate:blocks/cont/";
		switch (side) {
		case 0:
			return b + "metalbox_t_" + getNameSuffix()[m];
		case 1:
			return b + "metalbox_b";
		case 2:
		case 3:
		case 4:
		case 5:
			return b + "metalbox_s";
		}
		return super.getTexture(meta, side, face);
	}

	public static List<String> getTexList() {
		List<String> list = new ArrayList<String>();
		String b = "dcs_climate:blocks/cont/metalbox_";
		list.add(b + "t_clay");
		list.add(b + "t_fish");
		list.add(b + "t_leather");
		list.add(b + "t_fur");
		list.add(b + "t_feather");
		return list;
	}

	@Override
	public String getTexPath(int meta, boolean isFull) {
		int m = meta & 15;
		if (m > 4)
			m = 4;
		String b = "dcs_climate:items/block/cont/";
		return b + "metalbox_" + getNameSuffix()[m];
	}

	/* IRapidCollectables */

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
