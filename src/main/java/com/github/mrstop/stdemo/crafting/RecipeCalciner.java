package com.github.mrstop.stdemo.crafting;

import com.github.mrstop.stdemo.block.BlockLoader;
import com.github.mrstop.stdemo.fluid.FluidLoader;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;

public class RecipeCalciner {
    private static final RecipeCalciner recipeCalciner = new RecipeCalciner();
    private Map<ItemStack, ItemStack> productionItemList = new HashMap<>();
    private Map<ItemStack, FluidStack> productionFluidList = new HashMap<>();
    private RecipeCalciner() {
        this.addRecipes(Item.getItemFromBlock(BlockLoader.cinnabarOre), null, new FluidStack(FluidLoader.fluidMercury, 100));
        this.addRecipes(Item.getItemFromBlock(Blocks.stone), new ItemStack(Items.redstone, 5), null);
        this.addRecipes(Item.getItemFromBlock(Blocks.iron_ore), new ItemStack(Blocks.stone, 5), new FluidStack(FluidLoader.fluidMercury, 500));
        this.addRecipes(Item.getItemFromBlock(Blocks.iron_ore), new ItemStack(Blocks.diamond_block, 1), new FluidStack(FluidRegistry.getFluid("lava"), 100));
    }

    public static RecipeCalciner getInstance(){
        return recipeCalciner;
    }

    public void addRecipes(Item itemIn, ItemStack itemStackOut, FluidStack fluidStackOut){
        this.addRecipes(new ItemStack(itemIn), itemStackOut, fluidStackOut);
    }

    public void addRecipes(ItemStack itemStackIn, ItemStack itemStackOut, FluidStack fluidStackOut){
        this.putLists(itemStackIn, itemStackOut, fluidStackOut);
    }

    public void putLists(ItemStack itemStackIn, ItemStack itemStackOut, FluidStack fluidStackOut){
        this.productionItemList.put(itemStackIn, itemStackOut);
        this.productionFluidList.put(itemStackIn, fluidStackOut);
    }

    public boolean canCalcine(ItemStack itemStackIn, boolean judgeItemNumber){
        boolean canCalcine = false;
        if (itemStackIn == null){
            return false;
        }
        for (ItemStack itemStack : this.productionItemList.keySet()) {
            if (itemStack.isItemEqual(itemStackIn)){
                if (judgeItemNumber){
                    if (itemStackIn.stackSize >= itemStack.stackSize){
                        canCalcine = true;
                    }
                }
                else {
                    canCalcine = true;
                }
            }
        }
        return canCalcine;
    }

    public ItemStack getItemResult(ItemStack itemStackIn){
        if (itemStackIn == null){
            return null;
        }
        for (Map.Entry<ItemStack, ItemStack> entry : this.productionItemList.entrySet()) {
            ItemStack itemStackKey = entry.getKey();
            ItemStack itemStackValue = entry.getValue();
            if (itemStackIn.isItemEqual(itemStackKey)){
                if (itemStackIn.stackSize >= itemStackKey.stackSize){
                    return itemStackValue;
                }
            }
        }
        return null;
    }

    public FluidStack getFluidResult(ItemStack itemStackIn){
        if (itemStackIn == null){
            return null;
        }
        for (Map.Entry<ItemStack, FluidStack> entry : this.productionFluidList.entrySet()) {
            ItemStack itemStackKey = entry.getKey();
            FluidStack fluidStackValue = entry.getValue();
            if (itemStackIn.isItemEqual(itemStackKey)){
                if (itemStackIn.stackSize >= itemStackKey.stackSize){
                    return fluidStackValue;
                }
            }
        }
        return null;
    }
}
