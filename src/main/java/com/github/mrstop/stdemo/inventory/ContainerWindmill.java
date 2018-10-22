package com.github.mrstop.stdemo.inventory;

import com.github.mrstop.stdemo.tileentity.TileEntityWindmill;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;

public class ContainerWindmill extends Container {
    private TileEntityWindmill tileEntityWindmill;
    private float currentPower = 0.0F;
    public ContainerWindmill(InventoryPlayer inventoryPlayer, TileEntityWindmill tileEntityWindmill) {
        this.tileEntityWindmill = tileEntityWindmill;
        this.addSlotToContainer(new Slot(tileEntityWindmill, 0, 48, 33));
        this.addSlotToContainer(new Slot(tileEntityWindmill, 1, 114, 33));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
    }

    @Override
    public void onCraftGuiOpened(ICrafting iCrafting) {
        super.onCraftGuiOpened(iCrafting);
        iCrafting.sendProgressBarUpdate(this, 0, (int) this.tileEntityWindmill.getCurrentPower());
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        float tempCurrentPower = this.tileEntityWindmill.getCurrentPower();
        for (Object crafter : this.crafters) {
            ICrafting iCrafting = (ICrafting) crafter;
            if (this.currentPower != tempCurrentPower) {
                iCrafting.sendProgressBarUpdate(this, 0, (int) tempCurrentPower);
            }
        }
        this.currentPower = tempCurrentPower;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);
        switch (id){
            case 0:
                this.tileEntityWindmill.setCurrentPower(data);
                break;
        }
    }

    @SideOnly(Side.CLIENT)

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.tileEntityWindmill.isUseableByPlayer(player);
    }
}
