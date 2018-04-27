package com.github.mrstop.stdemo.block;

import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import com.github.mrstop.stdemo.tileentity.TileEntityMetalFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMetalFurnace extends BlockContainer {


    public BlockMetalFurnace()
    {
        super(Material.iron);
        this.setUnlocalizedName("metalFurnace");
        this.setHardness(2.5F);
        this.setStepSound(Block.soundTypeMetal);
        this.setCreativeTab(CreativeTabsLoader.tabSTDemo);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityMetalFurnace();
    }
}
