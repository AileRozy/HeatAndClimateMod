package defeatedcrow.hac.food.entity;

import javax.annotation.Nullable;

import defeatedcrow.hac.core.base.FoodEntityBase;
import defeatedcrow.hac.food.FoodInit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DishTacoriceEntity extends FoodEntityBase {

	public DishTacoriceEntity(World worldIn) {
		super(worldIn);
	}

	public DishTacoriceEntity(World worldIn, double posX, double posY, double posZ) {
		super(worldIn, posX, posY, posZ);
	}

	public DishTacoriceEntity(World worldIn, double posX, double posY, double posZ, @Nullable EntityPlayer player) {
		super(worldIn, posX, posY, posZ, player);
	}

	@Override
	protected ItemStack[] drops() {
		return new ItemStack[] { new ItemStack(FoodInit.dishBig, 1, 3), new ItemStack(FoodInit.dishBig, 1, 3) };
	}
}
