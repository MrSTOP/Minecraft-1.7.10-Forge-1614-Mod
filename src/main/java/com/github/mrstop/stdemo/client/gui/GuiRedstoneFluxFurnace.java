package com.github.mrstop.stdemo.client.gui;

import com.github.mrstop.stdemo.inventory.ContainerRedstoneFluxFurnace;
import com.github.mrstop.stdemo.tileentity.TileEntityRedstoneFluxFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiRedstoneFluxFurnace extends GuiContainer {

    private static final ResourceLocation redstoneFluxFurnaceGuiTexture = new ResourceLocation("stdemo:textures/gui/container/redstone_flux_furnace.png");
    private TileEntityRedstoneFluxFurnace tileEntityRedstoneFluxFurnace;

    public GuiRedstoneFluxFurnace(InventoryPlayer inventoryPlayer, TileEntityRedstoneFluxFurnace tileEntityRedstoneFluxFurnaceConstruct)
    {
        super(new ContainerRedstoneFluxFurnace(inventoryPlayer, tileEntityRedstoneFluxFurnaceConstruct));
        this.tileEntityRedstoneFluxFurnace = tileEntityRedstoneFluxFurnaceConstruct;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(redstoneFluxFurnaceGuiTexture);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

        int i;

        /*if (this.tileEntityRedstoneFluxFurnace.isBurning())
        {
            i = this.tileEntityRedstoneFluxFurnace.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(x + 56, y + 36 + 12 - i, 176, 12 - i, 14, i + 2);
            i = this.tileEntityRedstoneFluxFurnace.getCookProgressScaled(24);
            this.drawTexturedModalRect(x + 79, y + 34, 176, 14, i + 1, 16);
        }*/
    }
}
