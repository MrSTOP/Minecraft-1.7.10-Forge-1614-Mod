package com.github.mrstop.stdemo.inventory;

import com.github.mrstop.stdemo.item.ItemLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerDemo extends Container {

    InventoryDemo inventoryDemo = new InventoryDemo(this, 4, 1);

    protected Slot goldSlot;
    protected Slot diamondSlot;
    protected Slot emeraldSlot;
    protected Slot ironSlot;

    public ContainerDemo(EntityPlayer entityPlayer)
    {
        super();

        //public Slot(IInventory inventoryIn, int index, int xPosition, int yPosition) {...}
        // 第一个参数代表相关联的IInventory，第二个参数代表物品槽在IInventory中的ID，最后两个参数代表它在GUI中的坐标

        /*for (int i = 0;i < 4;i++)
        {
            this.addSlotToContainer(new Slot(inventoryDemo[i], i, 38 + i * 32, 20));
        }*/
        this.addSlotToContainer(this.goldSlot = new Slot(inventoryDemo, 0, 38 + 0 * 32, 20)
        {
            @Override
            public boolean isItemValid(ItemStack itemStack)
            {
                return itemStack != null && itemStack.getItem() == Items.gold_ingot && super.isItemValid(itemStack);
            }

            @Override
            public int getSlotStackLimit()
            {
                return 16;
            }
        });

        this.addSlotToContainer(this.diamondSlot = new Slot(inventoryDemo, 1, 38 + 1 * 32, 20)
        {
            {
                this.putStack(new ItemStack(Items.diamond, 64));
            }

            @Override
            public boolean canTakeStack(EntityPlayer entityPlayer1)
            {
                return false;
            }

        });

        this.addSlotToContainer(this.emeraldSlot = new Slot(inventoryDemo, 2, 38 + 2 * 32, 20)
        {
            @Override
            public boolean isItemValid(ItemStack itemStack)
            {
                return itemStack != null && itemStack.getItem() == Items.emerald && super.isItemValid(itemStack);
            }

            @Override
            public void onSlotChanged()
            {
                ItemStack itemStack = this.getStack();
                int amoumt = itemStack == null ? 64 : 64 - itemStack.stackSize;
                ContainerDemo.this.diamondSlot.putStack(amoumt == 0 ? null : new ItemStack(Items.diamond, amoumt));
                super.onSlotChanged();
            }
        });

        this.addSlotToContainer(this.ironSlot = new Slot(inventoryDemo, 3, 38 + 3 * 32, 20)
        {
            {
                this.putStack(new ItemStack(Items.iron_ingot, 64));
            }

            @Override
            public boolean canTakeStack(EntityPlayer entityPlayer)
            {
                return false;
            }
        });
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(entityPlayer.inventory, j + i * 9 + 9, 8 + j * 18, 51 + i * 18));
            }

        }
        for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(entityPlayer.inventory, i, 8 + i * 18, 109));
        }

        //this.addSlotToContainer(this.goldSlot == new InventoryDemo())
    }

    public Slot getIronSlot()
    {
        return this.ironSlot;
    }

    @Override
    public void onContainerClosed(EntityPlayer entityPlayer)
    {
        super.onContainerClosed(entityPlayer);

        if (entityPlayer.isServerWorld())
        {
            for (int i = 0; i < 4; ++i)
            {
                ItemStack itemstack;
                itemstack = i == 1 ? null : i == 3 ? null : this.inventoryDemo.getStackInSlotOnClosing(i);
                //switch (i)
                //{
                //    case 0:
                //        itemstack = this.inventoryDemo.getStackInSlotOnClosing(i);
                //        break;
                //    case 2:
                //        itemstack = this.inventoryDemo.getStackInSlotOnClosing(i);
                //        break;
                //    default:
                //        itemstack = null;
                //}
                if (itemstack != null)
                {
                    entityPlayer.dropPlayerItemWithRandomChoice(itemstack, false);
                }
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer)
    {
        return entityPlayer.getCurrentEquippedItem() == null ? false : new ItemStack(ItemLoader.goldenEgg).isItemEqual(entityPlayer.getCurrentEquippedItem());
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int index)
    {
        Slot slot = (Slot)inventorySlots.get(index);

        if (slot == null || !slot.getHasStack())
            //
            // ???????????????????????
            //
        {
            return null;
        }

        ItemStack newStack = slot.getStack();
        ItemStack oldStack = newStack.copy();

        boolean isMerged = false;
///////////////////////////////////////////////////////////////////////////////////////////////////
        if (index == 0 || index == 2)
        {
            isMerged = mergeItemStack(newStack, 4, 40, true);
            //合并提供了ItemStack与容器/玩家物品栏之间的第一个可用索引，
            // 该索引介于min索引（含）和max索引（不含）之间。
            // 参数：物品栈，minIndex，maxIndex，negativ 方向。
            // !!Container实现不会检查项目是否对插槽有效!!
        }
        else if (index >= 4 && index < 31)
        {
            isMerged = !goldSlot.getHasStack() && newStack.stackSize <= 16 && mergeItemStack(newStack, 0, 1, false)
        }
///////////////////////////////////////////////////////////////////////////////////////////////////
        if(!isMerged)
        {
            return null;
        }
        if (newStack.stackSize == 0)
        {
            slot.putStack(null);
        }
        else
        {
            slot.onSlotChanged();
        }

        slot.onPickupFromSlot(entityPlayer, newStack);

        return oldStack;
    }
}
