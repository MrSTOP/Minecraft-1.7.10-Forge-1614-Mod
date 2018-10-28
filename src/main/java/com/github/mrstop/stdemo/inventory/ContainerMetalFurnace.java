package com.github.mrstop.stdemo.inventory;


import com.github.mrstop.stdemo.tileentity.TileEntityMetalFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class ContainerMetalFurnace extends Container {

    private TileEntityMetalFurnace tileEntityMetalFurnace;
    private int lastCookTime;
    private int lastBurnTime;
    private int lastItemBurnTime;

    public ContainerMetalFurnace(InventoryPlayer inventoryPlayer, TileEntityMetalFurnace tileEntityMetalFurnace) {
        this.tileEntityMetalFurnace = tileEntityMetalFurnace;
        //inventory = p_i1824_1_;
        //slotIndex = p_i1824_2_;
        //xDisplayPosition = p_i1824_3_;
        // yDisplayPosition = p_i1824_4_;
        this.addSlotToContainer(new Slot(tileEntityMetalFurnace, 0, 56, 17));
        this.addSlotToContainer(new Slot(tileEntityMetalFurnace, 1, 56, 53));
        this.addSlotToContainer(new SlotFurnace(inventoryPlayer.player, tileEntityMetalFurnace, 2, 116, 35));
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
        iCrafting.sendProgressBarUpdate(this, 0, this.tileEntityMetalFurnace.metalFurnaceBurnTime);
        iCrafting.sendProgressBarUpdate(this, 1, this.tileEntityMetalFurnace.metalFurnaceCookTime);
        iCrafting.sendProgressBarUpdate(this, 2, this.tileEntityMetalFurnace.currentItemBurnTime);
    }

    //查找容器中所做的更改，并将它们发送给每个侦听器。
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            if (this.lastCookTime != this.tileEntityMetalFurnace.metalFurnaceCookTime) {
                icrafting.sendProgressBarUpdate(this, 0, this.tileEntityMetalFurnace.metalFurnaceCookTime);
            }
            if (this.lastBurnTime != this.tileEntityMetalFurnace.metalFurnaceBurnTime) {
                icrafting.sendProgressBarUpdate(this, 1, this.tileEntityMetalFurnace.metalFurnaceBurnTime);
            }
            if (this.lastItemBurnTime != this.tileEntityMetalFurnace.currentItemBurnTime) {
                icrafting.sendProgressBarUpdate(this, 2, this.tileEntityMetalFurnace.currentItemBurnTime);
            }
        }
        this.lastCookTime = this.tileEntityMetalFurnace.metalFurnaceCookTime;
        this.lastBurnTime = this.tileEntityMetalFurnace.metalFurnaceBurnTime;
        this.lastItemBurnTime = this.tileEntityMetalFurnace.currentItemBurnTime;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);
        switch (id) {
            case 0:
                this.tileEntityMetalFurnace.metalFurnaceCookTime = data;
                break;
            case 1:
                this.tileEntityMetalFurnace.metalFurnaceBurnTime = data;
                break;
            case 2:
                this.tileEntityMetalFurnace.currentItemBurnTime = data;
                break;
            default:
                break;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return this.tileEntityMetalFurnace.isUseableByPlayer(entityPlayer);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index)
    {
        return null;
    }
}
