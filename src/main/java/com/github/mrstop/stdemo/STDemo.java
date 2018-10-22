package com.github.mrstop.stdemo;

import com.github.mrstop.stdemo.common.CommonProxy;


import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

/**
 * @author 闫坤炜
 */

@Mod(modid = STDemo.MODID, name = STDemo.NAME, version = STDemo.VERSION, acceptedMinecraftVersions = "1.7.10")

public class STDemo {
    public static final String MODID = "stdemo";
    public static final String NAME = "STDemo";
    public static final String VERSION = "1.0.0";
    public static final float PI = 3.1415923F;
    public static final int GUIIDMetalFurnace = 1;
    public static final int GUIIDDemo = 2;

    @Instance(STDemo.MODID)
    public static STDemo instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
    }

    @SidedProxy(clientSide = "com.github.mrstop.stdemo.client.ClientProxy", serverSide = "com.github.mrstop.stdemo.common.CommonProxy")
    public static CommonProxy proxy;
}
