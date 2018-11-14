package com.github.mrstop.stdemo.client.render.entity;

import com.github.mrstop.stdemo.entity.EntityExplosionEgg;
import com.github.mrstop.stdemo.entity.EntityGoldenEgg;
import com.github.mrstop.stdemo.item.ItemLoader;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;

public class EntityRenderLoader {

    public EntityRenderLoader() {
        this.registerRenders(EntityExplosionEgg.class, new RenderSnowball(ItemLoader.explosionEgg));
        this.registerRenders(EntityGoldenEgg.class, new RenderSnowball(ItemLoader.goldenEgg));
    }

    private void registerRenders(Class<? extends Entity> entityClass, Render renderer) {
        RenderingRegistry.registerEntityRenderingHandler(entityClass, renderer);
    }


}
