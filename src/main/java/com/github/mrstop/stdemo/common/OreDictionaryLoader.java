package com.github.mrstop.stdemo.common;


import java.util.List;

import com.github.mrstop.stdemo.block.BlockLoader;
import com.github.mrstop.stdemo.item.ItemLoader;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;


public class OreDictionaryLoader {

    public OreDictionaryLoader(FMLPreInitializationEvent  event) {
        List<ItemStack> dustRedstones = OreDictionary.getOres("dustRedstone");
        List<ItemStack> dustGlowstones = OreDictionary.getOres("dustGlowstone");
        for (ItemStack itemStack : dustGlowstones) {
            OreDictionary.registerOre("dustRedstone", itemStack);
        }
        for (ItemStack itemStack : dustRedstones) {
            OreDictionary.registerOre("dustGlowstone", itemStack);
        }

        OreDictionary.registerOre("oreChromite", BlockLoader.chromiteOre);
        OreDictionary.registerOre("oreCinnabar", BlockLoader.cinnabarOre);
        OreDictionary.registerOre("ingotCopper", ItemLoader.chromiteIngot);
        OreDictionary.registerOre("dye", ItemLoader.seedsColorBlack);
//        OreDictionary.registerOre("dyeBlack", ItemLoader.seedsColorBlack);
//        OreDictionary.registerOre("dyeRed", ItemLoader.seedsColorBlack);
//        OreDictionary.registerOre("dyeGreen", ItemLoader.seedsColorBlack);
//        OreDictionary.registerOre("dyeBrown", ItemLoader.seedsColorBlack);
//        OreDictionary.registerOre("dyeBlue", ItemLoader.seedsColorBlack);
//        OreDictionary.registerOre("dyePurple", ItemLoader.seedsColorBlack);
//        OreDictionary.registerOre("dyeCyan", ItemLoader.seedsColorBlack);
//        OreDictionary.registerOre("dyeLightGray", ItemLoader.seedsColorBlack);
//        OreDictionary.registerOre("dyeGray", ItemLoader.seedsColorBlack);
//        OreDictionary.registerOre("dyePink", ItemLoader.seedsColorBlack);
//        OreDictionary.registerOre("dyeLime", ItemLoader.seedsColorBlack);
//        OreDictionary.registerOre("dyeYellow", ItemLoader.seedsColorBlack);
//        OreDictionary.registerOre("dyeLightBlue", ItemLoader.seedsColorBlack);
//        OreDictionary.registerOre("dyeMagenta", ItemLoader.seedsColorBlack);
//        OreDictionary.registerOre("dyeOrange", ItemLoader.seedsColorBlack);
//        OreDictionary.registerOre("dyeWhite", ItemLoader.seedsColorBlack);

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
