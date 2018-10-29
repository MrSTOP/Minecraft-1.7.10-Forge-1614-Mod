package com.github.mrstop.stdemo.inventory;

import com.github.mrstop.stdemo.tileentity.TileEntityElectrolyticMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class ContainerElectrolyticMachine extends Container {
    private TileEntityElectrolyticMachine tileEntityElectrolyticMachine;
    private int processTime = 0;
    private int fluidID = 0;
    private int fluidAmount = 0;
    private int energyAmount = 0;

    public ContainerElectrolyticMachine(InventoryPlayer inventoryPlayer, TileEntityElectrolyticMachine tileEntityElectrolyticMachine) {
        this.tileEntityElectrolyticMachine = tileEntityElectrolyticMachine;
        this.addSlotToContainer(new Slot(tileEntityElectrolyticMachine, 0, 56, 32));
        this.addSlotToContainer(new Slot(tileEntityElectrolyticMachine, 1, 105, 32));
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
        iCrafting.sendProgressBarUpdate(this, 0, this.tileEntityElectrolyticMachine.getProcessTime());
        iCrafting.sendProgressBarUpdate(this, 1, this.tileEntityElectrolyticMachine.getEnergyAmount());
        iCrafting.sendProgressBarUpdate(this, 2, this.tileEntityElectrolyticMachine.getFluidAmount(0));
        iCrafting.sendProgressBarUpdate(this, 3, this.tileEntityElectrolyticMachine.getFluidID(0));
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        int tempProcessTime = this.tileEntityElectrolyticMachine.getProcessTime();
        int tempEnergyAmount = this.tileEntityElectrolyticMachine.getEnergyAmount();
        int tempFluidAmount = this.tileEntityElectrolyticMachine.getFluidAmount(0);
        int tempFluidID = this.tileEntityElectrolyticMachine.getFluidID(0);
        for (Object crafter: this.crafters) {
            ICrafting iCrafting = (ICrafting) crafter;
            if (this.processTime != tempProcessTime){
                iCrafting.sendProgressBarUpdate(this, 0, tempProcessTime);
            }
            if (this.energyAmount != tempEnergyAmount){
                iCrafting.sendProgressBarUpdate(this, 1, tempEnergyAmount);
            }
            if (this.fluidAmount != tempFluidAmount){
                iCrafting.sendProgressBarUpdate(this, 2, tempFluidAmount);
            }
            if (this.fluidID != tempFluidID){
                iCrafting.sendProgressBarUpdate(this, 3, tempFluidID);
            }
        }
        this.processTime = tempProcessTime;
        this.fluidAmount = tempFluidAmount;
        this.energyAmount = tempEnergyAmount;
        this.fluidID = tempFluidID;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int ID, int data) {
        super.updateProgressBar(ID, data);
        switch (ID){
            case 0:
                this.tileEntityElectrolyticMachine.GUIProcessTime = data;
                break;
            case 1:
                this.tileEntityElectrolyticMachine.GUIEnergyAmount = data;
                break;
            case 2:
                this.tileEntityElectrolyticMachine.GUIFluidAmount = data;
                break;
            case 3:
                this.tileEntityElectrolyticMachine.GUIFluidID = data;
                break;
            default:
                break;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.tileEntityElectrolyticMachine.isUsedByPlayer(player);
    }
}
