package com.github.mrstop.stdemo.client;

import com.github.mrstop.stdemo.client.block.render.RenderBlockLoader;
import com.github.mrstop.stdemo.client.entity.render.EntityRenderLoader;
import com.github.mrstop.stdemo.client.tielentity.render.TileEntityRenderLoader;
import com.github.mrstop.stdemo.common.CommonProxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        new EntityRenderLoader();
        new TileEntityRenderLoader();
        new RenderBlockLoader();
        //RenderingRegistry.registerEntityRenderingHandler(EntityGoldenChicken.class, new RenderGolderChicken(new Model));
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
        new KeyLoader();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)

    {
        super.postInit(event);
    }
}
