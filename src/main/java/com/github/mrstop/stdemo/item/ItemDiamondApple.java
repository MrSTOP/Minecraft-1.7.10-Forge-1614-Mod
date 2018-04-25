package com.github.mrstop.stdemo.item;

import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import javax.swing.text.html.parser.Entity;

public class ItemDiamondApple extends ItemFood {

    public ItemDiamondApple()
    {
        super(10, 1.5F, false);
        this.setAlwaysEdible();
        this.setUnlocalizedName("diamondApple");
        this.setCreativeTab(CreativeTabsLoader.tabSTDemo);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("stdemo:diamond_apple");
    }

    @Override
    public void onFoodEaten(ItemStack stack, World WorldIn, EntityPlayer player)
    {
        if(!WorldIn.isRemote)
        {
            //PotionEffect的构造函数使用的时间是以gametick计数的
            player.addPotionEffect(new PotionEffect(Potion.saturation.id, 200, 1));
            player.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 1200, 2));
            player.addPotionEffect(new PotionEffect(Potion.blindness.id, 400, 2));
            player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 2000, 2));
            player.addExperience(100);
        }
    }
}
