package com.github.mrstop.stdemo.block;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import com.github.mrstop.stdemo.tileentity.TileEntityMetalFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.dispenser.IBlockSource;
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

public class BlockMetalFurnace extends BlockContainer
{

    @SideOnly(Side.CLIENT)
    private IIcon top;
    @SideOnly(Side.CLIENT)
    private IIcon front;

    private static boolean isBurning;
    private final boolean isBurningFlag;
    private final Random random = new Random();

    public BlockMetalFurnace(boolean isActive)
    {
        super(Material.iron);
        this.setUnlocalizedName("metalFurnace");
        this.setHardness(2.5F);
        this.setStepSound(Block.soundTypeMetal);
        if (!isActive)
        {
            this.setCreativeTab(CreativeTabsLoader.tabSTDemo);
        }
        this.isBurningFlag = isActive;
        if (isActive == true)
        {
            this.setLightLevel(1.0F);
        }
    }



    @Override
    public Item getItemDropped(int meta, Random random, int fortune)
    {
        return Item.getItemFromBlock(BlockLoader.metalFunaceInactive);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onBlockAdded(World worldIn, int x, int y, int z)
    {
        super.onBlockAdded(worldIn, x, y, z);
        this.onMetalFurnaceAdded(worldIn, x, y, z);
    }
    //功能不明
    private void onMetalFurnaceAdded(World world, int blockX, int blockY, int blockZ)
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

    //在块激活时调用（右键单击该块）。 参数：world，x，y，z，player，side，hitX，hitY，hitZ。 返回：握手(Swing hand)（客户端），终止方块放置（服务器）
    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX, float subY, float subZ)
    {
        if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
            TileEntityMetalFurnace tileEntityMetalfurnace = (TileEntityMetalFurnace)worldIn.getTileEntity(x, y, z);

            if (tileEntityMetalfurnace != null)
            {
                //System.out.println("OpenGui!!");
                player.openGui(STDemo.instance, STDemo.GUIIDMetalFurnace, worldIn, x, y, z);
            }
            return true;
        }
    }
    //用途未知
    //根据是否燃烧来更新炉子对应的方块
    public static void updateMentalFurnaceBlockState(boolean isBurningFlag, World world, int blockX, int blockY, int blockZ)
    {
        int metadata = world.getBlockMetadata(blockX, blockY, blockZ);                                                  //获取方块当前Metadata
        TileEntity tileentity = world.getTileEntity(blockX, blockY, blockZ);                                            //临时TileEntity
        isBurning = true;                                                                                               //设置燃烧状态为true
        if (isBurningFlag)                                                                                              //判断燃烧状态是否为true
        {
            world.setBlock(blockX, blockY, blockZ, BlockLoader.metalFunaceActive);                                      //将方块设为燃烧状态
        }
        else
        {
            world.setBlock(blockX, blockY, blockZ, BlockLoader.metalFunaceInactive);                                    //将方块设为未燃烧状态
        }
        isBurning = false;                                                                                              //设置燃烧状态为false
        world.setBlockMetadataWithNotify(blockX, blockY, blockZ, metadata, 2);                              //设置方块Metadata
        if (tileentity != null)
        {
            tileentity.validate();
            world.setTileEntity(blockX, blockY, blockZ, tileentity);
        }
    }

    //返回方块的TileEntity类的新实例，在放置方块时调用
    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityMetalFurnace();
    }

    //在方块被放置在世界中时调用。设置方块Metadata
    @Override
    public void onBlockPlacedBy(World worldIn, int x, int y, int z, EntityLivingBase placer, ItemStack itemIn)
    {
        int sideFlag = MathHelper.floor_double((double)(placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

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
        if (itemIn.hasDisplayName())
        {
            ((TileEntityMetalFurnace)worldIn.getTileEntity(x, y, z)).setCustomInventoryName(itemIn.getDisplayName());
        }
    }

    //功能不明/*破坏方块时抛出物品的掉落物，似乎会损失*/
    @Override
    public void breakBlock(World worldIn, int x, int y, int z, Block blockBroken, int meta)
    {
        if (!isBurning)                                                                                                 //判断是否在燃烧
        {
            TileEntityMetalFurnace tileentitymetalfurnace = (TileEntityMetalFurnace) worldIn.getTileEntity(x, y, z);    //临时TileEntityMetalFurnace变量

            if (tileentitymetalfurnace != null)                                                                         //判断临时TileEntityMetalFurnace变量是否为空
            {
                for (int i1 = 0; i1 < tileentitymetalfurnace.getSizeInventory(); ++i1)                                  //遍历物品槽
                {
                    ItemStack itemstack = tileentitymetalfurnace.getStackInSlot(i1);                                    //临时ItemStack变量

                    if (itemstack != null)                                                                              //判断临时ItemStack变量是否为空
                    {
                        float fX = this.random.nextFloat() * 0.8F + 0.1F;
                        float fY = this.random.nextFloat() * 0.8F + 0.1F;
                        float fZ = this.random.nextFloat() * 0.8F + 0.1F;

                        while (itemstack.stackSize > 0)
                        {
                            int j1 = this.random.nextInt(21) + 10;

                            if (j1 > itemstack.stackSize)
                            {
                                j1 = itemstack.stackSize;
                            }

                            itemstack.stackSize -= j1;
                            EntityItem entityitem = new EntityItem(worldIn, (double)((float)x + fX), (double)((float)y + fY), (double)((float)z + fZ), new ItemStack(itemstack.getItem(), j1, itemstack.getMetadata()));

                            if (itemstack.hasTagCompound())
                            {
                                entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                            }

                            float rand = 0.05F;
                            entityitem.motionX = (double)((float)this.random.nextGaussian() * rand);
                            entityitem.motionY = (double)((float)this.random.nextGaussian() * rand + 0.2F);
                            entityitem.motionZ = (double)((float)this.random.nextGaussian() * rand);
                            worldIn.spawnEntityInWorld(entityitem);
                        }
                    }
                }
                worldIn.updateNeighborsAboutBlockChange(x, y, z, blockBroken);
            }
        }
        super.breakBlock(worldIn, x, y, z, blockBroken, meta);
    }

    //随机调用的显示更新，可以添加粒子或其他项目进行显示
    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World worldIn, int x, int y, int z, Random random)
    {
        if (isBurningFlag)
        {
            int l = worldIn.getBlockMetadata(x, y, z);
            float f = (float)x + 0.5F;
            float f1 = (float)y + 0.0F + random.nextFloat() * 6.0F / 16.0F;
            float f2 = (float)z + 0.5F;
            float f3 = 0.52F;
            float f4 = random.nextFloat() * 0.6F - 0.3F;

            if (l == 4)
            {
                worldIn.spawnParticle("smoke", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                worldIn.spawnParticle("flame", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
            }
            else if (l == 5)
            {
                worldIn.spawnParticle("smoke", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                worldIn.spawnParticle("flame", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
            }
            else if (l == 2)
            {
                worldIn.spawnParticle("smoke", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0D, 0.0D, 0.0D);
                worldIn.spawnParticle("flame", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0D, 0.0D, 0.0D);
            }
            else if (l == 3)
            {
                worldIn.spawnParticle("smoke", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0D, 0.0D, 0.0D);
                worldIn.spawnParticle("flame", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    //具有比较器输入
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
        this.blockIcon = register.registerIcon("stdemo:metalFurnace_side");
        this.front = register.registerIcon(this.isBurningFlag ? "stdemo:metalFurnace_active" : "stdemo:metalFurnace_inactive");
        this.top = register.registerIcon("stdemo:metalFurnace_top");
    }

    //创造模式鼠标中键获取物品
    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, int x, int y, int z)
    {
        return Item.getItemFromBlock(BlockLoader.metalFunaceInactive);
    }
}
