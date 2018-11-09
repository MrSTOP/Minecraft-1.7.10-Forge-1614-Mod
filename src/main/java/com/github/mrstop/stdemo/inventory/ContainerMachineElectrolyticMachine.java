package com.github.mrstop.stdemo.inventory;

import com.github.mrstop.stdemo.tileentity.TileEntityMachineElectrolyticMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;

public class ContainerMachineElectrolyticMachine extends Container {
    private TileEntityMachineElectrolyticMachine tileEntityMachineElectrolyticMachine;
    private int processTime = 0;
    private int fluidID = 0;
    private int fluidAmount = 0;
    private int energyAmount = 0;

    public ContainerMachineElectrolyticMachine(InventoryPlayer inventoryPlayer, TileEntityMachineElectrolyticMachine tileEntityMachineElectrolyticMachine) {
        this.tileEntityMachineElectrolyticMachine = tileEntityMachineElectrolyticMachine;
        this.addSlotToContainer(new Slot(tileEntityMachineElectrolyticMachine, 0, 56, 32));
        this.addSlotToContainer(new Slot(tileEntityMachineElectrolyticMachine, 1, 105, 32));
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
        iCrafting.sendProgressBarUpdate(this, 0, this.tileEntityMachineElectrolyticMachine.getProcessTime());
        iCrafting.sendProgressBarUpdate(this, 1, this.tileEntityMachineElectrolyticMachine.getEnergyAmount());
        iCrafting.sendProgressBarUpdate(this, 2, this.tileEntityMachineElectrolyticMachine.getFluidAmount(0));
        iCrafting.sendProgressBarUpdate(this, 3, this.tileEntityMachineElectrolyticMachine.getFluidID(0));
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        int tempProcessTime = this.tileEntityMachineElectrolyticMachine.getProcessTime();
        int tempEnergyAmount = this.tileEntityMachineElectrolyticMachine.getEnergyAmount();
        int tempFluidAmount = this.tileEntityMachineElectrolyticMachine.getFluidAmount(0);
        int tempFluidID = this.tileEntityMachineElectrolyticMachine.getFluidID(0);
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
                this.tileEntityMachineElectrolyticMachine.GUIProcessTime = data;
                break;
            case 1:
                this.tileEntityMachineElectrolyticMachine.GUIEnergyAmount = data;
                break;
            case 2:
                this.tileEntityMachineElectrolyticMachine.GUIFluidAmount = data;
                break;
            case 3:
                this.tileEntityMachineElectrolyticMachine.GUIFluidID = data;
                break;
            default:
                break;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.tileEntityMachineElectrolyticMachine.isUsedByPlayer(player);
    }
}
