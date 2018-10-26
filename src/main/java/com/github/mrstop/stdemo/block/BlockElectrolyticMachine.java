package com.github.mrstop.stdemo.block;

import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import com.github.mrstop.stdemo.tileentity.TileEntityElectrolyticMachine;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockElectrolyticMachine extends BlockContainer {

    public BlockElectrolyticMachine() {
        super(Material.rock);
        this.setUnlocalizedName("electrolyticMachine");
        this.setHardness(0.5F);
        this.setStepSound(soundTypeStone);
        this.setHarvestLevel("pickaxe", 1);
        this.setCreativeTab(CreativeTabsLoader.tabSTDemo);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityElectrolyticMachine();
    }
}
