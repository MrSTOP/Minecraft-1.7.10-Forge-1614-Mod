package com.github.mrstop.stdemo.block;

import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import com.github.mrstop.stdemo.tileentity.TileEntityQuartzFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockQuartzFurnace extends BlockContainer {
    private boolean isBurning;
    @SideOnly(Side.CLIENT)
    private IIcon topBottom;
    @SideOnly(Side.CLIENT)
    private IIcon front;

    public BlockQuartzFurnace(boolean isActive) {
        super(Material.rock);
        this.setUnlocalizedName("quartzFurnace");
        this.setHardness(1.0F);
        this.setStepSound(soundTypeSnow);
        this.setHarvestLevel("pickaxe", 2);
        this.isBurning = isActive;
        if (isActive){
            this.setLightLevel(5.0F);
        }
        else {
            this.setCreativeTab(CreativeTabsLoader.tabSTDemo);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (side == 0 || side == 1){
            return this.topBottom;
        }
        else {
            if (meta != 0) {
                if (side == meta){
                    return this.front;
                }
            }
            else {
                if (side == 3){
                    return this.front;
                }
            }
        }
        return this.blockIcon;
    }

    @Override
    public void onBlockAdded(World worldIn, int x, int y, int z) {
        super.onBlockAdded(worldIn, x, y, z);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, int x, int y, int z, EntityLivingBase placer, ItemStack itemIn) {
        int sideFlag = MathHelper.floor_double((double)(placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        switch (sideFlag){
            case 0:
                worldIn.setBlockMetadataWithNotify(x, y, z, 2, 2);
                break;
            case 1:
                worldIn.setBlockMetadataWithNotify(x, y, z, 5, 2);
                break;
            case 2:
                worldIn.setBlockMetadataWithNotify(x, y, z, 3, 2);
                break;
            case 3:
                worldIn.setBlockMetadataWithNotify(x, y, z, 4, 2);
                break;
            default:
                break;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, int x, int y, int z) {
        return Item.getItemFromBlock(BlockLoader.quartzFurnaceInactive);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon("stdemo:quartz_furnace_side");
        this.topBottom = iconRegister.registerIcon("stdemo:quartz_furnace_top");
        this.front = iconRegister.registerIcon((this.isBurning ? "stdemo:quartz_furnace_front_active" : "stdemo:quartz_furnace_front_inactive"));
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityQuartzFurnace();
    }
}
