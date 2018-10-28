package com.github.mrstop.stdemo.client.gui;

import com.github.mrstop.stdemo.inventory.ContainerElectrolyticMachine;
import com.github.mrstop.stdemo.tileentity.TileEntityElectrolyticMachine;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIElectrolyticMachine extends GuiContainer {
    private static final ResourceLocation electrolyticMachineGuiTexture = new ResourceLocation("stdemo:textures/gui/container/gui_electrolytic_machine.png");
    private TileEntityElectrolyticMachine tileEntityElectrolyticMachine;

    public GUIElectrolyticMachine(InventoryPlayer inventoryPlayer, TileEntityElectrolyticMachine tileEntityElectrolyticMachine) {
        super(new ContainerElectrolyticMachine(inventoryPlayer, tileEntityElectrolyticMachine));
        this.tileEntityElectrolyticMachine = tileEntityElectrolyticMachine;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(electrolyticMachineGuiTexture);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
        int i = this.tileEntityElectrolyticMachine.getEnergyScale(60);
        this.drawTexturedModalRect(x + 12, y + 73, 176, 60, 16, i);
        i = this.tileEntityElectrolyticMachine.getFluidScale(60);
        System.out.print(i + "<<\n");
        this.drawTexturedModalRect(x + 149, y + 73 - i, 176, 0, 16, i);
    }
}
