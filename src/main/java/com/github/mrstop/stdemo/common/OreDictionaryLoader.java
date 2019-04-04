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
        OreDictionary.registerOre("dye", ItemLoader.seedsColorRed);
        OreDictionary.registerOre("dye", ItemLoader.seedsColorGreen);
        OreDictionary.registerOre("dye", ItemLoader.seedsColorBrown);
        OreDictionary.registerOre("dye", ItemLoader.seedsColorBlue);
        OreDictionary.registerOre("dye", ItemLoader.seedsColorYellow);
        OreDictionary.registerOre("dye", ItemLoader.seedsColorWhite);
        OreDictionary.registerOre("dyeBlack", ItemLoader.seedsColorBlack);
        OreDictionary.registerOre("dyeRed", ItemLoader.seedsColorRed);
        OreDictionary.registerOre("dyeGreen", ItemLoader.seedsColorGreen);
        OreDictionary.registerOre("dyeBrown", ItemLoader.seedsColorBrown);
        OreDictionary.registerOre("dyeBlue", ItemLoader.seedsColorBlue);
        OreDictionary.registerOre("dyeYellow", ItemLoader.seedsColorYellow);
        OreDictionary.registerOre("dyeWhite", ItemLoader.seedsColorWhite);

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
