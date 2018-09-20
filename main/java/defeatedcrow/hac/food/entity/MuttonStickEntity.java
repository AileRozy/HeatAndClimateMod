package defeatedcrow.hac.food.entity;

import javax.annotation.Nullable;

import defeatedcrow.hac.core.base.FoodEntityBase;
import defeatedcrow.hac.food.FoodInit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MuttonStickEntity extends FoodEntityBase {

	public MuttonStickEntity(World worldIn) {
		super(worldIn);
		this.setSize(0.375F, 0.75F);
	}

	public MuttonStickEntity(World worldIn, double posX, double posY, double posZ) {
		super(worldIn, posX, posY, posZ);
		this.setSize(0.375F, 0.75F);
	}

	public MuttonStickEntity(World worldIn, double posX, double posY, double posZ, @Nullable EntityPlayer player) {
		super(worldIn, posX, posY, posZ, player);
		this.setSize(0.375F, 0.75F);
	}

	@Override
	protected ItemStack[] drops() {
		return new ItemStack[] {
				new ItemStack(FoodInit.sticks, 1, 8),
				new ItemStack(FoodInit.sticks, 1, 9)
		};
	}
}
