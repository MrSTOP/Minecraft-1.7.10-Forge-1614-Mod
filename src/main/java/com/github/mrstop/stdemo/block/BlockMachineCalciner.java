package com.github.mrstop.stdemo.block;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import com.github.mrstop.stdemo.tileentity.TileEntityMachineCalciner;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
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
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX, float subY, float subZ) {
        if (worldIn.isRemote){
            return true;
        }
        else {
            TileEntityMachineCalciner tileEntityMachineCalciner = (TileEntityMachineCalciner) worldIn.getTileEntity(x, y, z);
            if (tileEntityMachineCalciner != null){
                player.openGui(STDemo.instance, STDemo.GUIIDMachineCalciner, worldIn, x, y, z);
            }
            return true;
        }
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityMachineCalciner();
    }
}
