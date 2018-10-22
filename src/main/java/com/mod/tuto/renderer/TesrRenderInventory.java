package com.mod.tuto.renderer;

import org.lwjgl.opengl.GL11;

import com.mod.tuto.init.BlockMod;
import com.mod.tuto.proxy.ClientProxy;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class TesrRenderInventory implements ISimpleBlockRenderingHandler
{
    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
    {
        if(block == BlockMod.blockTesr && metadata == 0)
        {
            GL11.glPushMatrix();
            GL11.glRotatef(180F, 0F, 0F, 1F);
            GL11.glTranslatef(0F, -1F, 0F);
            Minecraft.getMinecraft().getTextureManager().bindTexture(TileEntityBlockTesrSpecialRenderer.texture);
            TileEntityBlockTesrSpecialRenderer.model.renderAll();
            GL11.glPopMatrix();
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId)
    {
        return true;
    }

    @Override
    public int getRenderId()
    {
        return ClientProxy.renderTesr;
    }
}