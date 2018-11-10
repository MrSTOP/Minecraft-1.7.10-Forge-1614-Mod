package com.github.mrstop.stdemo.client.gui;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.inventory.ContainerMachineCalciner;
import com.github.mrstop.stdemo.tileentity.TileEntityMachineCalciner;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineCalciner extends GuiContainer {
    private static final ResourceLocation machineCalcinerTexture = new ResourceLocation("stdemo:textures/gui/container/gui_machine_calciner.png");
    private TileEntityMachineCalciner tileEntityMachineCalciner;
    private String machineCalcinerCustomName;

    public GUIMachineCalciner(InventoryPlayer inventoryPlayer, TileEntityMachineCalciner tileEntityMachineCalciner) {
        super(new ContainerMachineCalciner(inventoryPlayer, tileEntityMachineCalciner));
        this.tileEntityMachineCalciner = tileEntityMachineCalciner;
        this.machineCalcinerCustomName = this.tileEntityMachineCalciner.isCustomInventoryName()
                ? this.tileEntityMachineCalciner.getInventoryName()
                : I18n.format(this.tileEntityMachineCalciner.getInventoryName());
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        int x = (this.xSize - this.fontRendererObj.getStringWidth(this.machineCalcinerCustomName)) / 2;
        this.fontRendererObj.drawString(this.machineCalcinerCustomName, x, 5, STDemo.GUICustomNameColor);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(machineCalcinerTexture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }
}
