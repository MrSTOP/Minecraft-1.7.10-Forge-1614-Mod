package com.github.mrstop.stdemo.block;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.core.util.STDemoHelper;
import com.github.mrstop.stdemo.item.ItemLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Random;

public class BlockColorCropsBlack extends BlockColorCrops {

    public BlockColorCropsBlack() {
        super();
        this.setUnlocalizedName("colorBlackBlock");
    }




    ////BlockCrops
    @Override
    public boolean canPlaceBlockAt(World worldIn, int x, int y, int z) {
        return super.canPlaceBlockAt(worldIn, x, y, z);
    }

    @Override
    protected boolean canPlaceBlockOn(Block ground) {
        return ground == Blocks.farmland;
    }

    @Override
    public void onNeighborBlockChange(World worldIn, int x, int y, int z, Block neighbor) {
        super.onNeighborBlockChange(worldIn, x, y, z, neighbor);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        super.updateTick(world, x, y, z, rand);
        int growStage = world.getBlockMetadata(x, y, z) + 1;
        if (growStage > maxGrowthStage) {
            growStage = maxGrowthStage;
        }
        world.setBlockMetadataWithNotify(x, y, z, growStage, 2);

    }

    //检测方块是否能继续存在，不能则以物品形式掉落
    @Override
    protected void checkAndDropBlock(World worldIn, int x, int y, int z) {
        super.checkAndDropBlock(worldIn, x, y, z);
    }

    @Override
    public boolean canBlockStay(World worldIn, int x, int y, int z) {
        return super.canBlockStay(worldIn, x, y, z);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World worldIn, int x, int y, int z) {
        return super.getCollisionBoundingBoxFromPool(worldIn, x, y, z);
    }

    @Override
    public boolean isOpaqueCube() {
        return super.isOpaqueCube();
    }

    @Override
    public boolean renderAsNormalBlock() {
        return super.renderAsNormalBlock();
    }

    @Override
    public int getRenderType() {
        return 1;
    }
    ////BlockCrops

    public void incrementGrowStage(World world, Random rand, int X, int Y, int Z) {
        int growStage = world.getBlockMetadata(X, Y, Z) + MathHelper.getRandomIntegerInRange(rand, 1, 3);
        if (growStage > maxGrowthStage) {
            growStage = maxGrowthStage;
        }
        world.setBlockMetadataWithNotify(X, Y, Z,growStage, 2);
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX, float subY, float subZ) {
//        if (worldIn.isRemote){
//            return true;
//        }
//        else {
//            return true;
//        }
        return false;
    }



    @Override
    public Item getItemDropped(int meta, Random rand, int fortune) {
        return meta == maxGrowthStage ? this.getCrop() : getSeed();
    }

    @Override
    public int quantityDropped(Random random) {
        return super.quantityDropped(random);
    }


    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int growState) {
//        System.out.println("METADATA:" + growState);
        if (growState < 0 || growState > maxGrowthStage) {
            return icons[0];
        }
        return icons[growState];
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, int x, int y, int z, int meta, float chance, int fortune) {
        super.dropBlockAsItemWithChance(worldIn, x, y, z, meta, chance, fortune);
    }

    //创造模式中键获取物品
    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, int x, int y, int z) {
        return this.getSeed();
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> drops = super.getDrops(world, x, y, z, metadata, fortune);
        if (metadata >= 7) {
            for (int i = 0; i < 3 + fortune; ++i) {
                if (world.rand.nextInt(15) <= metadata) {
                    drops.add(new ItemStack(ItemLoader.bestSword, 1, 0));
                }
            }
        }
        return drops;
    }

    ////IGrowable
    @Override
    public boolean canFertilize(World worldIn, int x, int y, int z, boolean isClient) {
        return worldIn.getBlockMetadata(x, y, z) != maxGrowthStage;
    }

    @Override
    public boolean shouldFertilize(World worldIn, Random random, int x, int y, int z) {
        return true;
    }

    @Override
    public void fertilize(World worldIn, Random random, int x, int y, int z) {
        incrementGrowStage(worldIn, random, x, y, z);
    }
    ////IGrowable

    ////IPlantable
    //作物类型（可以种植的环境）
    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        return EnumPlantType.Plains;
    }

    @Override
    public Block getPlant(IBlockAccess world, int x, int y, int z) {
        return this;
    }

    @Override
    public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z);
    }
    ////IPlantable












    @Override
    protected Item getSeed() {
        return ItemLoader.seedsColorBlack;
    }

    @Override
    protected Item getCrop() {
        return ItemLoader.color;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        STDemoHelper.registerIconArray(iconRegister, icons, "color_crop_", 0, icons.length - 1);
        icons[icons.length - 1] = iconRegister.registerIcon(STDemo.MOD_DOMAIN + "color_black");
    }
}
