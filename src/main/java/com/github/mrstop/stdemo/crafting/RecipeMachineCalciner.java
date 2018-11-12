package com.github.mrstop.stdemo.crafting;

import com.github.mrstop.stdemo.block.BlockLoader;
import com.github.mrstop.stdemo.fluid.FluidLoader;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;

public class RecipeMachineCalciner {
    private static final RecipeMachineCalciner RECIPE_MACHINE_CALCINER = new RecipeMachineCalciner();
    private Map<ItemStack, ItemStack> productionItem = new HashMap<>();
    private Map<ItemStack, FluidStack> productionFluid = new HashMap<>();
    private RecipeMachineCalciner() {
        this.addRecipes(Item.getItemFromBlock(Blocks.coal_block), new FluidStack(FluidLoader.fluidMercury, 100));
        this.addRecipes(Item.getItemFromBlock(Blocks.iron_ore), new ItemStack(Items.iron_ingot, 1), new FluidStack(FluidRegistry.getFluid("lava"), 100));
        this.addRecipes(Item.getItemFromBlock(Blocks.stone), new ItemStack(Items.redstone, 5));
        this.addRecipes(Item.getItemFromBlock(BlockLoader.cinnabarOre), new ItemStack(Blocks.stone, 2), new FluidStack(FluidLoader.fluidMercury, 500));
        this.addRecipes(new ItemStack(Blocks.gold_ore, 5), new ItemStack(Items.gold_ingot, 5));
        this.addRecipes(new ItemStack(Blocks.snow, 5), new FluidStack(FluidRegistry.getFluid("water"), 5000));
    }

    public static RecipeMachineCalciner getInstance(){
        return RECIPE_MACHINE_CALCINER;
    }

    public void addRecipes(Item itemIn, ItemStack itemStackOut){
        this.addRecipes(new ItemStack(itemIn, 1), itemStackOut, null);
    }

    public void addRecipes(Item itemIn, FluidStack fluidStackOut){
        this.addRecipes(new ItemStack(itemIn, 1), null, fluidStackOut);
    }

    public void addRecipes(ItemStack itemStackIn, ItemStack itemStackOut){
        this.addRecipes(itemStackIn, itemStackOut, null);
    }

    public void addRecipes(ItemStack itemStackIn, FluidStack fluidStackOut) {
        this.addRecipes(itemStackIn, null, fluidStackOut);
    }

    public void addRecipes(Item itemIn, ItemStack itemStackOut, FluidStack fluidStackOut){
        this.addRecipes(new ItemStack(itemIn, 1), itemStackOut, fluidStackOut);
    }

    public void addRecipes(ItemStack itemStackIn, ItemStack itemStackOut, FluidStack fluidStackOut){
        this.putProduction(itemStackIn, itemStackOut, fluidStackOut);
    }

    private void putProduction(ItemStack itemStackIn, ItemStack itemStackOut, FluidStack fluidStackOut){
        this.productionItem.put(itemStackIn, itemStackOut);
        this.productionFluid.put(itemStackIn, fluidStackOut);
    }
    public int getRequireIngredientAmount(ItemStack itemStackIn){
        if (itemStackIn == null){
            return 0;
        }
        for (ItemStack itemStack : this.productionItem.keySet()) {
            if (itemStack.isItemEqual(itemStackIn)){
                return itemStack.stackSize;
            }
        }
        return 0;
    }

    public boolean canCalcine(ItemStack itemStackIn, boolean judgeItemNumber){
        boolean canCalcine = false;
        if (itemStackIn == null){
            return false;
        }
        for (ItemStack itemStack : this.productionItem.keySet()) {
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
        for (Map.Entry<ItemStack, ItemStack> entry : this.productionItem.entrySet()) {
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
        for (Map.Entry<ItemStack, FluidStack> entry : this.productionFluid.entrySet()) {
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
