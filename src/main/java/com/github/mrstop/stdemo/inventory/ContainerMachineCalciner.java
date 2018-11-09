package com.github.mrstop.stdemo.inventory;

import com.github.mrstop.stdemo.tileentity.TileEntityMachineCalciner;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;

public class ContainerMachineCalciner extends Container {
    TileEntityMachineCalciner tileEntityMachineCalciner;

    public ContainerMachineCalciner(InventoryPlayer inventoryPlayer, TileEntityMachineCalciner tileEntityMachineCalciner) {
        this.tileEntityMachineCalciner = tileEntityMachineCalciner;
        this.addSlotToContainer(new Slot(tileEntityMachineCalciner, 0, 56, 32));
        this.addSlotToContainer(new Slot(tileEntityMachineCalciner, 1, 105, 32));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
    }

    @Override
    public void onCraftGuiOpened(ICrafting iCrafting) {
        super.onCraftGuiOpened(iCrafting);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int ID, int data) {
        super.updateProgressBar(ID, data);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.tileEntityMachineCalciner.isUseableByPlayer(player);
    }
}
