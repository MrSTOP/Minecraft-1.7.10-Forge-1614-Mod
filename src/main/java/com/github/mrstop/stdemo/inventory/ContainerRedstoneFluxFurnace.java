package com.github.mrstop.stdemo.inventory;

import com.github.mrstop.stdemo.tileentity.TileEntityRedstoneFluxFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerRedstoneFluxFurnace extends Container {

    private TileEntityRedstoneFluxFurnace tileEntityRedstoneFluxFurnace;
    private int lastCookTime;
    //private int lastBurnTime;
    //private int lastItemBurnTime;

    public ContainerRedstoneFluxFurnace(InventoryPlayer inventoryPlayer, TileEntityRedstoneFluxFurnace tileEntityRedstoneFluxFurnaceConstruct)
    {
        this.tileEntityRedstoneFluxFurnace = tileEntityRedstoneFluxFurnaceConstruct;
        this.addSlotToContainer(new Slot(tileEntityRedstoneFluxFurnaceConstruct, 0, 56, 17));
        this.addSlotToContainer(new Slot(tileEntityRedstoneFluxFurnaceConstruct, 1, 116, 35));
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
    }

    /*@Override
    public void onCraftGuiOpened(ICrafting iCrafting)
    {
        super.onCraftGuiOpened(iCrafting);
        iCrafting.sendProgressBarUpdate(this, 0, this.tileEntityRedstoneFluxFurnace.redstoneFluxFurnaceBurnTime);
        iCrafting.sendProgressBarUpdate(this, 1, this.tileEntityRedstoneFluxFurnace.redstoneFluxFurnaceCookTime);
        iCrafting.sendProgressBarUpdate(this, 2, this.tileEntityRedstoneFluxFurnace.currentItemBurnTime);
    }*/

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            if (this.lastCookTime != this.tileEntityRedstoneFluxFurnace.redstoneFluxFurnaceCookTime)
            {
                icrafting.sendProgressBarUpdate(this, 0, this.tileEntityRedstoneFluxFurnace.redstoneFluxFurnaceCookTime);
            }

            /*if (this.lastBurnTime != this.tileEntityRedstoneFluxFurnace.redstoneFluxFurnaceBurnTime)
            {
                icrafting.sendProgressBarUpdate(this, 1, this.tileEntityRedstoneFluxFurnace.redstoneFluxFurnaceBurnTime);
            }*/

            /*if (this.lastItemBurnTime != this.tileEntityRedstoneFluxFurnace.currentItemBurnTime)
            {
                icrafting.sendProgressBarUpdate(this, 2, this.tileEntityRedstoneFluxFurnace.currentItemBurnTime);
            }*/
        }

        this.lastCookTime = this.tileEntityRedstoneFluxFurnace.redstoneFluxFurnaceCookTime;
        //this.lastBurnTime = this.tileEntityRedstoneFluxFurnace.redstoneFluxFurnaceBurnTime;
        //this.lastItemBurnTime = this.tileEntityRedstoneFluxFurnace.currentItemBurnTime;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data)
    {
        super.updateProgressBar(id, data);
        switch (id) {
            case 0:
                this.tileEntityRedstoneFluxFurnace.redstoneFluxFurnaceCookTime = data;
                break;
            /*case 1:
                this.tileEntityRedstoneFluxFurnace.redstoneFluxFurnaceBurnTime = data;
                break;*/
            /*case 2:
                this.tileEntityRedstoneFluxFurnace.currentItemBurnTime = data;
                break;*/
            default:
                break;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer)
    {
        return this.tileEntityRedstoneFluxFurnace.isUseableByPlayer(entityPlayer);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index)
    {
        return null;
    }

}
