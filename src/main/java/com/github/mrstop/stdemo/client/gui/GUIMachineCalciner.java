package com.github.mrstop.stdemo.client.gui;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.client.utils.GUIHelper;
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
        int scale = 0;
        this.mc.getTextureManager().bindTexture(machineCalcinerTexture);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        scale = this.tileEntityMachineCalciner.getProcessTimeScale(19);
        this.drawTexturedModalRect(this.guiLeft + 79, this.guiTop + 34, 176, 60, scale, 12);

        //绘制能量条
        scale = this.tileEntityMachineCalciner.getEnergyScale(60);
        this.drawTexturedModalRect(this.guiLeft + 12, this.guiTop + 73 - scale, 176, 60 - scale, 16, scale);

        //绘制流体槽*******************************************************************
        scale = this.tileEntityMachineCalciner.getFluidScale(60);
        //绘制流体材质
        GUIHelper.drawGUIFluid(this.mc.renderEngine, this.tileEntityMachineCalciner.GUIFluidID, this.guiLeft, this.guiTop, 149, 73, scale, this.zLevel);
        mc.renderEngine.bindTexture(machineCalcinerTexture);
        //绘制流体槽结束***************************************************************
        this.drawTexturedModalRect(this.guiLeft + 149, this.guiTop + 13, 192, 0, 16, 60);
    }
}
