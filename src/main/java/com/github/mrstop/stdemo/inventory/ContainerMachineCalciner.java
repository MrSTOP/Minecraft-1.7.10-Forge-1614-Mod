package com.github.mrstop.stdemo.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerMachineCalciner extends Container {
    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return false;
    }
}
