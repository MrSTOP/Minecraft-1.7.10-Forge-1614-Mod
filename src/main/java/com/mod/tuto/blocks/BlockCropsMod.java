package com.mod.tuto.blocks;

import com.mod.tuto.init.ItemMod;

import net.minecraft.block.BlockCrops;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class BlockCropsMod extends BlockCrops
{
    protected Item func_149866_i()
    {
        return ItemMod.seedTuto;
    }
    
    protected Item func_149865_P()
    {
        return Items.diamond;
    }
}