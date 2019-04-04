package com.github.mrstop.stdemo.crafting;

import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class RecipeMachineElectrolyticMachine {
    private static final RecipeMachineElectrolyticMachine RECIPE_MACHINE_ELECTROLYTIC_MACHINE = new RecipeMachineElectrolyticMachine();

    private Map<ItemStack, ItemStack> productionItem = new HashMap<>();
    private Map<ItemStack, ItemStack> productionFluid = new HashMap<>();

    private RecipeMachineElectrolyticMachine() {
    }
    public static RecipeMachineElectrolyticMachine getInstant(){
        return RECIPE_MACHINE_ELECTROLYTIC_MACHINE;
    }
}
