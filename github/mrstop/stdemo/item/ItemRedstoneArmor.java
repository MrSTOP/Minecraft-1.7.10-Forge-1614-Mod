package com.github.mrstop.stdemo.item;


import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraft.entity.Entity;


public class ItemRedstoneArmor extends ItemArmor {

    public static final ItemArmor.ArmorMaterial REDSTONE_ARMOR = EnumHelper.addArmorMaterial("redstone", 10, new int[] {2, 6, 4, 2}, 10);

    public ItemRedstoneArmor(ArmorMaterial armorMaterial, int renderIndex, int armorType)
    {
        super(armorMaterial, renderIndex, armorType);
    }
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        if (this.armorType == 2)
        {

            return "stdemo:textures/models/armor/redstone_layer_2.png";
        }
        return "stdemo:textures/models/armor/redstone_layer_1.png";
    }
}
