package com.github.mrstop.stdemo.block;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import com.github.mrstop.stdemo.tileentity.TileEntityRedstoneFluxFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class BlockRedstoneFluxFurnace extends BlockContainer {

    @SideOnly(Side.CLIENT)
    private IIcon top;
    @SideOnly(Side.CLIENT)
    private IIcon front;

    private static boolean isBurning;
    private final boolean isBurningFlag;
    private final Random random = new Random();

    public BlockRedstoneFluxFurnace(boolean isActive)
    {
        super(Material.iron);
        this.setUnlocalizedName("redstoneFluxFurnace");
        this.setHardness(0.8F);
        this.setStepSound(soundTypeStone);
        this.setHarvestLevel("pickaxe", 1);
        this.isBurningFlag = isActive;
        if (!isActive)
        {
            this.setCreativeTab(CreativeTabsLoader.tabSTDemo) ;
        }
        if (isActive)
        {
            this.setLightLevel(5.0F);
        }
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune)
    {
        return Item.getItemFromBlock(BlockLoader.getRedstoneFluxFurnaceInactive);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);
        this.onRedstoneFluxFurnaceAdded(world, x, y, z);
    }

    private void onRedstoneFluxFurnaceAdded(World world, int blockX, int blockY, int blockZ)
    {
        if (!world.isRemote)                                                                                            //判断是否为客户端
        {
            Block block = world.getBlock(blockX, blockY, blockZ - 1);                                      //获得金属熔炉北面的方块
            Block block1 = world.getBlock(blockX, blockY, blockZ + 1);                                     //获得金属熔炉南面的方块
            Block block2 = world.getBlock(blockX - 1, blockY, blockZ);                                     //获得金属熔炉西面的方块
            Block block3 = world.getBlock(blockX + 1, blockY, blockZ);                                     //获得金属熔炉东面的方块
            byte byte0 = 3;

            if (block.isFullBlock() && !block1.isFullBlock())
            {
                byte0 = 3;
            }

            if (block1.isFullBlock() && !block.isFullBlock())
            {
                byte0 = 2;
            }

            if (block2.isFullBlock() && !block3.isFullBlock())
            {
                byte0 = 5;
            }

            if (block3.isFullBlock() && !block2.isFullBlock())
            {
                byte0 = 4;
            }

            world.setBlockMetadataWithNotify(blockX, blockY, blockZ, byte0, 2);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX, float subY, float subZ)
    {
        if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
            TileEntityRedstoneFluxFurnace tileEntityRedstoneFluxFurnace = (TileEntityRedstoneFluxFurnace) worldIn.getTileEntity(x, y, z);

            if (tileEntityRedstoneFluxFurnace != null)
            {
                player.openGui(STDemo.instance, STDemo.GUIIDRedstoneFluxFurnace, worldIn, x, y, z);
            }
            return true;
        }
    }

    public static void updateRedstoneFluxFurnace(boolean isBurningFlag, World world, int blockX, int blockY, int blockZ)
    {
        int metadata = world.getBlockMetadata(blockX, blockY, blockZ);
        TileEntity tileEntity = world.getTileEntity(blockX, blockY, blockZ);
        //isBurning = true;
        if (isBurningFlag)
        {
            world.setBlock(blockX, blockY, blockZ, BlockLoader.redstoneFluxFurnaceActive);
        }
        else
       {
           world.setBlock(blockX, blockY, blockZ, BlockLoader.getRedstoneFluxFurnaceInactive);
       }
       //isBurning = false;
        world.setBlockMetadataWithNotify(blockX, blockY, blockZ, metadata, 2);
        if (tileEntity != null)
        {
            tileEntity.validate();
            world.setTileEntity(blockX, blockY ,blockZ, tileEntity);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityRedstoneFluxFurnace();
    }

    @Override
    public void onBlockPlacedBy(World worldIn, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack itemStack)
    {
        int sideFlag = MathHelper.floor_double((double)(entityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        if (sideFlag == 0)
        {
            worldIn.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }
        if (sideFlag == 1)
        {
            worldIn.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }
        if (sideFlag == 2)
        {
            worldIn.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }
        if (sideFlag == 3)
        {
            worldIn.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }
        if (itemStack.hasDisplayName())
        {
            ((TileEntityRedstoneFluxFurnace) worldIn.getTileEntity(x, y, z)).setCustomInventoryName(itemStack.getDisplayName());
        }
    }

    @Override
    public void breakBlock(World worldIn, int x, int y, int z, Block blockBroken, int meta)
    {
            if (!isBurning) {
                TileEntityRedstoneFluxFurnace tileEntityRedstoneFluxFurnace = (TileEntityRedstoneFluxFurnace) worldIn.getTileEntity(x, y, z);    //临时TileEntityMetalFurnace变量

                if (tileEntityRedstoneFluxFurnace != null)                                                                         //判断临时TileEntityMetalFurnace变量是否为空
                {
                    for (int i1 = 0; i1 < tileEntityRedstoneFluxFurnace.getSizeInventory(); ++i1)                                  //遍历物品槽
                    {
                        ItemStack itemstack = tileEntityRedstoneFluxFurnace.getStackInSlot(i1);                                    //临时ItemStack变量

                        if (itemstack != null)                                                                              //判断临时ItemStack变量是否为空
                        {
                            float fX = this.random.nextFloat() * 0.8F + 0.1F;
                            float fY = this.random.nextFloat() * 0.8F + 0.1F;
                            float fZ = this.random.nextFloat() * 0.8F + 0.1F;

                            while (itemstack.stackSize > 0) {
                                int j1 = this.random.nextInt(21) + 10;

                                if (j1 > itemstack.stackSize) {
                                    j1 = itemstack.stackSize;
                                }

                                itemstack.stackSize -= j1;
                                EntityItem entityitem = new EntityItem(worldIn, (double) ((float) x + fX), (double) ((float) y + fY), (double) ((float) z + fZ), new ItemStack(itemstack.getItem(), j1, itemstack.getMetadata()));

                                if (itemstack.hasTagCompound()) {
                                    entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                                }

                                float rand = 0.05F;
                                entityitem.motionX = (double) ((float) this.random.nextGaussian() * rand);
                                entityitem.motionY = (double) ((float) this.random.nextGaussian() * rand + 0.2F);
                                entityitem.motionZ = (double) ((float) this.random.nextGaussian() * rand);
                                worldIn.spawnEntityInWorld(entityitem);
                            }
                        }
                    }
                worldIn.updateNeighborsAboutBlockChange(x, y, z, blockBroken);
            }
        }
    }

    @Override
    public boolean hasComparatorInputOverride()
    {
        return true;
    }

    //获得比较器输入
    @Override
    public int getComparatorInputOverride(World worldIn, int x, int y, int z, int side)
    {
        return Container.calcRedstoneFromInventory((IInventory)worldIn.getTileEntity(x, y, z));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return meta == 0 ? (side == 1 ? this.top : (side == 3 ? this.front : this.blockIcon)) : (side == 1 ? this.top : (side != meta ? this.blockIcon : this.front));
        //如果meta为0且side为1(顶面)返回top
        //如果meta为0且side为3(南面)返回front
        //如果meta为0且side不等于3(南面)返回blockIcon
        //如果meta不等于0且side等于1(顶面)返回top
        //如果meta不等于0且side不等于meta返回blockIcon
        //如果meta不等于0且side等于meta返回front
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        this.blockIcon = register.registerIcon("stdemo:redstoneFluxFurnace_side");
        this.front = register.registerIcon(this.isBurningFlag ? "stdemo:redstoneFluxFurnace_active" : "stdemo:RedstoneFluxlFurnace_inactive");
        this.top = register.registerIcon("stdemo:redstoneFluxFurnace_top");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, int x, int y, int z)
    {
        return Item.getItemFromBlock(BlockLoader.getRedstoneFluxFurnaceInactive);
    }
}
