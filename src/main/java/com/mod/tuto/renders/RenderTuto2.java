package com.mod.tuto.renders;

import com.mod.tuto.Reference;
import com.mod.tuto.entity.EntityTuto;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderTuto2 extends RenderLiving
{
    private ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/mobs/MobTuto2.png");

    public RenderTuto2(ModelBase p_i1262_1_, float p_i1262_2_)
    {
        super(p_i1262_1_, p_i1262_2_);
    }
    
    protected ResourceLocation getEntiyTexture(EntityLiving living)
    {
        return this.getEntityTexture((EntityTuto) living);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_)
    {
        return texture;
    }
}