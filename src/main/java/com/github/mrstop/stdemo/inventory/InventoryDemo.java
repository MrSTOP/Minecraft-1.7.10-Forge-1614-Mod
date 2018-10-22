package com.github.mrstop.stdemo.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryDemo implements IInventory {

    /** A list of one item containing the result of the crafting formula
     * 包含合成表结果的一个项目列表*/
    private ItemStack[] stackList = new ItemStack[4];
    private Container eventHandler;
    private int inventoryWidth;

    public InventoryDemo(Container container, int width, int height)
    {
        int k = width * height;
        this.stackList = new ItemStack[k];
        this.eventHandler = container;
        this.inventoryWidth = width;
    }
    /**
     * Returns the number of slots in the inventory.
     * 返回清单中的槽数量。
     */
    public int getSizeInventory()
    {
        return 4;
    }

    /**
     * Returns the stack in slot i
     * 返回槽i中的栈
     */
    public ItemStack getStackInSlot(int slotIn)
    {
        return this.stackList[slotIn];
    }

    /**
     * Returns the name of the inventory
     * 返回清单的名称
     */
    public String getInventoryName()
    {
        return "Result";
    }

    /**
     * Returns if the inventory is named
     * 如果清单已被命名，则返回true
     */
    public boolean isCustomInventoryName()
    {
        return false;
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     * 从库存槽（第一个参数）删除至指定数量（第二个参数）的项目，并将它们返回到新堆栈中。
     */
    public ItemStack decrStackSize(int index, int count)
    {
        if (this.stackList[index] != null)
        {
            ItemStack itemstack = this.stackList[0];
            this.stackList[index] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     * 当一些容器被关闭时，他们在每个槽上调用它，然后放弃它作为EntityItem返回的任何东西 - 就像关闭工作台GUI一样。
     */
    public ItemStack getStackInSlotOnClosing(int index)
    {
        if (this.stackList[index] != null)
        {
            ItemStack itemstack = this.stackList[index];
            this.stackList[index] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     * 将给定物品堆迭设置到清单中的指定插槽（可以是合成或盔甲部分）
     */
    public void setInventorySlotContents(int index, ItemStack stack)
    {
        this.stackList[index] = stack;
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?
     * 返回清单插槽的最大堆栈大小。 似乎永远是64，可能会延长。 这不仅仅是一个集合吗？
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think it
     * hasn't changed and skip it.
     * 对于TileEntity，确保包含TileEntity的块在稍后保存到磁盘 - 游戏不会认为它没有更改并跳过它。
     */
    public void markDirty() {}

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     * 不要将该方法的命名为canInteractWith，因为它与Container冲突
     */
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return true;
    }

    public void openChest() {}

    public void closeChest() {}

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     * 如果允许自动化将给定堆栈（忽略堆栈大小）插入给定插槽，则返回true。
     */
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return true;
    }
}