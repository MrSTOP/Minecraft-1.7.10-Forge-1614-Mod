package com.github.mrstop.stdemo.block;

import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import com.github.mrstop.stdemo.item.ItemLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

public class BlockColorCrops extends BlockCrops {

    protected int maxGrowthStage = 7;

    @SideOnly(Side.CLIENT)
    protected IIcon[] icons = new IIcon[8];

    public BlockColorCrops() {
        this.setTickRandomly(true);
        float f = 0.5F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
        this.setCreativeTab(CreativeTabsLoader.tabSTDemo);
        this.setUnlocalizedName("colorBlock");
        this.setHardness(0.0F);
        this.setStepSound(soundTypeGrass);
        this.disableStats();
    }

    @Override
    protected boolean canPlaceBlockOn(Block ground) {
        return ground == Blocks.farmland;
    }

    public void incrementGrowStage(World world, Random rand, int X, int Y, int Z) {
        int growStage = world.getBlockMetadata(X, Y, Z) + MathHelper.getRandomIntegerInRange(rand, 2, 5);
        if (growStage > maxGrowthStage)
        {
            growStage = maxGrowthStage;
        }
        world.setBlockMetadataWithNotify(X, Y, Z,growStage, 2);
    }

    @Override
    public Item getItemDropped(int meta, Random rand, int fortune) {
        if (meta != 7)
        {
            return Item.getItemFromBlock(this);
        }
        return ItemLoader.color;

    }

    @Override
    public int getRenderType() {
        return 1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int growState) {
        return icons[growState];
    }

    @Override
    public boolean canFertilize(World worldIn, int x, int y, int z, boolean isClient) {
        return worldIn.getBlockMetadata(x, y, z) != maxGrowthStage;
    }

    @Override
    public boolean shouldFertilize(World worldIn, Random random, int x, int y, int z) {
        return true;
    }

    @Override
    public void  fertilize(World worldIn, Random random, int x, int y, int z) {
        incrementGrowStage(worldIn, random, x, y, z);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        super.updateTick(world, x, y, z, rand);
        int growStage = world.getBlockMetadata(x, y, z) + 1;
        if (growStage > 7) {
            growStage = 7;
        }
        world.setBlockMetadataWithNotify(x, y, z, growStage, 2);
    }

    @Override
    protected Item getSeed() {
        return ItemLoader.seedsColor;
    }

    @Override
    protected Item getCrop() {
        return ItemLoader.color;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> ret = super.getDrops(world, x, y, z, metadata, fortune);

        if (metadata >= 7) {
            for (int i = 0; i < 3 + fortune; ++i) {
                if (world.rand.nextInt(15) <= metadata) {
                    ret.add(new ItemStack(this.getCrop(), 1, 0));
                }
            }
        }
        return ret;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.icons[0] = iconRegister.registerIcon("stdemo:color_0");
        this.icons[1] = iconRegister.registerIcon("stdemo:color_1");
        this.icons[2] = iconRegister.registerIcon("stdemo:color_2");
        this.icons[3] = iconRegister.registerIcon("stdemo:color_3");
        this.icons[4] = iconRegister.registerIcon("stdemo:color_4");
        this.icons[5] = iconRegister.registerIcon("stdemo:color_5");
        this.icons[6] = iconRegister.registerIcon("stdemo:color_6");
        this.icons[7] = iconRegister.registerIcon("stdemo:color_7");
    }
}
