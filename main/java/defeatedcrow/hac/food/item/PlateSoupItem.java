package defeatedcrow.hac.food.item;

import java.util.List;

import javax.annotation.Nullable;

import defeatedcrow.hac.core.ClimateCore;
import defeatedcrow.hac.core.DCLogger;
import defeatedcrow.hac.core.base.FoodEntityBase;
import defeatedcrow.hac.core.base.FoodItemBase;
import defeatedcrow.hac.food.FoodInit;
import defeatedcrow.hac.food.entity.PlateGratinEntity;
import defeatedcrow.hac.food.entity.PlateGyozaEntity;
import defeatedcrow.hac.food.entity.PlateMeatPaellaEntity;
import defeatedcrow.hac.food.entity.PlatePaellaEntity;
import defeatedcrow.hac.food.entity.PlatePotatoEntity;
import defeatedcrow.hac.food.entity.PlateSoupEntity;
import defeatedcrow.hac.main.util.DCName;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlateSoupItem extends FoodItemBase {

	public PlateSoupItem(boolean isWolfFood) {
		super(isWolfFood);
		this.setContainerItem(FoodInit.steakplate);
	}

	@Override
	public int getMaxMeta() {
		return 11;
	}

	@Override
	public String getTexPath(int meta, boolean f) {
		int i = MathHelper.clamp(0, meta, 11);
		String s = "items/food/plate_" + this.getNameSuffix()[i];
		if (f) {
			s = "textures/" + s;
		}
		return ClimateCore.PACKAGE_ID + ":" + s;
	}

	@Override
	public String[] getNameSuffix() {
		String[] s = {
			"potato_raw",
			"potato_baked",
			"tomato_raw",
			"tomato_baked",
			"gratin_raw",
			"gratin_baked",
			"paella_raw",
			"paella_baked",
			"meat_paella_raw",
			"meat_paella_baked",
			"gyoza_raw",
			"gyoza_baked" };
		return s;
	}

	@Override
	public Entity getPlacementEntity(World world, EntityPlayer player, double x, double y, double z, ItemStack item) {
		int i = item.getMetadata();
		FoodEntityBase ret = new PlatePotatoEntity(world, x, y, z, player);
		if (i == 2 || i == 3) {
			ret = new PlateSoupEntity(world, x, y, z, player);
		}

		if (i == 4 || i == 5) {
			ret = new PlateGratinEntity(world, x, y, z, player);
		}
		if (i == 6 || i == 7) {
			ret = new PlatePaellaEntity(world, x, y, z, player);
		}
		if (i == 8 || i == 9) {
			ret = new PlateMeatPaellaEntity(world, x, y, z, player);
		}
		if (i == 10 || i == 11) {
			ret = new PlateGyozaEntity(world, x, y, z, player);
		}

		if ((i & 1) == 0) {
			ret.setRAW(true);
		}
		ret.setIndividual(world.rand.nextInt(32));
		DCLogger.debugLog("individual " + ret.getIndividual());
		return ret;
	}

	@Override
	public int getFoodAmo(int meta) {
		switch (meta) {
		case 0:
		case 2:
			return 0;
		case 1:
		case 3:
			return 12;
		case 5:
			return 10;
		case 7:
		case 9:
		case 11:
			return 16;
		}
		return 0;
	}

	@Override
	public float getSaturation(int meta) {
		return (meta & 1) == 0 ? 0F : 0.5F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation2(ItemStack stack, @Nullable World world, List<String> tooltip) {
		tooltip.add(DCName.PLACEABLE_ENTITY.getLocalizedName());
	}

}
