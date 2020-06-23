package defeatedcrow.hac.machine.gui;

import java.util.ArrayList;

import defeatedcrow.hac.machine.block.TilePressMachine;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPressMachine extends GuiContainer {
	private static final ResourceLocation guiTex = new ResourceLocation("dcs_climate",
			"textures/gui/press_machine_gui.png");
	/** The player inventory bound to this GUI. */
	private final InventoryPlayer playerInventory;
	private final TilePressMachine machine;

	public GuiPressMachine(TilePressMachine te, InventoryPlayer playerInv) {
		super(new ContainerPressMachine(te, playerInv));
		this.playerInventory = playerInv;
		this.machine = te;
		this.ySize = 186;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = I18n.translateToLocal(this.machine.getName());
		this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 4, 4210752);
		this.fontRenderer.drawString(this.playerInventory.getDisplayName()
				.getUnformattedText(), 8, this.ySize - 92, 4210752);
	}

	@Override
	public void drawScreen(int x, int y, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(x, y, partialTicks);
		if (isPointInRegion(41, 12, 58, 58, x, y)) {
			ArrayList<String> list1 = new ArrayList<String>();
			list1.add("Required Items");
			this.drawHoveringText(list1, x, y - 12, fontRenderer);
		}
		this.renderHoveredToolTip(x, y);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(guiTex());
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		int l = this.getCookProgressScaled(20);
		this.drawTexturedModalRect(i + 110, j + 26, 176, 0, l, 12);
	}

	private int getCookProgressScaled(int pixels) {
		int i = this.machine.getField(0);
		int j = this.machine.MAX_PROGRESS_TIME;
		return i != 0 && j != 0 ? i * pixels / j : 0;
	}

	protected static ResourceLocation guiTex() {
		return guiTex;
	}
}
