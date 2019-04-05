package com.github.mrstop.stdemo.block;

import com.github.mrstop.stdemo.core.util.STDemoHelper;
import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

import java.util.ArrayList;
import java.util.Random;

public abstract class BlockColorFlower extends BlockBush implements IGrowable {

    protected static final int MAX_GROWTH_STAGE = 7;

    protected IIcon[] icons = new IIcon[MAX_GROWTH_STAGE + 1];

    public BlockColorFlower(String unlocalizedName) {
        this(unlocalizedName, CreativeTabsLoader.tabSTDemo);
    }

    public BlockColorFlower(String unlocalizedName, CreativeTabs creativeTabs) {
        this(unlocalizedName, creativeTabs, Material.plants);
    }
    public BlockColorFlower(String unlocalizedName, CreativeTabs creativeTabs, Material material) {
        super(material);
        this.setTickRandomly(true);
        this.disableStats();
        float f = 0.5F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
        this.setCreativeTab(creativeTabs);
        this.setUnlocalizedName(unlocalizedName);
        this.setHardness(0.0F);
        this.setStepSound(soundTypeGrass);
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
    public void updateTick(World world, int x, int y, int z, Random rand) {
        super.updateTick(world, x, y, z, rand);
        int growStageAdd = 0;
        if (world.getBlockLightValue(x, y + 1, z) >= 9) {
            if (world.rand.nextFloat() <= 0.2) {
                growStageAdd = 1;
            } else {
                growStageAdd = 0;
            }
            int growStage = world.getBlockMetadata(x, y, z) + growStageAdd;
            if (growStage > MAX_GROWTH_STAGE) {
                growStage = MAX_GROWTH_STAGE;
            }
            world.setBlockMetadataWithNotify(x, y, z, growStage, 2);
        }
    }

    //检测方块是否能继续存在，不能则以物品形式掉落
    @Override
    protected void checkAndDropBlock(World worldIn, int x, int y, int z) {
        super.checkAndDropBlock(worldIn, x, y, z);
    }

    @Override
    public boolean canBlockStay(World worldIn, int x, int y, int z) {
        return worldIn.getBlock(x, y - 1, z) == Blocks.farmland;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World worldIn, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return 1;
    }
    ////BlockCrops

    public void incrementGrowStage(World world, Random rand, int X, int Y, int Z) {
        int growStageAdd = 0;
        float percentage = rand.nextFloat();
        if (percentage <= 0.3) {
            growStageAdd = 1;
        } else if (percentage >= 0.9) {
            growStageAdd = 2;
        } else {
            growStageAdd = 0;
        }
        int growStage = world.getBlockMetadata(X, Y, Z) + growStageAdd;
        if (growStage > MAX_GROWTH_STAGE) {
            growStage = MAX_GROWTH_STAGE;
        }
        world.setBlockMetadataWithNotify(X, Y, Z,growStage, 2);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int growState) {
        if (growState < 0 || growState > MAX_GROWTH_STAGE) {
            return icons[0];
        }
        return icons[growState];
    }

    //创造模式中键获取物品
    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, int x, int y, int z) {
        return this.getSeed();
    }


    abstract protected Item getSeed();

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> drops = new ArrayList<>();
        drops.add(new ItemStack(this.getSeed(), 1));
        if (metadata >= MAX_GROWTH_STAGE) {
            for (int i = 0; i < 3 + fortune; i++) {
                if (world.rand.nextFloat() < 0.3) {
                    drops.add(new ItemStack(this.getSeed(), world.rand.nextInt(4)));
                }
            }
        }
        return drops;
    }

    ////IGrowable
    @Override
    public boolean canFertilize(World worldIn, int x, int y, int z, boolean isClient) {
        return worldIn.getBlockMetadata(x, y, z) != MAX_GROWTH_STAGE;
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
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iIconRegister){
        STDemoHelper.registerIconArray(iIconRegister, icons, "color_flower_", 0, 7);
        this.registerLastIcons(iIconRegister, MAX_GROWTH_STAGE);
    }

    abstract protected void registerLastIcons(IIconRegister iIconRegister, int index);
}
