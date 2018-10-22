package com.github.mrstop.stdemo.crafting;

import com.github.mrstop.stdemo.item.ItemLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class MetalFurnaceRecipes {

    private static final MetalFurnaceRecipes SMELTING_BASE = new MetalFurnaceRecipes();

    private Map smeltingList = new HashMap();
    private Map experienceList = new HashMap();

    public static MetalFurnaceRecipes metalFurnaceSmelting()
    {
        return SMELTING_BASE;
    }

    private MetalFurnaceRecipes()
    {
        this.addRecipes(ItemLoader.goldenEgg, new ItemStack(ItemLoader.explosionEgg), 100F);
    }

    public void addRecipes(Item item, ItemStack itemStack, float experience)
    {
        this.addLists(item, itemStack, experience);
    }

    public void addLists(Item item, ItemStack itemStack, float experience)
    {
        this.putLists(new ItemStack(item, 1, 32767), itemStack, experience);
    }

    public void putLists(ItemStack itemStack, ItemStack itemStack2, float experience)
    {
        this.smeltingList.put(itemStack, itemStack2);
        this.experienceList.put(itemStack2, Float.valueOf(experience));
    }

    public ItemStack getSmeltingResult(ItemStack itemStack)
    {
        Iterator iterator = this.smeltingList.entrySet().iterator();
        Entry entry;

        do {
            if (!iterator.hasNext()) {
                return null;
            }
            entry = (Entry) iterator.next();
        } while (!canBeSmelted(itemStack, (ItemStack) entry.getKey()));
        return (ItemStack) entry.getValue();
    }

    private boolean canBeSmelted(ItemStack itemStack, ItemStack itemStack2)
    {
        return itemStack2.getItem() == itemStack.getItem() && (itemStack2.getMetadata() == 32767 || itemStack2.getMetadata() == itemStack.getMetadata());
    }

    public float giveExperience(ItemStack itemStack)
    {
        Iterator iterator = this.experienceList.entrySet().iterator();
        Entry entry;

        do {
            if (!iterator.hasNext()) {
                return 0.0F;
            }

            entry = (Entry) iterator.next();
        } while (!this.canBeSmelted(itemStack, (ItemStack) entry.getKey()));
        if (itemStack.getItem().getSmeltingExperience((itemStack)) != -1)
        {
            return itemStack.getItem().getSmeltingExperience((itemStack));
        }
        return ((Float) entry.getValue()).floatValue();
    }
}
