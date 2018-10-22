package com.mod.tuto.tems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemMods extends Item
{
    /*public void onUpdate(ItemStack p_77663_1_, World p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_)
    {
        EntityPlayer player = (EntityPlayer)p_77663_3_;
        ItemStack equip = player.getCurrentEquippedItem();
        
        if(equip == p_77663_1_)
        {
            player.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 660, 0));
        }
    }*/
    
    /*public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
    {
        p_77659_3_.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 660, 0));
        return p_77659_1_;
    }*/
    
    protected void onFoodEaten(ItemStack p_77849_1_, World p_77849_2_, EntityPlayer p_77849_3_)
    {
        
    }
}