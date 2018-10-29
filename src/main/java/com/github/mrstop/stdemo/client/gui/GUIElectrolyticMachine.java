package com.github.mrstop.stdemo.client.gui;

import com.github.mrstop.stdemo.inventory.ContainerElectrolyticMachine;
import com.github.mrstop.stdemo.tileentity.TileEntityElectrolyticMachine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;

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
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        //绘制能量条
        int i = this.tileEntityElectrolyticMachine.getEnergyScale(60);
        this.drawTexturedModalRect(this.guiLeft + 12, this.guiTop + 73 - i, 176, 60 - i, 16, i);

        //绘制流体槽*******************************************************************
        i = this.tileEntityElectrolyticMachine.getFluidScale(60);
        //获取以及绑定材质
        //*******************************************
        IIcon icon = null;
        if (FluidRegistry.getFluid(this.tileEntityElectrolyticMachine.GUIFluidID) != null){
            icon = FluidRegistry.getFluid(this.tileEntityElectrolyticMachine.GUIFluidID).getIcon();
        }
        if (icon == null) {
            icon = ((TextureMap) Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture)).getAtlasSprite("missingno");
        }
        this.mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        //*******************************************

        //绘制流体材质
        ///////////////////////////////////////////////////////////////
        Tessellator tessellator = Tessellator.instance;
        double minU = icon.getInterpolatedU(0);
        double maxU = icon.getInterpolatedU(16);
        double minV = icon.getInterpolatedV(0);
        double maxV = icon.getInterpolatedV(16);
        for (int j = 0; j < i / 16; j++) {
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(this.guiLeft + 149 + 16, this.guiTop + 73 - j * 16, zLevel,maxU, minV);
            tessellator.addVertexWithUV(this.guiLeft + 149 + 16, this.guiTop + 73 - 16 - j * 16, zLevel, maxU, maxV);
            tessellator.addVertexWithUV(this.guiLeft + 149 + 00, this.guiTop + 73 - 16 - j * 16, zLevel, minU, maxV);
            tessellator.addVertexWithUV(this.guiLeft + 149 + 00, this.guiTop + 73 - j * 16, zLevel, minU, minV);
            tessellator.draw();
        }
        if (i % 16 != 0){
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(this.guiLeft + 149 + 16, this.guiTop + 73 - (i / 16) * 16, zLevel,maxU, minV);
            tessellator.addVertexWithUV(this.guiLeft + 149 + 16, this.guiTop + 73 - (i / 16) * 16 - (i % 16), zLevel, maxU, icon.getInterpolatedV(i %16));
            tessellator.addVertexWithUV(this.guiLeft + 149 + 00, this.guiTop + 73 - (i / 16) * 16 - (i % 16), zLevel, minU, icon.getInterpolatedV(i %16));
            tessellator.addVertexWithUV(this.guiLeft + 149 + 00, this.guiTop + 73 - (i / 16) * 16, zLevel, minU, minV);
            tessellator.draw();
        }
        ///////////////////////////////////////////////////////////////
        //绘制流体槽结束***************************************************************
        mc.renderEngine.bindTexture(electrolyticMachineGuiTexture);
        this.drawTexturedModalRect(this.guiLeft + 149, this.guiTop + 13, 192, 0, 16, 60);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }
}
