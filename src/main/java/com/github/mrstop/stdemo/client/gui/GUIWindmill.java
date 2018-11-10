package com.github.mrstop.stdemo.client.gui;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.inventory.ContainerWindmill;
import com.github.mrstop.stdemo.tileentity.TileEntityWindmill;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GUIWindmill extends GuiContainer {
    private static final ResourceLocation windmillGuiTexture = new ResourceLocation("stdemo:textures/gui/container/gui_windmill.png");
    private  TileEntityWindmill tileEntityWindmill;
    private String windmillCustomName;

    public GUIWindmill(InventoryPlayer inventoryPlayer, TileEntityWindmill tileEntityWindmill) {
        super(new ContainerWindmill(inventoryPlayer, tileEntityWindmill));
        this.tileEntityWindmill = tileEntityWindmill;
        this.windmillCustomName = this.tileEntityWindmill.isCustomInventoryName()
                ? this.tileEntityWindmill.getInventoryName()
                : I18n.format(this.tileEntityWindmill.getInventoryName());
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        int x = (this.xSize - this.fontRendererObj.getStringWidth(this.windmillCustomName)) / 2;
        this.fontRendererObj.drawString(this.windmillCustomName, x, 5, STDemo.GUICustomNameColor);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(windmillGuiTexture);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
        int scale = this.tileEntityWindmill.getPowerScale(50);
        this.drawTexturedModalRect(x + 80, y + 17 + 50 - scale, 176, 50 - scale, 18, scale);
    }
}
