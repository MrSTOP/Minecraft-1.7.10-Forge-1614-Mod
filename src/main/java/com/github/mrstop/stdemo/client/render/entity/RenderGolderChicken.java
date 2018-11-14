package com.github.mrstop.stdemo.client.render.entity;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.entity.EntityGoldenChicken;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderGolderChicken extends RenderLiving {

    protected ResourceLocation golderChickenTexture;

    public RenderGolderChicken(ModelBase modelBase, float shadowSize)
    {
        super(modelBase, shadowSize);
        setEntityTexture();
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityLivingBase, float f)
    {
        preRenderCallbackGoldenChicken((EntityGoldenChicken) entityLivingBase, f);
    }

    protected void preRenderCallbackGoldenChicken(EntityGoldenChicken entity, float f)
    {

    }

    protected void setEntityTexture()
    {
        golderChickenTexture = new ResourceLocation(STDemo.MOD_ID + "texture/entity/golden_chicken.png");
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return golderChickenTexture;
    }
}
