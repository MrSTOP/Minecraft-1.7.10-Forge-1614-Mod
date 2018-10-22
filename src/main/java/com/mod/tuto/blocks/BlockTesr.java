package com.mod.tuto.blocks;

import java.util.List;

import com.mod.tuto.proxy.ClientProxy;
import com.mod.tuto.tileentity.TileEntityBlockTesr;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockTesr extends Block
{    
    public BlockTesr(Material p_i45394_1_)
    {
        super(p_i45394_1_);
    }
    
    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
        return new TileEntityBlockTesr();
    }
    
    @Override
    public boolean hasTileEntity(int metadata)
    {
        return true;
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    public int getRenderType()
    {
        return ClientProxy.renderTesr;
    }
    
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack)
    {
        if(stack.getMetadata() == 0)
        {
            TileEntity tile = world.getTileEntity(x, y, z);
            if(tile instanceof TileEntityBlockTesr)
            {
                int direction = MathHelper.floor_double((double)(living.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
                ((TileEntityBlockTesr)tile).setDirection((byte)direction);
            }
        }
    }
    
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity)
    {
        if(world.getBlockMetadata(x, y, z) == 0)
        {
            this.setBlockBounds(0F, 0F, 0F, 0.5F, 0.5F, 1F);
            super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
            this.setBlockBounds(0F, 0F, 0F, 0.4F, 0.2F, 1F);
            super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
            this.setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
        }
    }
}