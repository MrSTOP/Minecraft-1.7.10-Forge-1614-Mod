package com.mod.tuto.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelBlockTesr extends ModelBase
{
    public ModelRenderer shape1;
    public ModelRenderer shape2;
  
  public ModelBlockTesr()
  {
      this.textureWidth = 64;
      this.textureHeight = 64;
    
      this.shape1 = new ModelRenderer(this, 0, 0);
      this.shape1.mirror = true;
      this.shape1.addBox(0F, 0F, 0F, 6, 1, 10);
      this.shape1.setRotationPoint(1F, 23F, -4F);
      this.shape1.setTextureSize(64, 64);
      setRotation(this.shape1, 0F, 0F, 0F);

      this.shape2 = new ModelRenderer(this, 33, 0);
      this.shape2.mirror = true;
      this.shape2.addBox(0F, 0F, 0F, 4, 3, 4);
      this.shape2.setRotationPoint(-4F, 20F, 3F);
      this.shape2.setTextureSize(64, 64);
      setRotation(this.shape2, 0F, 0F, 0F);
  }
  
  public void renderAll()
  {
    this.shape1.render(0.0625F);
    this.shape2.render(0.0625F);
  }
  
  public static void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
}
