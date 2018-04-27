package com.github.mrstop.stdemo.entity;

import com.github.mrstop.stdemo.STDemo;
import net.minecraft.entity.Entity;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.EntityList;

public class EntityLoader {

    private static int nextID = 0;

    public EntityLoader()
    {
        registerEntity(EntityGoldenChicken.class, "GoldenChicken", 80, 3, true);
        registerEntityEgg(EntityGoldenChicken.class, 0xffff66, 0x660000);
    }

    private static void registerEntity(Class<? extends Entity> entityClass, String name, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
    {
        System.out.print("RegisterGoldenChicken");
        EntityRegistry.registerModEntity(entityClass, name, nextID++, STDemo.instance, trackingRange, updateFrequency,sendsVelocityUpdates);
    }

    private static void registerEntityEgg(Class<? extends Entity> entityClass, int eggPrimary, int eggSecondary)
    {
        //EntityList.entityEggs.put()
    }
}
