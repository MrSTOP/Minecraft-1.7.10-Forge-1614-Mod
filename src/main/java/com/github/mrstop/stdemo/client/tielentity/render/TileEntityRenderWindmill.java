package com.github.mrstop.stdemo.client.tielentity.render;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.tileentity.TileEntityWindmill;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TileEntityRenderWindmill extends TileEntitySpecialRenderer {

    private final ResourceLocation textureWindmill = new ResourceLocation(STDemo.MODID, "textures/models/windmill.png");
    private int textureWidth = 64;
    private int textuerHeight = 32;

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float p_147500_8_) {
        int metadata = tileEntity.getBlockMetadata();
        if (metadata < 4){
            return;
        }
        GL11.glPushMatrix();
        {
            GL11.glDisable(GL11.GL_LIGHTING);
            int r = ((TileEntityWindmill)tileEntity).getRotation();
            Tessellator tessellator = Tessellator.instance;
            GL11.glTranslatef((float) x, (float) y, (float) z);
            this.bindTexture(textureWindmill);
            tessellator.setColorOpaque_F(1F, 1F, 1F);
            tessellator.setBrightness(tileEntity.getBlockType().getMixedBrightnessForBlock(tileEntity.getWorld(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord));
            tessellator.startDrawingQuads();
            {
                switch (metadata){
                    case 4:
                        GL11.glTranslatef(0.5F, 0.5F, 0.0F);
                        GL11.glRotated(r, 0, 0, 1);
                        GL11.glTranslatef(-0.5F, -0.5F, -0.0F);
                        tessellator.addVertexWithUV(-2.0, -2.0, -0.2, 1, 0);
                        tessellator.addVertexWithUV(-2.0, +3.0, -0.2, 1, 1);
                        tessellator.addVertexWithUV(+3.0, +3.0, -0.2, 0, 1);
                        tessellator.addVertexWithUV(+3.0, -2.0, -0.2, 0, 0);

                        tessellator.addVertexWithUV(+3.0, -2.0, -0.2, 1, 0);
                        tessellator.addVertexWithUV(+3.0, +3.0, -0.2, 1, 1);
                        tessellator.addVertexWithUV(-2.0, +3.0, -0.2, 0, 1);
                        tessellator.addVertexWithUV(-2.0, -2.0, -0.2, 0, 0);
                        break;
                    case 5:
                        GL11.glTranslatef(0.0F, 0.5F, 0.5F);
                        GL11.glRotated(r, 1, 0, 0);
                        GL11.glTranslatef(-0.0F, -0.5F, -0.5F);

                        tessellator.addVertexWithUV(1.2, -2.0, +3.0, 1, 1);
                        tessellator.addVertexWithUV(1.2, +3.0, +3.0, 1, 0);
                        tessellator.addVertexWithUV(1.2, +3.0, -2.0, 0, 0);
                        tessellator.addVertexWithUV(1.2, -2.0, -2.0, 0, 1);

                        tessellator.addVertexWithUV(1.2, -2.0, -2.0, 1, 0);
                        tessellator.addVertexWithUV(1.2, +3.0, -2.0, 1, 1);
                        tessellator.addVertexWithUV(1.2, +3.0, +3.0, 0, 1);
                        tessellator.addVertexWithUV(1.2, -2.0, +3.0, 0, 0);
                        break;
                    case 6:
                        GL11.glTranslatef(0.5F, 0.5F, 0.0F);
                        GL11.glRotated(r, 0, 0, 1);
                        GL11.glTranslatef(-0.5F, -0.5F, -0.0F);

                        tessellator.addVertexWithUV(-2.0, -2.0, 1.2, 1, 0);
                        tessellator.addVertexWithUV(-2.0, +3.0, 1.2, 1, 1);
                        tessellator.addVertexWithUV(+3.0, +3.0, 1.2, 0, 1);
                        tessellator.addVertexWithUV(+3.0, -2.0, 1.2, 0, 0);

                        tessellator.addVertexWithUV(+3.0, -2.0, 1.2, 1, 0);
                        tessellator.addVertexWithUV(+3.0, +3.0, 1.2, 1, 1);
                        tessellator.addVertexWithUV(-2.0, +3.0, 1.2, 0, 1);
                        tessellator.addVertexWithUV(-2.0, -2.0, 1.2, 0, 0);
                        break;
                    case 7:
                        GL11.glTranslatef(0.0F, 0.5F, 0.5F);
                        GL11.glRotated(r, 1, 0, 0);
                        GL11.glTranslatef(-0.0F, -0.5F, -0.5F);
                        tessellator.addVertexWithUV(-0.2, -2.0, +3.0, 1, 1);
                        tessellator.addVertexWithUV(-0.2, +3.0, +3.0, 1, 0);
                        tessellator.addVertexWithUV(-0.2, +3.0, -2.0, 0, 0);
                        tessellator.addVertexWithUV(-0.2, -2.0, -2.0, 0, 1);

                        tessellator.addVertexWithUV(-0.2, -2.0, -2.0, 1, 0);
                        tessellator.addVertexWithUV(-0.2, +3.0, -2.0, 1, 1);
                        tessellator.addVertexWithUV(-0.2, +3.0, +3.0, 0, 1);
                        tessellator.addVertexWithUV(-0.2, -2.0, +3.0, 0, 0);
                        break;

                }
            }
            tessellator.draw();
            GL11.glEnable(GL11.GL_LIGHTING);
        }
        GL11.glPopMatrix();
    }
}
