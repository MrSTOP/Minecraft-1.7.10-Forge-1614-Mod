package com.github.mrstop.stdemo.block;

import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import com.github.mrstop.stdemo.tileentity.TileEntityMachineCalciner;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMachineCalciner extends BlockContainer {
    public BlockMachineCalciner() {
        super(Material.iron);
        this.setCreativeTab(CreativeTabsLoader.tabSTDemo);
        this.setUnlocalizedName("machineCalciner");
        this.setHarvestLevel("pickaxe", 1);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityMachineCalciner();
    }
}