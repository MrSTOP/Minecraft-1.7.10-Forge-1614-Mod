package com.github.mrstop.stdemo.entity;

import com.github.mrstop.stdemo.STDemo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import cpw.mods.fml.common.registry.EntityRegistry;

public class EntityLoader {

    private static int nextID = 0;

    public EntityLoader()
    {
        registerEntity(EntityGoldenChicken.class, "GoldenChicken", 80, 3, true);
        registerEntity(EntityExplosionEgg.class, "ExplosionEgg", 64, 10, true);
        registerEntity(EntityGoldenEgg.class, "GolderEgg", 64, 10, true);
    }

    private static void registerEntity(Class<? extends Entity> entityClass, String name, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
    {
        //System.out.print("RegisterGoldenChicken");
        EntityRegistry.registerModEntity(entityClass, name, nextID++, STDemo.instance, trackingRange, updateFrequency,sendsVelocityUpdates);
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenders()
    {

    }

    /*@SideOnly(Side.CLIENT)
    private static <T extends Entity> void registerEntityRender(Class<T> entityClass, Class<? extends Render<T>>)
    {
        //RenderingRegistry.registerEntityRenderingHandler(entityClass, new EntityR);
    }*/
}
