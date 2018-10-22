package com.mod.tuto.init;

import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

public class RemoveMod
{
    public static void init()
    {
        removeCraft(new ItemStack(Items.diamond_axe));
    }
    
    private static void removeCraft(ItemStack stack)
    {
        List<IRecipe> recipeList = CraftingManager.getInstance().getRecipeList();
        for(int i = 0; i < recipeList.size(); i++)
        {
            ItemStack out = recipeList.get(i).getRecipeOutput();
            if(out != null && stack.getItem() == out.getItem() && stack.getMetadata() == out.getMetadata())
            {
                recipeList.remove(i);
            }
        }
    }
}