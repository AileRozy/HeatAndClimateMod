package defeatedcrow.hac.food.entity;

import javax.annotation.Nullable;

import defeatedcrow.hac.core.base.FoodEntityBase;
import defeatedcrow.hac.food.FoodInit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PieSugarEntity extends FoodEntityBase {

	public PieSugarEntity(World worldIn) {
		super(worldIn);
		this.setSize(0.3F, 0.15F);
	}

	public PieSugarEntity(World worldIn, double posX, double posY, double posZ) {
		super(worldIn, posX, posY, posZ);
		this.setSize(0.3F, 0.15F);
	}

	public PieSugarEntity(World worldIn, double posX, double posY, double posZ, @Nullable EntityPlayer player) {
		super(worldIn, posX, posY, posZ, player);
		this.setSize(0.3F, 0.15F);
	}

	@Override
	protected ItemStack[] drops() {
		return new ItemStack[] {
			new ItemStack(FoodInit.pastrySquare, 1, 0),
			new ItemStack(FoodInit.pastrySquare, 1, 1) };
	}
}
