package com.github.mrstop.stdemo.inventory;

import com.github.mrstop.stdemo.tileentity.TileEntityMachineRedstoneFluxFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineRedstoneFluxFurnace extends Container {

    private TileEntityMachineRedstoneFluxFurnace tileEntityMachineRedstoneFluxFurnace;
    private int lastCookTime;
    //private int lastBurnTime;
    //private int lastItemBurnTime;

    public ContainerMachineRedstoneFluxFurnace(InventoryPlayer inventoryPlayer, TileEntityMachineRedstoneFluxFurnace tileEntityMachineRedstoneFluxFurnaceConstruct) {
        this.tileEntityMachineRedstoneFluxFurnace = tileEntityMachineRedstoneFluxFurnaceConstruct;
        this.addSlotToContainer(new Slot(tileEntityMachineRedstoneFluxFurnaceConstruct, 0, 56, 17));
        this.addSlotToContainer(new Slot(tileEntityMachineRedstoneFluxFurnaceConstruct, 1, 116, 35));
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
        iCrafting.sendProgressBarUpdate(this, 0, this.tileEntityMachineRedstoneFluxFurnace.redstoneFluxFurnaceCookTime);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);
            if (this.lastCookTime != this.tileEntityMachineRedstoneFluxFurnace.redstoneFluxFurnaceCookTime) {
                icrafting.sendProgressBarUpdate(this, 0, this.tileEntityMachineRedstoneFluxFurnace.redstoneFluxFurnaceCookTime);
            }
        }
        this.lastCookTime = this.tileEntityMachineRedstoneFluxFurnace.redstoneFluxFurnaceCookTime;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);
        switch (id) {
            case 0:
                this.tileEntityMachineRedstoneFluxFurnace.redstoneFluxFurnaceCookTime = data;
                break;
            default:
                break;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return this.tileEntityMachineRedstoneFluxFurnace.isUseableByPlayer(entityPlayer);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        return null;
    }

}
