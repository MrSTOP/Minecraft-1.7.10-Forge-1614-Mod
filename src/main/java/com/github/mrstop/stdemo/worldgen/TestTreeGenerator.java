package com.github.mrstop.stdemo.worldgen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class TestTreeGenerator extends WorldGenTrees {
    /** 生成的树的最小高度。 */
    private final int minTreeHeight;
    /** 如果这棵树能生长藤蔓，那就是真的。 */
    private final boolean vinesGrow;
    /** 要在树生成中使用的木材的元数据值。 */
    private final int metaWood;
    /** 要在树生成中使用的叶子的元数据值。 */
    private final int metaLeaves;
//    private static final String __OBFID = "CL_00000438";

    public TestTreeGenerator(boolean doBlockNotify) {
        this(doBlockNotify, 4, 0, 0, false);
    }

    public TestTreeGenerator(boolean doBlockNotify, int minTreeHeight, int metaWood, int metaLeaves, boolean vinesGrow) {
        super(doBlockNotify);
        this.minTreeHeight = minTreeHeight;
        this.metaWood = metaWood;
        this.metaLeaves = metaLeaves;
        this.vinesGrow = vinesGrow;
    }

    /**
     * 进行了生成返回true
     * */
    public boolean generate(World world, Random random, int x, int y, int z) {
        int treeHeight = random.nextInt(3) + this.minTreeHeight;
        boolean canGenerate = true;
        /**
         * 判断要生成树的位置的y坐标是否大于1并且+树高后是否小于256
         * 检测树是否会生成在世界内
         * */
        if (y >= 1 && y + treeHeight + 1 <= 256) {
            byte range = 0;
            int zCenter = 0;
            Block block;
            /**
             *从y高度开始, 共执行treeHeight + 2次
             * 检测是否有足够的空间生成树干和树叶
             * */
            for (int currentY = y; currentY <= y + 1 + treeHeight; ++currentY) {
                range = 1;
                /**
                 * 如果是第一次循环
                 * 检测最下面的树干(最贴近地面的树干)是否有空间生成
                 * */
                if (currentY == y) {
                    range = 0;
                }
                /**
                 * 如果是倒数3,2,1次循环
                 * 检测树叶是否有空间生成
                 * */
                if (currentY >= y + 1 + treeHeight - 2) {
                    range = 2;
                }
                /**
                 * 遍历以(x, z)为中心长度为2*range的正方形区域，若前一次循环已经发现空间不足则不再进行循环
                 * */
                for (int xCenter = x - range; xCenter <= x + range && canGenerate; ++xCenter) {
                    for (zCenter = z - range; zCenter <= z + range && canGenerate; ++zCenter) {
                        /**
                         * 检测当前y值是否符合高度要求
                         * */
                        if (currentY >= 0 && currentY < 256) {
                            block = world.getBlock(xCenter, currentY, zCenter);

                            /**
                             * 检查当前方块是否可以被替换，不可替换则取消树的生成
                             * */
                            if (!this.isReplaceable(world, xCenter, currentY, zCenter)) {
                                canGenerate = false;
                            }
                        }
                        else {
                            /**
                             * 当前y值不符合高度要求
                             * 取消生成树
                             * */
                            canGenerate = false;
                        }
                    }
                }
            }
            /**
             * 检查是否要生成树
             * */
            if (!canGenerate) {
                return false;
            }
            else {
                /**
                 * 获取树下面一格的方块
                 * */
                Block blockBase = world.getBlock(x, y - 1, z);
                /**
                 * 检查blockBase是否适合树苗生长
                 * */
                boolean isSoil = blockBase.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, (BlockSapling) Blocks.sapling);
                /**
                 * 如果适合树苗生长并且高度适合*/
                if (isSoil && y < 256 - treeHeight - 1) {
                    blockBase.onPlantGrow(world, x, y - 1, z, x, y, z);
                    range = 3;
                    byte b1 = 0;
                    int l1;
                    int i2;
                    int j2;
                    int i3;
                    int yCenter = zCenter;
                    for (yCenter = y - range + treeHeight; yCenter <= y + treeHeight; ++yCenter) {
                        i3 = yCenter - (y + treeHeight);
                        l1 = b1 + 1 - i3 / 2;

                        for (i2 = x - l1; i2 <= x + l1; ++i2) {
                            j2 = i2 - x;

                            for (int k2 = z - l1; k2 <= z + l1; ++k2) {
                                int l2 = k2 - z;

                                if (Math.abs(j2) != l1 || Math.abs(l2) != l1 || random.nextInt(2) != 0 && i3 != 0) {
                                    Block block1 = world.getBlock(i2, yCenter, k2);

                                    if (block1.isAir(world, i2, yCenter, k2) || block1.isLeaves(world, i2, yCenter, k2)) {
                                        this.setBlockAndNotifyAdequately(world, i2, yCenter, k2, Blocks.leaves, this.metaLeaves);
                                    }
                                }
                            }
                        }
                    }

                    for (yCenter = 0; yCenter < treeHeight; ++yCenter) {
                        block = world.getBlock(x, y + yCenter, z);

                        if (block.isAir(world, x, y + yCenter, z) || block.isLeaves(world, x, y + yCenter, z)) {
                            this.setBlockAndNotifyAdequately(world, x, y + yCenter, z, Blocks.log, this.metaWood);

                            if (this.vinesGrow && yCenter > 0) {
                                if (random.nextInt(3) > 0 && world.isAirBlock(x - 1, y + yCenter, z)) {
                                    this.setBlockAndNotifyAdequately(world, x - 1, y + yCenter, z, Blocks.vine, 8);
                                }

                                if (random.nextInt(3) > 0 && world.isAirBlock(x + 1, y + yCenter, z)) {
                                    this.setBlockAndNotifyAdequately(world, x + 1, y + yCenter, z, Blocks.vine, 2);
                                }

                                if (random.nextInt(3) > 0 && world.isAirBlock(x, y + yCenter, z - 1)) {
                                    this.setBlockAndNotifyAdequately(world, x, y + yCenter, z - 1, Blocks.vine, 1);
                                }

                                if (random.nextInt(3) > 0 && world.isAirBlock(x, y + yCenter, z + 1)) {
                                    this.setBlockAndNotifyAdequately(world, x, y + yCenter, z + 1, Blocks.vine, 4);
                                }
                            }
                        }
                    }

                    if (this.vinesGrow) {
                        for (yCenter = y - 3 + treeHeight; yCenter <= y + treeHeight; ++yCenter) {
                            i3 = yCenter - (y + treeHeight);
                            l1 = 2 - i3 / 2;

                            for (i2 = x - l1; i2 <= x + l1; ++i2) {
                                for (j2 = z - l1; j2 <= z + l1; ++j2) {
                                    if (world.getBlock(i2, yCenter, j2).isLeaves(world, i2, yCenter, j2)) {
                                        if (random.nextInt(4) == 0 && world.getBlock(i2 - 1, yCenter, j2).isAir(world, i2 - 1, yCenter, j2)) {
                                            this.growVines(world, i2 - 1, yCenter, j2, 8);
                                        }

                                        if (random.nextInt(4) == 0 && world.getBlock(i2 + 1, yCenter, j2).isAir(world, i2 + 1, yCenter, j2)) {
                                            this.growVines(world, i2 + 1, yCenter, j2, 2);
                                        }

                                        if (random.nextInt(4) == 0 && world.getBlock(i2, yCenter, j2 - 1).isAir(world, i2, yCenter, j2 - 1)) {
                                            this.growVines(world, i2, yCenter, j2 - 1, 1);
                                        }

                                        if (random.nextInt(4) == 0 && world.getBlock(i2, yCenter, j2 + 1).isAir(world, i2, yCenter, j2 + 1)) {
                                            this.growVines(world, i2, yCenter, j2 + 1, 4);
                                        }
                                    }
                                }
                            }
                        }

                        if (random.nextInt(5) == 0 && treeHeight > 5) {
                            for (yCenter = 0; yCenter < 2; ++yCenter) {
                                for (i3 = 0; i3 < 4; ++i3) {
                                    if (random.nextInt(4 - yCenter) == 0) {
                                        l1 = random.nextInt(3);
                                        this.setBlockAndNotifyAdequately(world, x + Direction.offsetX[Direction.rotateOpposite[i3]], y + treeHeight - 5 + yCenter, z + Direction.offsetZ[Direction.rotateOpposite[i3]], Blocks.cocoa, l1 << 2 | i3);
                                    }
                                }
                            }
                        }
                    }

                    return true;
                }
                else {
                    return false;
                }
            }
        }
        else {
            /**
             * 树不会生成在世界内
             * */
            return false;
        }
    }

    /**
     * 从给定的块向下生长给定长度的藤蔓。
     */
    private void growVines(World world, int x, int startY, int z, int length) {
        this.setBlockAndNotifyAdequately(world, x, startY, z, Blocks.vine, length);
        int remainingLength = 4;

        while (true) {
            --startY;

            if (!world.getBlock(x, startY, z).isAir(world, x, startY, z) || remainingLength <= 0) {
                return;
            }

            this.setBlockAndNotifyAdequately(world, x, startY, z, Blocks.vine, length);
            --remainingLength;
        }
    }

    ////WorldGenAbstractTree
    protected boolean func_150523_a(Block block) {
        return F1(block);
    }
    private boolean F1(Block block) {
        return block.getMaterial() == Material.air || block.getMaterial() == Material.leaves || block == Blocks.grass || block == Blocks.dirt || block == Blocks.log || block == Blocks.log2 || block == Blocks.sapling || block == Blocks.vine;
    }

    public void func_150524_b(World world, Random random, int x, int y, int z) {
        F2(world, random, x, y, z);
    }

    private void F2(World world, Random random, int x, int y, int z) {

    }

    protected boolean isReplaceable(World world, int x, int y, int z)
    {
        Block block = world.getBlock(x, y, z);
        return block.isAir(world, x, y, z) || block.isLeaves(world, x, y, z) || block.isWood(world, x, y, z) || F1(block);
    }
    ////WorldGenAbstractTree
}
