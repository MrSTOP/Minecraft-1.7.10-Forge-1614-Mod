package com.github.mrstop.stdemo.client.block.render;

import com.github.mrstop.stdemo.block.BlockLoader;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RenderWindmillGround implements ISimpleBlockRenderingHandler {

    public static final int renderID = RenderingRegistry.getNextAvailableRenderId();
    private final Tessellator tessellator = Tessellator.instance;
    public static final RenderWindmillGround renderWindmillGround =  new RenderWindmillGround();

    @Override
    public boolean renderWorldBlock(IBlockAccess blockAccess, int posX, int posY, int posZ, Block block, int modelId, RenderBlocks renderer) {
        IIcon iicon = BlockLoader.windmillGroundBlock.getIcon(0, 0);
        float minU = iicon.getMinU();
        float minV = iicon.getMinV();
        float maxU = iicon.getMaxU();
        float maxV = iicon.getMaxV();
        float sizeU = maxU - minU;
        float sizeV = minV - maxV;
        float partU = sizeU / 16;
        float partV = sizeV / 16;
        float noRightSide = maxU - partU * 2;
        float noLeftSide = minU + partU * 2;
        float noUpSide = maxV + partV * 2;
        float noDownSide = minV - partV * 2;
        // start drawing
        tessellator.addTranslation(posX, posY, posZ);
        // No idea what this is for but it fixes messed up color tint
        tessellator.setColorOpaque_F(1F, 1F, 1F);
        tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, posX, posY, posZ));
        switch (blockAccess.getBlockMetadata(posX, posY, posZ)){
            case 0:
                //WAST
                tessellator.addVertexWithUV(0.0, 0.0, 1.0, maxU, minV);
                tessellator.addVertexWithUV(0.0, 0.3, 1.0, maxU, maxV);
                tessellator.addVertexWithUV(0.0, 0.3, 0.0, minU, maxV);
                tessellator.addVertexWithUV(0.0, 0.0, 0.0, minU, minV);
                //NORTH
                tessellator.addVertexWithUV(0.0, 0.0, 0.0, maxU, minV);
                tessellator.addVertexWithUV(0.0, 0.3, 0.0, maxU, maxV);
                tessellator.addVertexWithUV(1.0, 0.3, 0.0, minU, maxV);
                tessellator.addVertexWithUV(1.0, 0.0, 0.0, minU, minV);
                //EAST
                tessellator.addVertexWithUV(1.0, 0.0, 0.0, maxU, minV);
                tessellator.addVertexWithUV(1.0, 0.3, 0.0, maxU, maxV);
                tessellator.addVertexWithUV(1.0, 0.3, 1.0, minU, maxV);
                tessellator.addVertexWithUV(1.0, 0.0, 1.0, minU, minV);
                //SOUTH
                tessellator.addVertexWithUV(1.0, 0.0, 1.0, maxU, minV);
                tessellator.addVertexWithUV(1.0, 0.3, 1.0, maxU, maxV);
                tessellator.addVertexWithUV(0.0, 0.3, 1.0, minU, maxV);
                tessellator.addVertexWithUV(0.0, 0.0, 1.0, minU, minV);
                //UP
                tessellator.addVertexWithUV(0.0, 0.3, 1.0, maxU, minV);
                tessellator.addVertexWithUV(1.0, 0.3, 1.0, maxU, maxV);
                tessellator.addVertexWithUV(1.0, 0.3, 0.0, minU, maxV);
                tessellator.addVertexWithUV(0.0, 0.3, 0.0, minU, minV);
                //DOWN
                tessellator.addVertexWithUV(1.0, 0.0, 1.0, maxU, minV);
                tessellator.addVertexWithUV(0.0, 0.0, 1.0, maxU, maxV);
                tessellator.addVertexWithUV(0.0, 0.0, 0.0, minU, maxV);
                tessellator.addVertexWithUV(1.0, 0.0, 0.0, minU, minV);
                break;
            case 1:
                //WAST
                tessellator.addVertexWithUV(0.3, 0.0, 1.0, noRightSide, minV);
                tessellator.addVertexWithUV(0.3, 0.3, 1.0, noRightSide, maxV);
                tessellator.addVertexWithUV(0.3, 0.3, 0.3, minU, maxV);
                tessellator.addVertexWithUV(0.3, 0.0, 0.3, minU, minV);
                //NORTH
                tessellator.addVertexWithUV(0.3, 0.0, 0.3, maxU, minV);
                tessellator.addVertexWithUV(0.3, 0.3, 0.3, maxU, maxV);
                tessellator.addVertexWithUV(1.0, 0.3, 0.3, noLeftSide, maxV);
                tessellator.addVertexWithUV(1.0, 0.0, 0.3, noLeftSide, minV);
                //UP
                tessellator.addVertexWithUV(0.3, 0.3, 1.0, noRightSide, minV);
                tessellator.addVertexWithUV(1.0, 0.3, 1.0, noRightSide, noUpSide);
                tessellator.addVertexWithUV(1.0, 0.3, 0.3, minU, noUpSide);
                tessellator.addVertexWithUV(0.3, 0.3, 0.3, minU, minV);
                //DOWN
                tessellator.addVertexWithUV(1.0, 0.0, 1.0, noRightSide, noDownSide);
                tessellator.addVertexWithUV(0.3, 0.0, 1.0, noRightSide, maxV);
                tessellator.addVertexWithUV(0.3, 0.0, 0.3, minU, maxV);
                tessellator.addVertexWithUV(1.0, 0.0, 0.3, minU, noDownSide);
                break;
            case 2:
                //WEST
                tessellator.addVertexWithUV(0.3, 0.0, 1.0, noRightSide, minV);
                tessellator.addVertexWithUV(0.3, 0.3, 1.0, noRightSide, maxV);
                tessellator.addVertexWithUV(0.3, 0.3, 0.0, noLeftSide, maxV);
                tessellator.addVertexWithUV(0.3, 0.0, 0.0, noLeftSide, minV);
                //UP
                tessellator.addVertexWithUV(0.3, 0.3, 1.0, noRightSide, minV);
                tessellator.addVertexWithUV(1.0, 0.3, 1.0, noRightSide, noUpSide);
                tessellator.addVertexWithUV(1.0, 0.3, 0.0, noLeftSide, noUpSide);
                tessellator.addVertexWithUV(0.3, 0.3, 0.0, noLeftSide, minV);
                //DOWN
                tessellator.addVertexWithUV(1.0, 0.0, 1.0, noRightSide, noDownSide);
                tessellator.addVertexWithUV(0.3, 0.0, 1.0, noRightSide, maxV);
                tessellator.addVertexWithUV(0.3, 0.0, 0.0, noLeftSide, maxV);
                tessellator.addVertexWithUV(1.0, 0.0, 0.0, noLeftSide, noDownSide);
                break;
            case 3:
                //WEST
                tessellator.addVertexWithUV(0.3, 0.0, 0.7, maxU, minV);
                tessellator.addVertexWithUV(0.3, 0.3, 0.7, maxU, maxV);
                tessellator.addVertexWithUV(0.3, 0.3, 0.0, noLeftSide, maxV);
                tessellator.addVertexWithUV(0.3, 0.0, 0.0, noLeftSide, minV);
                //SOUTH
                tessellator.addVertexWithUV(1.0, 0.0, 0.7, noRightSide, minV);
                tessellator.addVertexWithUV(1.0, 0.3, 0.7, noRightSide, maxV);
                tessellator.addVertexWithUV(0.3, 0.3, 0.7, minU, maxV);
                tessellator.addVertexWithUV(0.3, 0.0, 0.7, minU, minV);
                //UP
                tessellator.addVertexWithUV(0.3, 0.3, 0.7, maxU, minV);
                tessellator.addVertexWithUV(1.0, 0.3, 0.7, maxU, noUpSide);
                tessellator.addVertexWithUV(1.0, 0.3, 0.0, noLeftSide, noUpSide);
                tessellator.addVertexWithUV(0.3, 0.3, 0.0, noLeftSide, minV);
                //DOWN
                tessellator.addVertexWithUV(1.0, 0.0, 0.7, maxU, noDownSide);
                tessellator.addVertexWithUV(0.3, 0.0, 0.7, maxU, maxV);
                tessellator.addVertexWithUV(0.3, 0.0, 0.0, noLeftSide, maxV);
                tessellator.addVertexWithUV(1.0, 0.0, 0.0, noLeftSide, noDownSide);
                break;
            case 4:
                //NORTH
                tessellator.addVertexWithUV(0.0, 0.0, 0.3, noRightSide, minV);
                tessellator.addVertexWithUV(0.0, 0.3, 0.3, noRightSide, maxV);
                tessellator.addVertexWithUV(1.0, 0.3, 0.3, noLeftSide, maxV);
                tessellator.addVertexWithUV(1.0, 0.0, 0.3, noLeftSide, minV);
                //UP
                tessellator.addVertexWithUV(0.0, 0.3, 1.0, noRightSide, noDownSide);
                tessellator.addVertexWithUV(1.0, 0.3, 1.0, noRightSide, noUpSide);
                tessellator.addVertexWithUV(1.0, 0.3, 0.3, minU, noUpSide);
                tessellator.addVertexWithUV(0.0, 0.3, 0.3, minU, noDownSide);
                //DOWN
                tessellator.addVertexWithUV(1.0, 0.0, 1.0, noRightSide, noDownSide);
                tessellator.addVertexWithUV(0.0, 0.0, 1.0, noRightSide, noUpSide);
                tessellator.addVertexWithUV(0.0, 0.0, 0.3, minU, noUpSide);
                tessellator.addVertexWithUV(1.0, 0.0, 0.3, minU, noDownSide);
                break;
            case 5:
                //WAST
                tessellator.addVertexWithUV(0.0, 0.3, 1.0, maxU, minV);
                tessellator.addVertexWithUV(0.0, 1.0, 1.0, maxU, maxV);
                tessellator.addVertexWithUV(0.0, 1.0, 0.0, minU, maxV);
                tessellator.addVertexWithUV(0.0, 0.3, 0.0, minU, minV);
                //NORTH
                tessellator.addVertexWithUV(0.0, 0.3, 0.0, maxU, minV);
                tessellator.addVertexWithUV(0.0, 1.0, 0.0, maxU, maxV);
                tessellator.addVertexWithUV(1.0, 1.0, 0.0, minU, maxV);
                tessellator.addVertexWithUV(1.0, 0.3, 0.0, minU, minV);
                //EAST
                tessellator.addVertexWithUV(1.0, 0.3, 0.0, maxU, minV);
                tessellator.addVertexWithUV(1.0, 1.0, 0.0, maxU, maxV);
                tessellator.addVertexWithUV(1.0, 1.0, 1.0, minU, maxV);
                tessellator.addVertexWithUV(1.0, 0.3, 1.0, minU, minV);
                //SOUTH
                tessellator.addVertexWithUV(1.0, 0.3, 1.0, maxU, minV);
                tessellator.addVertexWithUV(1.0, 1.0, 1.0, maxU, maxV);
                tessellator.addVertexWithUV(0.0, 1.0, 1.0, minU, maxV);
                tessellator.addVertexWithUV(0.0, 0.3, 1.0, minU, minV);
                //UP
                tessellator.addVertexWithUV(0.0, 1.0, 1.0, maxU, minV);
                tessellator.addVertexWithUV(1.0, 1.0, 1.0, maxU, maxV);
                tessellator.addVertexWithUV(1.0, 1.0, 0.0, minU, maxV);
                tessellator.addVertexWithUV(0.0, 1.0, 0.0, minU, minV);
                //DOWN
                tessellator.addVertexWithUV(1.0, 0.0, 1.0, noRightSide, noDownSide);
                tessellator.addVertexWithUV(0.0, 0.0, 1.0, noRightSide, noUpSide);
                tessellator.addVertexWithUV(0.0, 0.0, 0.0, noLeftSide, noUpSide);
                tessellator.addVertexWithUV(1.0, 0.0, 0.0, noLeftSide, noDownSide);
                break;
            case 6:
                //SOUTH
                tessellator.addVertexWithUV(1.0, 0.0, 0.7, noRightSide, minV);
                tessellator.addVertexWithUV(1.0, 0.3, 0.7, noRightSide, maxV);
                tessellator.addVertexWithUV(0.0, 0.3, 0.7, noLeftSide, maxV);
                tessellator.addVertexWithUV(0.0, 0.0, 0.7, noLeftSide, minV);
                //UP
                tessellator.addVertexWithUV(0.0, 0.3, 0.7, maxU, noDownSide);
                tessellator.addVertexWithUV(1.0, 0.3, 0.7, maxU, noUpSide);
                tessellator.addVertexWithUV(1.0, 0.3, 0.0, noLeftSide, noUpSide);
                tessellator.addVertexWithUV(0.0, 0.3, 0.0, noLeftSide, noDownSide);
                //DOWN
                tessellator.addVertexWithUV(1.0, 0.0, 0.7, maxU, noDownSide);
                tessellator.addVertexWithUV(0.0, 0.0, 0.7, maxU, noUpSide);
                tessellator.addVertexWithUV(0.0, 0.0, 0.0, noLeftSide, noUpSide);
                tessellator.addVertexWithUV(1.0, 0.0, 0.0, noLeftSide, noDownSide);
                break;
            case 7:
                //EAST
                tessellator.addVertexWithUV(0.7, 0.0, 0.3, maxU, minV);
                tessellator.addVertexWithUV(0.7, 0.3, 0.3, maxU, maxV);
                tessellator.addVertexWithUV(0.7, 0.3, 1.0, noLeftSide, maxV);
                tessellator.addVertexWithUV(0.7, 0.0, 1.0, noLeftSide, minV);
                //NORTH
                tessellator.addVertexWithUV(0.0, 0.0, 0.3, noRightSide, minV);
                tessellator.addVertexWithUV(0.0, 0.3, 0.3, noRightSide, maxV);
                tessellator.addVertexWithUV(0.7, 0.3, 0.3, minU, maxV);
                tessellator.addVertexWithUV(0.7, 0.0, 0.3, minU, minV);
                //UP
                tessellator.addVertexWithUV(0.0, 0.3, 1.0, noRightSide, noDownSide);
                tessellator.addVertexWithUV(0.7, 0.3, 1.0, noRightSide, maxV);
                tessellator.addVertexWithUV(0.7, 0.3, 0.3, minU, maxV);
                tessellator.addVertexWithUV(0.0, 0.3, 0.3, minU, noDownSide);
                //DOWN
                tessellator.addVertexWithUV(0.7, 0.0, 1.0, noRightSide, minV);
                tessellator.addVertexWithUV(0.0, 0.0, 1.0, noRightSide, noUpSide);
                tessellator.addVertexWithUV(0.0, 0.0, 0.3, minU, noUpSide);
                tessellator.addVertexWithUV(0.7, 0.0, 0.3, minU, minV);
                break;
            case 8:
                //EAST
                tessellator.addVertexWithUV(0.7, 0.0, 0.0, noRightSide, minV);
                tessellator.addVertexWithUV(0.7, 0.3, 0.0, noRightSide, maxV);
                tessellator.addVertexWithUV(0.7, 0.3, 1.0, noLeftSide, maxV);
                tessellator.addVertexWithUV(0.7, 0.0, 1.0, noLeftSide, minV);
                //UP
                tessellator.addVertexWithUV(0.0, 0.3, 1.0, noRightSide, noDownSide);
                tessellator.addVertexWithUV(0.7, 0.3, 1.0, noRightSide, maxV);
                tessellator.addVertexWithUV(0.7, 0.3, 0.0, noLeftSide, maxV);
                tessellator.addVertexWithUV(0.0, 0.3, 0.0, noLeftSide, noDownSide);
                //DOWN
                tessellator.addVertexWithUV(0.7, 0.0, 1.0, noRightSide, minV);
                tessellator.addVertexWithUV(0.0, 0.0, 1.0, noRightSide, noUpSide);
                tessellator.addVertexWithUV(0.0, 0.0, 0.0, noLeftSide, noUpSide);
                tessellator.addVertexWithUV(0.7, 0.0, 0.0, noLeftSide, minV);
                break;
            case 9:
                //EAST
                tessellator.addVertexWithUV(0.7, 0.0, 0.0, noRightSide, minV);
                tessellator.addVertexWithUV(0.7, 0.3, 0.0, noRightSide, maxV);
                tessellator.addVertexWithUV(0.7, 0.3, 0.7, minU, maxV);
                tessellator.addVertexWithUV(0.7, 0.0, 0.7, minU, minV);
                //SOUTH
                tessellator.addVertexWithUV(0.7, 0.0, 0.7, maxU, minV);
                tessellator.addVertexWithUV(0.7, 0.3, 0.7, maxU, maxV);
                tessellator.addVertexWithUV(0.0, 0.3, 0.7, noLeftSide, maxV);
                tessellator.addVertexWithUV(0.0, 0.0, 0.7, noLeftSide, minV);
                //UP
                tessellator.addVertexWithUV(0.0, 0.3, 0.7, maxU, noDownSide);
                tessellator.addVertexWithUV(0.7, 0.3, 0.7, maxU, maxV);
                tessellator.addVertexWithUV(0.7, 0.3, 0.0, noLeftSide, maxV);
                tessellator.addVertexWithUV(0.0, 0.3, 0.0, noLeftSide, noDownSide);
                //DOWN
                tessellator.addVertexWithUV(0.7, 0.0, 0.7, maxU, minV);
                tessellator.addVertexWithUV(0.0, 0.0, 0.7, maxU, noUpSide);
                tessellator.addVertexWithUV(0.0, 0.0, 0.0, noLeftSide, noUpSide);
                tessellator.addVertexWithUV(0.7, 0.0, 0.0, noLeftSide, minV);
                break;
            default:
                renderer.renderStandardBlock(block, posX, posY, posZ);
        }
        tessellator.addTranslation(-posX, -posY, -posZ);
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    @Override
    public int getRenderId() {
        return renderID;
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        IIcon iicon = BlockLoader.windmillGroundBlock.getIcon(0, 0);
        float minU = iicon.getMinU();
        float minV = iicon.getMinV();
        float maxU = iicon.getMaxU();
        float maxV = iicon.getMaxV();
        float sizeU = maxU - minU;
        float sizeV = minV - maxV;
        float partU = sizeU / 16;
        float partV = sizeV / 16;
        //WAST
        tessellator.addVertexWithUV(0.0, 0.0, 1.0, maxU, minV);
        tessellator.addVertexWithUV(0.0, 0.3, 1.0, maxU, maxV);
        tessellator.addVertexWithUV(0.0, 0.3, 0.0, minU, maxV);
        tessellator.addVertexWithUV(0.0, 0.0, 0.0, minU, minV);
        //NORTH
        tessellator.addVertexWithUV(0.0, 0.0, 0.0, maxU, minV);
        tessellator.addVertexWithUV(0.0, 0.3, 0.0, maxU, maxV);
        tessellator.addVertexWithUV(1.0, 0.3, 0.0, minU, maxV);
        tessellator.addVertexWithUV(1.0, 0.0, 0.0, minU, minV);
        //EAST
        tessellator.addVertexWithUV(1.0, 0.0, 0.0, maxU, minV);
        tessellator.addVertexWithUV(1.0, 0.3, 0.0, maxU, maxV);
        tessellator.addVertexWithUV(1.0, 0.3, 1.0, minU, maxV);
        tessellator.addVertexWithUV(1.0, 0.0, 1.0, minU, minV);
        //SOUTH
        tessellator.addVertexWithUV(1.0, 0.0, 1.0, maxU, minV);
        tessellator.addVertexWithUV(1.0, 0.3, 1.0, maxU, maxV);
        tessellator.addVertexWithUV(0.0, 0.3, 1.0, minU, maxV);
        tessellator.addVertexWithUV(0.0, 0.0, 1.0, minU, minV);
        //UP
        tessellator.addVertexWithUV(0.0, 0.3, 1.0, maxU, minV);
        tessellator.addVertexWithUV(1.0, 0.3, 1.0, maxU, maxV);
        tessellator.addVertexWithUV(1.0, 0.3, 0.0, minU, maxV);
        tessellator.addVertexWithUV(0.0, 0.3, 0.0, minU, minV);
        //DOWN
        tessellator.addVertexWithUV(1.0, 0.0, 1.0, maxU, minV);
        tessellator.addVertexWithUV(0.0, 0.0, 1.0, maxU, maxV);
        tessellator.addVertexWithUV(0.0, 0.0, 0.0, minU, maxV);
        tessellator.addVertexWithUV(1.0, 0.0, 0.0, minU, minV);
    }
}
