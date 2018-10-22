package com.github.mrstop.stdemo.entity;

import com.github.mrstop.stdemo.item.ItemLoader;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityGoldenEgg extends EntityThrowable {

    public EntityGoldenEgg(World world)
    {
        super(world);
    }

    public EntityGoldenEgg(World world, EntityLivingBase throwIn)
    {
        super(world, throwIn);
    }

    public EntityGoldenEgg(World world, double x, double y, double z)
    {
        super(world, x, y, z);
    }

    @Override
    protected void onImpact(MovingObjectPosition movingObjectPosition)
    {
        if (!this.worldObj.isRemote)
        {
            if (movingObjectPosition.entityHit instanceof EntityChicken)
            {
                EntityChicken originChicken = (EntityChicken) movingObjectPosition.entityHit;
                EntityPig pig = new EntityPig(this.worldObj);
                pig.setLocationAndAngles(originChicken.posX, originChicken.posY, originChicken.posZ, originChicken.rotationYaw, originChicken.rotationPitch);
                originChicken.setDead();
                this.worldObj.spawnEntityInWorld(pig);
            }
            else
            {
                this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(ItemLoader.goldenEgg)));
            }
            this.setDead();
        }
    }
}
