package com.github.mrstop.stdemo.client.gui;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.client.utils.GUIHelper;
import com.github.mrstop.stdemo.inventory.ContainerMachineElectrolyticMachine;
import com.github.mrstop.stdemo.tileentity.TileEntityMachineElectrolyticMachine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;

public class GUIMachineElectrolyticMachine extends GuiContainer {
    private static final ResourceLocation electrolyticMachineGuiTexture = new ResourceLocation("stdemo:textures/gui/container/gui_electrolytic_machine.png");
    private TileEntityMachineElectrolyticMachine tileEntityMachineElectrolyticMachine;
    private String machineElectrolyticMachineCustomName;

    public GUIMachineElectrolyticMachine(InventoryPlayer inventoryPlayer, TileEntityMachineElectrolyticMachine tileEntityMachineElectrolyticMachine) {
        super(new ContainerMachineElectrolyticMachine(inventoryPlayer, tileEntityMachineElectrolyticMachine));
        this.tileEntityMachineElectrolyticMachine = tileEntityMachineElectrolyticMachine;
        this.machineElectrolyticMachineCustomName = this.tileEntityMachineElectrolyticMachine.isCustomInventoryName()
                ? this.tileEntityMachineElectrolyticMachine.getInventoryName()
                : I18n.format(this.tileEntityMachineElectrolyticMachine.getInventoryName());
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        int x = (this.xSize - this.fontRendererObj.getStringWidth(this.machineElectrolyticMachineCustomName)) / 2;
        this.fontRendererObj.drawString(this.machineElectrolyticMachineCustomName, x, 5, STDemo.GUICustomNameColor);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(electrolyticMachineGuiTexture);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        //绘制能量条
        int scale = this.tileEntityMachineElectrolyticMachine.getEnergyScale(60);
        this.drawTexturedModalRect(this.guiLeft + 12, this.guiTop + 73 - scale, 176, 60 - scale, 16, scale);

        //绘制流体槽*******************************************************************
        scale = this.tileEntityMachineElectrolyticMachine.getFluidScale(60);
        //获取以及绑定材质
        //++++++++++++++++++++++++++++++++++++++++++
        IIcon icon = null;
        if (FluidRegistry.getFluid(this.tileEntityMachineElectrolyticMachine.GUIFluidID) != null){
            icon = FluidRegistry.getFluid(this.tileEntityMachineElectrolyticMachine.GUIFluidID).getIcon();
        }
        if (icon == null) {
            icon = ((TextureMap) Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture)).getAtlasSprite("missingno");
        }
        this.mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        //++++++++++++++++++++++++++++++++++++++++++

        //绘制流体材质
        GUIHelper.drawGUIFluid(icon, this.guiLeft, this.guiTop, 149, 73, scale, this.zLevel);
        //绘制流体槽结束***************************************************************
        mc.renderEngine.bindTexture(electrolyticMachineGuiTexture);
        this.drawTexturedModalRect(this.guiLeft + 149, this.guiTop + 13, 192, 0, 16, 60);
    }
}
