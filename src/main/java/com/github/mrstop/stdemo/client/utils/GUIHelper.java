package com.github.mrstop.stdemo.client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidRegistry;

public class GUIHelper {
    public static void drawGUIFluid(TextureManager renderEngine, int GUIFluidID, int GUILeft, int GUITop, int startX, int startY, int scale, float zLevel){
        //获取以及绑定材质
        IIcon icon = null;
        if (FluidRegistry.getFluid(GUIFluidID) != null){
            icon = FluidRegistry.getFluid(GUIFluidID).getIcon();
        }
        if (icon == null) {
            icon = ((TextureMap) Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture)).getAtlasSprite("missingno");
        }
        renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        Tessellator tessellator = Tessellator.instance;
        double minU = icon.getInterpolatedU(0);
        double maxU = icon.getInterpolatedU(16);
        double minV = icon.getInterpolatedV(0);
        double maxV = icon.getInterpolatedV(16);
        for (int j = 0; j < scale / 16; j++) {
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(GUILeft + startX + 16, GUITop + startY - j * 16, zLevel,maxU, minV);
            tessellator.addVertexWithUV(GUILeft + startX + 16, GUITop + startY - 16 - j * 16, zLevel, maxU, maxV);
            tessellator.addVertexWithUV(GUILeft + startX + 00, GUITop + startY - 16 - j * 16, zLevel, minU, maxV);
            tessellator.addVertexWithUV(GUILeft + startX + 00, GUITop + startY - j * 16, zLevel, minU, minV);
            tessellator.draw();
        }
        if (scale % 16 != 0){
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(GUILeft + startX + 16, GUITop + startY - (scale / 16) * 16, zLevel,maxU, minV);
            tessellator.addVertexWithUV(GUILeft + startX + 16, GUITop + startY - (scale / 16) * 16 - (scale % 16), zLevel, maxU, icon.getInterpolatedV(scale %16));
            tessellator.addVertexWithUV(GUILeft + startX + 00, GUITop + startY - (scale / 16) * 16 - (scale % 16), zLevel, minU, icon.getInterpolatedV(scale %16));
            tessellator.addVertexWithUV(GUILeft + startX + 00, GUITop + startY - (scale / 16) * 16, zLevel, minU, minV);
            tessellator.draw();
        }
    }
}
