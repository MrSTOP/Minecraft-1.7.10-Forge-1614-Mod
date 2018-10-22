package com.github.mrstop.stdemo.potion;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.common.ConfigLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;


public class PotionFallProtection extends Potion {

    public static final ResourceLocation icon = new ResourceLocation(STDemo.MODID, "/texture/gui/inventory.png".substring(1));;

    public PotionFallProtection()
    {
        super(ConfigLoader.PotionFallProtection, false, 0);
        this.setPotionName("potion.fallProtection");
        this.setIconIndex(0, 0);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public int getStatusIconIndex()
    {
        ITextureObject texture = Minecraft.getMinecraft().renderEngine.getTexture(icon);
        Minecraft.getMinecraft().renderEngine.bindTexture(icon);
        return super.getStatusIconIndex();
    }
}
