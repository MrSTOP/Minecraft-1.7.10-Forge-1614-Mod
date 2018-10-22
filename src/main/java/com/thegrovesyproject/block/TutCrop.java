package com.thegrovesyproject.block;

import net.minecraft.block.BlockCrops;
import net.minecraft.item.Item;

import com.thegrovesyproject.item.TMItem;

public class TutCrop extends BlockCrops{

	
	/**
	 * seeds
	 */
	@Override
	protected Item getSeed()
    {
        return TMItem.tutSeed;
    }

	/**
	 * crop
	 */
	@Override
    protected Item getCrop()
    {
        return TMItem.tutCrop;
    }
	
}
