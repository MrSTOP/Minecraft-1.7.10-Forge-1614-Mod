package com.github.mrstop.stdemo.common;


import java.util.List;

import com.github.mrstop.stdemo.block.BlockLoader;
import com.github.mrstop.stdemo.item.ItemLoader;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;


public class OreDictionaryLoader {

    public OreDictionaryLoader(FMLPreInitializationEvent  event)
    {
        List<ItemStack> dustRedstones = OreDictionary.getOres("dustRedstone");
        List<ItemStack> dustGlowstones = OreDictionary.getOres("dustGlowstone");
        for (ItemStack itemStack : dustGlowstones)
        {
            OreDictionary.registerOre("dustRedstone", itemStack);
        }
        for (ItemStack itemStack : dustRedstones)
        {
            OreDictionary.registerOre("dustGlowstone", itemStack);
        }

        OreDictionary.registerOre("oreChromite", BlockLoader.chromiteBlock);

        OreDictionary.registerOre("ingotCopper", ItemLoader.chromiteIngot);

        /*
        Log.Log.info("Registed Ore:");
        String[] OreList = OreDictionary.getOreNames();
        for (int i = 0; i < OreList.length; i++)
        {
            Log.Log.info(OreList[i]);
        }
        */
    }

}
