package com.mod.tuto.items;

import com.mod.tuto.Reference;
import com.mod.tuto.init.ItemMod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemArmorTuto extends ItemArmor
{

    public ItemArmorTuto(ArmorMaterial material, int metaData)
    {
        super(material, 0, metaData);
    }
    
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        if(stack.getItem() == ItemMod.pantalonArmor)
        {
            return Reference.MOD_ID + ":textures/models/armor/armorMod_layer_2.png";
        }
        
        else if(stack.getItem() == ItemMod.casqueArmor || stack.getItem() == ItemMod.plastronArmor || stack.getItem() == ItemMod.bootArmor)
        {
            return Reference.MOD_ID + ":textures/models/armor/armorMod_layer_1.png";
        }
        return null;
    }
    
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {
        if(this == ItemMod.bootArmor)
        {
            player.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 660, 0));
        }
    }
}
