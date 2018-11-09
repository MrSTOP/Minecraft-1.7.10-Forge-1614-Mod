package com.github.mrstop.stdemo.client.gui;

import com.github.mrstop.stdemo.inventory.ContainerMachineRedstoneFluxFurnace;
import com.github.mrstop.stdemo.tileentity.TileEntityMachineRedstoneFluxFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GUIMachineRedstoneFluxFurnace extends GuiContainer {

    private static final ResourceLocation redstoneFluxFurnaceGuiTexture = new ResourceLocation("stdemo:textures/gui/container/gui_redstone_flux_furnace.png");
    private TileEntityMachineRedstoneFluxFurnace tileEntityMachineRedstoneFluxFurnace;

    public GUIMachineRedstoneFluxFurnace(InventoryPlayer inventoryPlayer, TileEntityMachineRedstoneFluxFurnace tileEntityMachineRedstoneFluxFurnace) {
        super(new ContainerMachineRedstoneFluxFurnace(inventoryPlayer, tileEntityMachineRedstoneFluxFurnace));
        this.tileEntityMachineRedstoneFluxFurnace = tileEntityMachineRedstoneFluxFurnace;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(redstoneFluxFurnaceGuiTexture);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

        if (this.tileEntityMachineRedstoneFluxFurnace.isCooking())
        {
            int i = this.tileEntityMachineRedstoneFluxFurnace.getCookProgressScaled(24);
            this.drawTexturedModalRect(x + 79, y + 34, 176, 14, i + 1, 16);
        }
    }
}
