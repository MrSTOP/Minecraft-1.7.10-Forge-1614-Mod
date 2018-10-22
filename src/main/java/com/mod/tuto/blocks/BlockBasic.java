package com.mod.tuto.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBasic extends Block
{

    public BlockBasic(Material p_i45394_1_, int level)
    {
        super(p_i45394_1_);
        this.setHarvestLevel("pickaxe", level);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.8F, 0.5F, 1.0F);
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
}
