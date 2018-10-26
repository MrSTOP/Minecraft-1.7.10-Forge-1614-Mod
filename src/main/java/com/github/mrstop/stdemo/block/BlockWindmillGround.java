package com.github.mrstop.stdemo.block;

import com.github.mrstop.stdemo.client.block.render.RenderWindmillGround;
import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWindmillGround extends Block {

    @SideOnly(Side.CLIENT)
    private IIcon icon;

    public BlockWindmillGround(){
        super(Material.rock);
        this.setUnlocalizedName("windmillGroundBlock");
        this.setHardness(0.8F);
        this.setStepSound(soundTypeStone);
        this.setHarvestLevel("pickaxe", 1);
        this.setBlockBounds(0, 0, 0, 1, 0.5F, 1);
        this.setCreativeTab(CreativeTabsLoader.tabSTDemo);
    }


    @Override
    public void breakBlock(World worldIn, int x, int y, int z, Block blockBroken, int meta) {
        super.breakBlock(worldIn, x, y, z, blockBroken, meta);
        switch (meta){
            case 1:
                breakStructWhenBlockBreak(worldIn, x, y, z);
                break;
            case 2:
                breakStructWhenBlockBreak(worldIn, x, y, z - 1);
                break;
            case 3:
                breakStructWhenBlockBreak(worldIn, x, y, z - 2);
                break;
            case 4:
                breakStructWhenBlockBreak(worldIn, x - 1, y, z);
                break;
            case 5:
                breakStructWhenBlockBreak(worldIn, x - 1, y, z - 1);
                break;
            case 6:
                breakStructWhenBlockBreak(worldIn, x - 1, y, z - 2);
                break;
            case 7:
                breakStructWhenBlockBreak(worldIn, x - 2, y, z);
                break;
            case 8:
                breakStructWhenBlockBreak(worldIn, x - 2, y, z - 1);
                break;
            case 9:
                breakStructWhenBlockBreak(worldIn, x - 2, y, z - 2);
                break;
        }
    }

    private void breakStructWhenBlockBreak(World world, int xStart, int y, int zStart){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (world.getBlock(xStart + i, y, zStart + j).isAssociatedBlock(BlockLoader.windmillGroundBlock)){
                   world.breakBlock(xStart + i, y, zStart + j, true);
                }
            }
        }
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, int x, int y, int z) {
        super.setBlockBoundsBasedOnState(worldIn, x, y, z);
        switch (worldIn.getBlockMetadata(x, y, z)){
            case 1:
                this.setBlockBounds(0.3F, 0, 0.3F, 1, 0.3F, 1);
                break;
            case 2:
                this.setBlockBounds(0.3F, 0, 0, 1, 0.3F, 1);
                break;
            case 3:
                this.setBlockBounds(0.3F, 0, 0, 1, 0.3F, 0.7F);
                break;
            case 4:
                this.setBlockBounds(0, 0, 0.3F, 1, 0.3F, 1);
                break;
            case 5:
                this.setBlockBounds(0, 0, 0, 1, 1.0F, 1);
                break;
            case 6:
                this.setBlockBounds(0, 0, 0, 1, 0.3F, 0.7F);
                break;
            case 7:
                this.setBlockBounds(0, 0, 0.3F, 0.7F, 0.3F, 1);
                break;
            case 8:
                this.setBlockBounds(0, 0, 0, 0.7F, 0.3F, 1);
                break;
            case 9:
                this.setBlockBounds(0, 0, 0, 0.7F, 0.3F, 0.7F);
                break;
            default:
                this.setBlockBounds(0, 0, 0, 1, 0.3F, 1);
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World worldIn, int x, int y, int z) {
        return super.getCollisionBoundingBoxFromPool(worldIn, x, y, z);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return RenderWindmillGround.renderID;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return icon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister blockIcon) {
        this.icon = blockIcon.registerIcon("stdemo:windmill_ground_block");
    }
}

//    private void updateMultiBlockStructure(World world, int x, int y, int z){
//        Point point = new Point(-1, -1);
//        int blockCount = 0;
//        boolean[][] blockMatrix = new boolean[5][5];
//        blockCount = getBlockMatrix(world, x, y, z, blockMatrix);
//        if (isMultiBlockStructure(world, x, y, z, blockMatrix, blockCount, point)){
//            constructMultiBLockStructure(world, point, x, y, z);
//        }else {
//            destroyMultiBlockStructure(world, x, y, z, blockMatrix);
//            for (int i = 0; i < 5; i++){
//                for (int j = 0; j < 5; j++) {
//                    int tempX = x - i + 2;
//                    int tempZ = z + j - 2;
//                    boolean[][] tempMatrix = new boolean[5][5];
//                    if (blockMatrix[i][j] && i != 2 && j != 2){
//                        Point tempPoint = new Point(-1, -1);
//                        int tempBlockCount = getBlockMatrix(world, tempX, y, tempZ, tempMatrix);
//                        if (isMultiBlockStructure(world, tempX, y, tempZ, tempMatrix, tempBlockCount, tempPoint)){
//                            constructMultiBLockStructure(world, tempPoint, tempX, y, tempZ);
//                        }else {
//                            destroyMultiBlockStructure(world, tempX, y, tempZ, tempMatrix);
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    private int getBlockMatrix(World world, int x, int y, int z, boolean[][] blockMatrix){
//        int blockCount = 0;
//        for (int i = -2; i <= 2; i++) {
//            for (int j = -2; j <= 2; j++) {
//                if (world.getBlock(x + i, y, z + j).isAssociatedBlock(BlockLoader.windmillGroundBlock)){
//                    blockMatrix[2 - i][2 + j] = true;
//                    blockCount++;
//                }else {
//                    blockMatrix[2 - i][2 + j] = false;
//                }
//            }
//        }
//        System.out.print("===============================");
//        System.out.println();
//        System.out.print("X:" + x + " Y:" + y + " Z: " + z + " COUNT:" + blockCount);
//        System.out.println();
//        for (int i = 0; i < 5; i++) {
//            for (int j = 0; j < 5; j++) {
//                if (i != 2 || j != 2){
//                    if (blockMatrix[i][j]){
//                        System.out.print("■ ");
//                    }
//                    if (!blockMatrix[i][j]){
//                        System.out.print("□ ");
//                    }
//                }else {
//                    if (blockMatrix[i][j]){
//                        System.out.print("● ");
//                    }
//                    if (!blockMatrix[i][j]){
//                        System.out.print("○ ");
//                    }
//                }
//            }
//            System.out.println();
//        }
//        System.out.print("===============================");
//        System.out.println();
//        return blockCount;
//    }
//
//    private boolean isMultiBlockStructure(World world, int x, int y, int z, boolean[][] blockMatrix, int blockCount, Point centerPoint){
//        if (blockCount < 9 || blockCount > 19){
//            return false;
//        }
//        if (judgeStructureCenter(blockMatrix, centerPoint)){
//            return true;
//        }
//        return false;
//    }
//
//    private boolean judgeStructureCenter(boolean[][] blockMatrix, Point centerPoint){
//        LinkedList<Point> rightPoint = new LinkedList<Point>();
//        for (int i = 0; i < 5; i++) {
//            for (int j = 0; j < 5; j++) {
//                if (blockMatrix[i][j]){
//                    rightPoint.add(new Point(i, j));
//                }
//            }
//        }
//        while (!rightPoint.isEmpty()) {
//            Point currPoint = rightPoint.poll();
//            Point tempPoint = judgeMultiBlockStructure(blockMatrix, currPoint);
//            if (tempPoint != null){
//                centerPoint.x = tempPoint.x;
//                centerPoint.y = tempPoint.y;
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private Point judgeMultiBlockStructure(boolean[][] blockMatrix, Point currPoint){
//        int x = -1;
//        int z = -1;
//        x = currPoint.x;
//        z = currPoint.y;
//        if (currPoint == null){
//            return null;
//        }
//        if (z >= 3){
//
//        }else if (z == 2){
//            if (x >= 3){
//
//            }else if (x == 0){
//                if (blockMatrix[x][z + 1] && blockMatrix[x + 1][z]){
//                    if (blockMatrix[x][z + 2] && blockMatrix[x + 2][z]){
//                        if (blockMatrix[x + 1][z + 1] && blockMatrix[x + 1][z + 2]){
//                            if (blockMatrix[x + 2][z + 1] && blockMatrix[x + 2][z + 2]){
//                                if (!(blockMatrix[x][z - 1] || blockMatrix[x + 1][z - 1] || blockMatrix[x + 2][z - 1])){
//                                    if (!(blockMatrix[x + 3][z] || blockMatrix[x + 3][z + 1] || blockMatrix[x + 3][z + 2])){
//                                        return new Point(x + 1, z + 1);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }else if (x == 2){
//                if (blockMatrix[x][z + 1] && blockMatrix[x + 1][z]){
//                    if (blockMatrix[x][z + 2] && blockMatrix[x + 2][z]){
//                        if (blockMatrix[x + 1][z + 1] && blockMatrix[x + 1][z + 2]){
//                            if (blockMatrix[x + 2][z + 1] && blockMatrix[x + 2][z + 2]){
//                                if (!(blockMatrix[x][z - 1] || blockMatrix[x + 1][z - 1] || blockMatrix[x + 2][z - 1])){
//                                    if (!(blockMatrix[x - 1][z] || blockMatrix[x - 1][z + 1] || blockMatrix[x - 1][z + 2])){
//                                        return new Point(x + 1, z + 1);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }else {
//                if (blockMatrix[x][z + 1] && blockMatrix[x + 1][z]){
//                    if (blockMatrix[x][z + 2] && blockMatrix[x + 2][z]){
//                        if (blockMatrix[x + 1][z + 1] && blockMatrix[x + 1][z + 2]){
//                            if (blockMatrix[x + 2][z + 1] && blockMatrix[x + 2][z + 2]){
//                                if (!(blockMatrix[x][z - 1] || blockMatrix[x + 1][z - 1] || blockMatrix[x + 2][z - 1])){
//                                    if (!(blockMatrix[x - 1][z] || blockMatrix[x - 1][z + 1] || blockMatrix[x - 1][z + 2])){
//                                        if (!(blockMatrix[x + 3][z] || blockMatrix[x + 3][z + 1] || blockMatrix[x + 3][z + 2])){
//                                            return new Point(x + 1, z + 1);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }else if (z == 0){
//            if (x >= 3){
//
//            }else if (x == 0){
//                if (blockMatrix[x][z + 1] && blockMatrix[x + 1][z]){
//                    if (blockMatrix[x][z + 2] && blockMatrix[x + 2][z]){
//                        if (blockMatrix[x + 1][z + 1] && blockMatrix[x + 1][z + 2]){
//                            if (blockMatrix[x + 2][z + 1] && blockMatrix[x + 2][z + 2]){
//                                if (!(blockMatrix[x + 3][z] || blockMatrix[x + 3][z + 1] || blockMatrix[x + 3][z + 2])){
//                                    if (!(blockMatrix[x][z + 3] || blockMatrix[x + 1][z + 3] || blockMatrix[x + 2][z + 3])){
//                                        return new Point(x + 1, z + 1);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }else if (x == 2){
//                if (blockMatrix[x][z + 1] && blockMatrix[x + 1][z]){
//                    if (blockMatrix[x][z + 2] && blockMatrix[x + 2][z]){
//                        if (blockMatrix[x + 1][z + 1] && blockMatrix[x + 1][z + 2]){
//                            if (blockMatrix[x + 2][z + 1] && blockMatrix[x + 2][z + 2]){
//                                if (!(blockMatrix[x - 1][z] || blockMatrix[x - 1][z + 1] || blockMatrix[x - 1][z + 2])){
//                                    if (!(blockMatrix[x][z + 3] ||blockMatrix[x + 1][z + 3] || blockMatrix[x + 2][z + 3])){
//                                        return new Point(x + 1, z + 1);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }else {
//                if (blockMatrix[x][z + 1] && blockMatrix[x + 1][z]){
//                    if (blockMatrix[x][z + 2] && blockMatrix[x + 2][z]){
//                        if (blockMatrix[x + 1][z + 1] && blockMatrix[x + 1][z + 2]){
//                            if (blockMatrix[x + 2][z + 1] && blockMatrix[x + 2][z + 2]){
//                                if (!(blockMatrix[x - 1][z] || blockMatrix[x - 1][z + 1] || blockMatrix[x - 1][z + 2])){
//                                    if (!(blockMatrix[x][z + 3] || blockMatrix[x + 1][z + 3] || blockMatrix[x + 2][z + 3])){
//                                        if (!(blockMatrix[x + 3][z] || blockMatrix[x + 3][z + 1] || blockMatrix[x + 3][z + 2])){
//                                            return new Point(x + 1, z + 1);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }else {
//            if (x >= 3){
//
//            }else if (x == 0){
//                if (blockMatrix[x][z + 1] && blockMatrix[x + 1][z]){
//                    if (blockMatrix[x][z + 2] && blockMatrix[x + 2][z]){
//                        if (blockMatrix[x + 1][z + 1] && blockMatrix[x + 1][z + 2]){
//                            if (blockMatrix[x + 2][z + 1] && blockMatrix[x + 2][z + 2]){
//                                if (!(blockMatrix[x][z - 1] || blockMatrix[x + 1][z - 1] || blockMatrix[x + 2][z - 1])){
//                                    if (!(blockMatrix[x][z + 3] || blockMatrix[x + 1][z + 3] || blockMatrix[x + 2][z + 3])){
//                                        if (!(blockMatrix[x + 3][z] || blockMatrix[x + 3][z + 1] || blockMatrix[x + 3][z + 2])){
//                                            return new Point(x + 1, z + 1);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }else if (x == 2){
//                if (blockMatrix[x][z + 1] && blockMatrix[x + 1][z]){
//                    if (blockMatrix[x][z + 2] && blockMatrix[x + 2][z]){
//                        if (blockMatrix[x + 1][z + 1] && blockMatrix[x + 1][z + 2]){
//                            if (blockMatrix[x + 2][z + 1] && blockMatrix[x + 2][z + 2]){
//                                if (!(blockMatrix[x - 1][z] || blockMatrix[x - 1][z + 1] || blockMatrix[x - 1][z + 2])){
//                                    if (!(blockMatrix[x][z - 1] || blockMatrix[x + 1][z - 1] || blockMatrix[x + 2][z - 1])){
//                                        if (!(blockMatrix[x][z + 3] || blockMatrix[x + 1][z + 3] || blockMatrix[x + 2][z + 3])){
//                                            return new Point(x + 1, z + 1);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }else {
//                if (blockMatrix[x][z + 1] && blockMatrix[x + 1][z]){
//                    if (blockMatrix[x][z + 2] && blockMatrix[x + 2][z]){
//                        if (blockMatrix[x + 1][z + 1] && blockMatrix[x + 1][z + 2]){
//                            if (blockMatrix[x + 2][z + 1] && blockMatrix[x + 2][z + 2]){
//                                if (!(blockMatrix[x - 1][z] || blockMatrix[x - 1][z + 1] || blockMatrix[x - 1][z + 2])){
//                                    if (!(blockMatrix[x][z - 1] || blockMatrix[x + 1][z - 1] || blockMatrix[x + 2][z - 1])){
//                                        if (!(blockMatrix[x][z + 3] || blockMatrix[x + 1][z + 3] || blockMatrix[x + 2][z +3])){
//                                            if (!(blockMatrix[x + 3][z] || blockMatrix[x + 3][z + 1] || blockMatrix[x + 3][z + 2])){
//                                                return new Point(x + 1, z + 1);
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return null;
//    }
//
//    private void constructMultiBLockStructure(World world,Point centerPoint,int x, int y, int z){
//        world.setBlockMetadataWithNotify(x - centerPoint.x + 2 - 1, y, z + centerPoint.y - 2 - 1, 1, 2);
//        world.setBlockMetadataWithNotify(x - centerPoint.x + 2 - 1, y, z + centerPoint.y - 2, 2, 2);
//        world.setBlockMetadataWithNotify(x - centerPoint.x + 2 - 1, y, z + centerPoint.y - 2 + 1, 3, 2);
//        world.setBlockMetadataWithNotify(x - centerPoint.x + 2, y, z + centerPoint.y - 2 - 1, 4, 2);
//        world.setBlockMetadataWithNotify(x - centerPoint.x + 2, y, z + centerPoint.y - 2, 5, 2);
//        world.setBlockMetadataWithNotify(x - centerPoint.x + 2, y, z + centerPoint.y - 2 + 1, 6, 2);
//        world.setBlockMetadataWithNotify(x - centerPoint.x + 2 + 1, y, z + centerPoint.y - 2 - 1, 7, 2);
//        world.setBlockMetadataWithNotify(x - centerPoint.x + 2 + 1, y, z + centerPoint.y - 2, 8, 2);
//        world.setBlockMetadataWithNotify(x - centerPoint.x + 2 + 1, y, z + centerPoint.y - 2 + 1, 9, 2);
//    }
//
//    private void destroyMultiBlockStructure(World world, int x, int y, int z){
////        world.setBlock(x, y, z, Blocks.stone);
//        world.setBlockMetadataWithNotify(x, y, z, 0, 1);
//    }
//
//    private void destroyMultiBlockStructure(World world, int x, int y, int z, boolean[][] blockMatrix){
//        for (int i = 0; i < 5; i++) {
//            for (int j = 0; j < 5; j++) {
//                int tempX = x - i + 2;
//                int tempZ = z + j - 2;
//                if (blockMatrix[i][j]){
//                    destroyMultiBlockStructure(world, tempX, y, tempZ);
//                }
//            }
//        }
//    }