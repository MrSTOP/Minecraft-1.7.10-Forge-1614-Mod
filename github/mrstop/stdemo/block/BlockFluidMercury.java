package com.github.mrstop.stdemo.block;

import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import com.github.mrstop.stdemo.fluid.FluidLoader;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.BlockFluidClassic;


public class BlockFluidMercury extends BlockFluidClassic {

    @SideOnly(Side.CLIENT)
    protected IIcon stillIcon;
    @SideOnly(Side.CLIENT)
    protected IIcon flowingIcon;

    public BlockFluidMercury()
    {
        super(FluidLoader.fluidMercury, Material.water);
        this.setUnlocalizedName("fluidMercury");
        this.setCreativeTab(CreativeTabsLoader.tabSTDemo);
    }

    /*////////////////////////////////////////////////////////
    @Override public IIcon getIcon(int side, int meta){
        return (side == 0 || side == 1) ? stillIcon:flowingIcon;
    }

    @SideOnly(Side.CLIENT)
    @Override public void registerIcons(IIconRegister register) {
        stillIcon = register.registerIcon("testmod:fluidStill");
        flowingIcon = register.registerIcon("testmod:fluidFlowing");
    }
    @Override public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
        if (world.getBlock(x, y, z).getMaterial().isLiquid()) return false;
        return super.canDisplace(world, x, y, z);
    }

    @Override public boolean displaceIfPossible(World world, int x, int y, int z) {
        if (world.getBlock(x, y, z).getMaterial().isLiquid()) return false;
        return super.displaceIfPossible(world, x, y, z);
    }
    */////////////////////////////////////////////////////////


    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        //System.out.println("SIDE: " + side + "META:" + meta);
        return (side == 0 || side == 1) ? stillIcon:flowingIcon;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister register)
    {
        stillIcon = register.registerIcon("stdemo:mercury_still");
        flowingIcon = register.registerIcon("stdemo:mercury_flow");
        //FluidLoader.fluidMercury.setFlowingIcon(flowingIcon);
        //FluidLoader.fluidMercury.setStillIcon(stillIcon);
    }

    @Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z)
    {
        if (world.getBlock(x, y, z).getMaterial().isLiquid())
        {
            return false;
        }
        else
        {
            return super.canDisplace(world, x, y, z);
        }
    }

    @Override
    public boolean displaceIfPossible(World world, int x, int y, int z)
    {
        if (world.getBlock(x, y, z).getMaterial().isLiquid())
        {
            return false;
        }
        else
        {
            return super.displaceIfPossible(world, x, y, z);
        }
    }
}
