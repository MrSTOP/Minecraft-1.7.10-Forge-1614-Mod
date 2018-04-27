package com.github.mrstop.stdemo.enchantment;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.common.ConfigLoader;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EnchantmentFireBurn extends Enchantment {

    public EnchantmentFireBurn()
    {
        super(ConfigLoader.EnchantmentFireBurn, 1, EnumEnchantmentType.digger);
        this.setName("fireBurn");
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 15;
    }

    @Override
    public int getMaxLevel()
    {
        return 1;
    }

    @Override
    public boolean canApplyTogether(Enchantment ench)
    {
        return super.canApplyTogether(ench) && ench.effectId != silkTouch.effectId && ench.effectId != fortune.effectId;
    }

    @Override
    public boolean canApply(ItemStack stack)
    {
        return stack.getItem() == Items.shears ? true : super.canApply(stack);
    }
    /*getMinEnchantability和getMaxEnchantability方法的作用就是获取可以获取到此附魔的最低等级和最高等级。这里被设置成了和精准采集相同。
     *getMaxLevel方法指的就是这个附魔的最大等级了。自然，这个附魔只应该有一个等级。
     *canApplyTogether方法表示的是这个附魔可否与其他附魔共存。这里设定为不能和精准采集和时运共存。
     *canApply方法表示的是这个附魔可以作用的物品。既然是一个作用于工具的附魔，自然作用对象是所有工具和剪刀。
     */
}
