package com.github.mrstop.stdemo.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CreativeTabsLoader {
    public static CreativeTabs tabSTDemo;
    public CreativeTabsLoader(FMLPreInitializationEvent event) {
         tabSTDemo = new CreativeTabsSTDemo();
     }

}
