package com.github.mrstop.stdemo.client.gui;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.inventory.ContainerMetalFurnace;
import com.github.mrstop.stdemo.tileentity.TileEntityMetalFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GUIMetalFurnace extends GuiContainer {
    private static final ResourceLocation metalFurnaceGuiTexture = new ResourceLocation("stdemo:textures/gui/container/gui_metal_furnace.png");
    private TileEntityMetalFurnace tileEntityMetalFurnace;
    private String metalFurnaceCustomName;

    public GUIMetalFurnace(InventoryPlayer inventoryPlayer, TileEntityMetalFurnace tileEntityMetalFurnace) {
        super(new ContainerMetalFurnace(inventoryPlayer, tileEntityMetalFurnace));
        this.tileEntityMetalFurnace = tileEntityMetalFurnace;
        this.metalFurnaceCustomName = this.tileEntityMetalFurnace.isCustomInventoryName()
                ? this.tileEntityMetalFurnace.getInventoryName()
                : I18n.format(this.tileEntityMetalFurnace.getInventoryName());
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        int x = (this.xSize - this.fontRendererObj.getStringWidth(this.metalFurnaceCustomName)) / 2;
        this.fontRendererObj.drawString(this.metalFurnaceCustomName, x, 5, STDemo.GUICustomNameColor);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(metalFurnaceGuiTexture);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

        if (this.tileEntityMetalFurnace.isBurning())
        {
            int i;
            i = this.tileEntityMetalFurnace.getBurnTimeRemainingScaled(12);
//            this.drawTexturedModalRect(x + 56, y + 36 + 12 - i, 176, 12 - i, 14, i + 2);
            i = this.tileEntityMetalFurnace.getCookProgressScaled(24);
            System.out.print(i + "\n");
            this.drawTexturedModalRect(x + 80, y + 35, 176, 14, 20 + i, 17);
        }
    }
}
