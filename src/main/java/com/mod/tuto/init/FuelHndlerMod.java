package com.mod.tuto.init;

import cpw.mods.fml.common.IFuelHandler;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FuelHndlerMod implements IFuelHandler
{
    @Override
    public int getBurnTime(ItemStack fuel)
    {
        if(fuel.getItem() == Items.diamond)
        {
            return 1000;
        }
        
        else if(fuel.getItem() == Item.getItemFromBlock(BlockMod.block_test))
        {
            return 60;
        }
        return 0;
    }
}