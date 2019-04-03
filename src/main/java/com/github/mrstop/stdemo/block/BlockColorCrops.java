package com.github.mrstop.stdemo.block;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.core.util.STDemoHelper;
import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import com.github.mrstop.stdemo.item.ItemLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Random;

public class BlockColorCrops extends BlockCrops {

    protected static final int maxGrowthStage = 7;

//    @SideOnly(Side.CLIENT)
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
        if (growStage > maxGrowthStage) {
            growStage = maxGrowthStage;
        }
        world.setBlockMetadataWithNotify(X, Y, Z,growStage, 2);
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX, float subY, float subZ) {
        if (worldIn.isRemote){
            return true;
        }
        else {
            for (int i : OreDictionary.getOreIDs(player.getHeldItem())) {
                if (OreDictionary.getOreID("dyeBlack") == i) {
                    if (Math.random() <= 0.2) {
                        player.addChatMessage(new ChatComponentText("Black!"));
                        worldIn.setBlock(x, y, z, BlockLoader.colorBlockBlack);
                    }
                }
            }
            return true;
        }
    }

    @Override
    public Item getItemDropped(int meta, Random rand, int fortune) {
        if (meta != 7) {
            return Item.getItemFromBlock(this);
        } else {
            return ItemLoader.color;
        }
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
        if (growStage > maxGrowthStage) {
            growStage = maxGrowthStage;
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
        STDemoHelper.registerIconArray(iconRegister, icons, "color_");
    }
}
