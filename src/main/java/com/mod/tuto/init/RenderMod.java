package com.mod.tuto.init;

import com.mod.tuto.entity.EntityTuto;
import com.mod.tuto.entity.EntityTuto2;
import com.mod.tuto.models.ModelTuto;
import com.mod.tuto.models.ModelTuto2;
import com.mod.tuto.renders.RenderTuto;
import com.mod.tuto.renders.RenderTuto2;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class RenderMod
{
    public static void init()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityTuto.class, new RenderTuto(new ModelTuto(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityTuto2.class, new RenderTuto2(new ModelTuto2(), 1));
    }
}