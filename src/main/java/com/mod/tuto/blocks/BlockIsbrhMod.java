package com.mod.tuto.blocks;

import java.util.List;

import com.mod.tuto.proxy.ClientProxy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockIsbrhMod extends Block
{
    public BlockIsbrhMod(Material p_i45394_1_)
    {
        super(p_i45394_1_);
    }
    
    @SideOnly(Side.CLIENT)
    public float getAmbientOcclusionLightValue()
    {
        return 1.0F;
    }
    
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public int getRenderType()
    {
        return ClientProxy.renderIsbrh;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess state, int x, int y, int z, int side)
    {
        return true;
    }
    
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity)
    {
        if(world.getBlockMetadata(x, y, z) == 0)
        {
            this.setBlockBounds(0F, 0F, 0F, 1F, 0.5F, 1F);
            super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
            this.setBlockBounds(1F, 0F, 0F, 0.5F, 1F, 1F);
            super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
            this.setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
        }
        
        if(world.getBlockMetadata(x, y, z) == 1)
        {
            this.setBlockBounds(0F, 0F, 0F, 1F, 0.5F, 1F);
            super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
            this.setBlockBounds(1F, 0F, 0F, 0.5F, 1F, 1F);
            super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
            this.setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
        }
        
        if(world.getBlockMetadata(x, y, z) == 2)
        {
            this.setBlockBounds(0F, 0F, 0F, 1F, 0.5F, 1F);
            super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
            this.setBlockBounds(1F, 0F, 0F, 0.5F, 1F, 1F);
            super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
            this.setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
        }
        
        if(world.getBlockMetadata(x, y, z) == 3)
        {
            this.setBlockBounds(0F, 0F, 0F, 1F, 0.5F, 1F);
            super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
            this.setBlockBounds(1F, 0F, 0F, 0.5F, 1F, 1F);
            super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
            this.setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
        }
    }
    
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack)
    {
        int direction = MathHelper.floor_double((double)(living.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, direction, 2);
    }
}