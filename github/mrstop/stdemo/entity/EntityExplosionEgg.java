package com.github.mrstop.stdemo.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityExplosionEgg extends EntityThrowable {

    public EntityExplosionEgg(World worldIn)
    {
        super(worldIn);
    }

    public EntityExplosionEgg(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
    }

    public EntityExplosionEgg(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    @Override
    protected void onImpact(MovingObjectPosition movingObjectPosition)
    {
        if (!this.worldObj.isRemote)
        {
            this.worldObj.createExplosion(null, this.posX, this.posY, this.posZ, 50, false);
            this.setDead();
        }
    }
}
