package com.mod.tuto.renderer;

import org.lwjgl.opengl.GL11;

import com.mod.tuto.Reference;
import com.mod.tuto.models.ModelBlockTesr;
import com.mod.tuto.tileentity.TileEntityBlockTesr;

import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;


public class TileEntityBlockTesrSpecialRenderer extends TileEntitySpecialRenderer
{
    public static ModelBlockTesr model = new ModelBlockTesr();
    public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/models/blocks/blockTesr.png");
    
    public void renderTileEntity()
    {
        this.func_147497_a(TileEntityRendererDispatcher.instance);
    }
    
    @Override
    public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float partialRenderTick)
    {
        this.renderTileEntityBlockTesrAt((TileEntityBlockTesr)entity, x, y, z, partialRenderTick);
    }
    
    private void renderTileEntityBlockTesrAt(TileEntityBlockTesr tile, double x, double y, double z, float partialRenderTick)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y + 1.5D, z + 0.5D);
        GL11.glRotatef(180F, 0F, 0F, 1F);
        GL11.glRotatef(90F * tile.getDirection(), 0F, 1F, 0F);
        this.bindTexture(texture);
        model.renderAll();
        GL11.glPopMatrix();
    }
}