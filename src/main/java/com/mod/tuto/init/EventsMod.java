package com.mod.tuto.init;

import com.mod.tuto.events.DropsBlock;

import net.minecraftforge.common.MinecraftForge;

public class EventsMod
{
    public static void init()
    {
        MinecraftForge.EVENT_BUS.register(new DropsBlock());
    }
}