package com.github.mrstop.stdemo.client.entity.render;

import com.github.mrstop.stdemo.entity.EntityExplosionEgg;
import com.github.mrstop.stdemo.entity.EntityGoldenEgg;
import com.github.mrstop.stdemo.item.ItemLoader;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.renderer.entity.RenderSnowball;

public class EntityRenderLoader {

    public EntityRenderLoader()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityExplosionEgg.class, new RenderSnowball(ItemLoader.explosionEgg));
        RenderingRegistry.registerEntityRenderingHandler(EntityGoldenEgg.class, new RenderSnowball(ItemLoader.goldenEgg));
        //EntityLoader.registerRenders();
    }


}
