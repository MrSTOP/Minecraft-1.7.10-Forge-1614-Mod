package com.github.mrstop.stdemo.client.utils;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;

public class GUIHelper {
    public static void drawGUIFluid(IIcon fluidIcon,int GUILeft, int GUITop, int startX, int startY, int scale, float zLevel){
        Tessellator tessellator = Tessellator.instance;
        double minU = fluidIcon.getInterpolatedU(0);
        double maxU = fluidIcon.getInterpolatedU(16);
        double minV = fluidIcon.getInterpolatedV(0);
        double maxV = fluidIcon.getInterpolatedV(16);
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
            tessellator.addVertexWithUV(GUILeft + startX + 16, GUITop + startY - (scale / 16) * 16 - (scale % 16), zLevel, maxU, fluidIcon.getInterpolatedV(scale %16));
            tessellator.addVertexWithUV(GUILeft + startX + 00, GUITop + startY - (scale / 16) * 16 - (scale % 16), zLevel, minU, fluidIcon.getInterpolatedV(scale %16));
            tessellator.addVertexWithUV(GUILeft + startX + 00, GUITop + startY - (scale / 16) * 16, zLevel, minU, minV);
            tessellator.draw();
        }
    }
}
