package com.github.mrstop.stdemo.tileentity;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import com.github.mrstop.stdemo.block.BlockRedstoneFluxFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRedstoneFluxFurnace extends TileEntity implements ISidedInventory, IEnergyReceiver {

    private static final int processTime = 100;
    private static final int[] slotTop = new int[]{0};
    private static final int[] slotSide = new int[]{2, 1};
    private static int capacity = 1000000;
    private static int maxReceive = 30;

    private ItemStack[] redstoneFluxFurnaceItemStack = new ItemStack[2];
    public int redstoneFluxFurnaceCookTime;
    private String redstoneFluxFurnaceCustomName = null;

    public EnergyStorage energyStorage = new EnergyStorage(capacity, maxReceive);

    @Override
    public int getSizeInventory() {
        return this.redstoneFluxFurnaceItemStack.length;
    }

    @Override
    public ItemStack getStackInSlot(int slotIn) {
        return this.redstoneFluxFurnaceItemStack[slotIn];
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (this.redstoneFluxFurnaceItemStack[index] != null) {
            ItemStack itemstack;                                                   //临时ItemStack变量
            if (this.redstoneFluxFurnaceItemStack[index].stackSize <= count)              //判断物品栈数量，若小于指定数量则设当前物品栈为空，并将当前物品栈返回
            {
                itemstack = this.redstoneFluxFurnaceItemStack[index];
                this.redstoneFluxFurnaceItemStack[index] = null;                          //设当前物品栈为空，
                return itemstack;                                                  //将当前物品栈返回。
            } else                                                                   //否则从当前物品栈中间减去指定数量
            {
                itemstack = this.redstoneFluxFurnaceItemStack[index].splitStack(count);   //从当前物品栈中间减去指定数量
                if (this.redstoneFluxFurnaceItemStack[index].stackSize == 0)              //判断当前物品栈是否为0
                {
                    this.redstoneFluxFurnaceItemStack[index] = null;                      //为0则将当前物品栈设为null
                }
                return itemstack;                                                  //返回临时ItemStack
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int index) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.redstoneFluxFurnaceItemStack[index] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public boolean isCustomInventoryName() {
        return this.redstoneFluxFurnaceCustomName != null && this.redstoneFluxFurnaceCustomName.length() > 0;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        energyStorage.readFromNBT(compound);
        this.redstoneFluxFurnaceItemStack = new ItemStack[this.getSizeInventory()];
        NBTTagList nbtTagList = compound.getTagList("Items", 0);
        for (int i = 0; i < nbtTagList.tagCount(); ++i) {
            NBTTagCompound nbtTagCompound = nbtTagList.getCompoundTagAt(i);
            byte byte0 = nbtTagCompound.getByte("Slot");
            if (byte0 >= 0 && byte0 < this.redstoneFluxFurnaceItemStack.length) {
                this.redstoneFluxFurnaceItemStack[byte0] = ItemStack.loadItemStackFromNBT(nbtTagCompound);
            }
        }
        if (compound.hasKey("CustomName", 8)) {
            this.redstoneFluxFurnaceCustomName = compound.getString("CustomName");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        energyStorage.writeToNBT(compound);
        NBTTagList nbtTagList = new NBTTagList();
        for (int i = 0; i < this.redstoneFluxFurnaceItemStack.length; ++i) {
            if (this.redstoneFluxFurnaceItemStack[i] != null) {
                NBTTagCompound nbtTagCompound = new NBTTagCompound();
                nbtTagCompound.setByte("Slot", (byte) i);
                this.redstoneFluxFurnaceItemStack[i].writeToNBT(nbtTagCompound);
                nbtTagList.appendTag(nbtTagCompound);
            }
        }
        compound.setTag("Items", nbtTagList);
        if (this.isCustomInventoryName()) {
            compound.setString("CustomName", this.redstoneFluxFurnaceCustomName);
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int time) {
        return this.redstoneFluxFurnaceCookTime * time / processTime;
    }


    @Override
    public void updateEntity() {
        boolean isDirty = false;
        if (!this.worldObj.isRemote) {
            if (energyStorage.getEnergyStored() > 0 && this.redstoneFluxFurnaceItemStack[0] != null) {
                if (this.canSmelt()) {
                    this.energyStorage.modifyEnergyStored(-50);
                    this.redstoneFluxFurnaceCookTime += 10;
                    if (this.redstoneFluxFurnaceCookTime >= processTime) {
                        this.redstoneFluxFurnaceCookTime = 0;
                        this.smeltItem();
                        isDirty = true;
                    }
                }
            }
        }
        if (isDirty) {
            this.markDirty();
        }
    }

    private boolean canSmelt() {
        if (this.redstoneFluxFurnaceItemStack[0] == null || this.energyStorage.getEnergyStored() <=0) {
            return false;
        }
        ItemStack produceItemStack = FurnaceRecipes.instance().getSmeltingResult(this.redstoneFluxFurnaceItemStack[0]);
        if (produceItemStack == null) {
            return false;
        }
        else if (this.redstoneFluxFurnaceItemStack[1] == null){
            return true;
        }
        else if (!this.redstoneFluxFurnaceItemStack[1].isItemEqual(produceItemStack)) {
            return false;
        }
        else {
            int result = redstoneFluxFurnaceItemStack[1].stackSize + produceItemStack.stackSize;
            return result <= getInventoryStackLimit() && result <= this.redstoneFluxFurnaceItemStack[1].getMaxStackSize();
        }
    }

    public void smeltItem() {
        ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.redstoneFluxFurnaceItemStack[0]);
        if (this.redstoneFluxFurnaceItemStack[1] == null) {
            this.redstoneFluxFurnaceItemStack[1] = itemstack.copy();
        }
        else if (this.redstoneFluxFurnaceItemStack[1].getItem() == itemstack.getItem()) {
            this.redstoneFluxFurnaceItemStack[1].stackSize += itemstack.stackSize;
            // Forge BugFix: Results may have multiple items Forge BugFix:结果可能有多个物品
        }
        --this.redstoneFluxFurnaceItemStack[0].stackSize;
        if (this.redstoneFluxFurnaceItemStack[0].stackSize <= 0) {
            this.redstoneFluxFurnaceItemStack[0] = null;
        }
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
        //如果worldObj的TileEntity不是这个TileEntity则返回false，如果worldObj的TileEntity是这个TileEntity则返回(玩家距离该TileEntity的直线距离小于等于64.0D则返回true，否则返回false)
    }

    @Override
    public void openChest() {}

    @Override
    public void closeChest() {}

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return index == 1 ? false : true;
        //如果为产物槽返回false如果不是产物槽返回(如果是燃料槽返回(判断是否为燃料是返回true，否则返回false)如果不是返回true（即判断结果为原料槽）)
    }

    @Override
    public int[] getSlotsForFace(int slotsSides)
    {
        return slotsSides == 1 ? slotTop : slotSide;
        //slotsSides等于0返回slotBottom为1返回slotTop为其他返回slotSide
    }

    @Override
    public boolean canInsertItem(int slots, ItemStack itemStack, int sides)
    {
        return this.isItemValidForSlot(slots, itemStack);
    }

    @Override
    public boolean canExtractItem(int slots, ItemStack itemStack, int sides)
    {
        return sides != 0 || slots != 1 || itemStack.getItem() == Items.bucket;
        //如果不是下面或物品槽不是原料槽或物品是桶返回true
    }

    public boolean isCooking() {
        return this.redstoneFluxFurnaceCookTime > 0;
    }

    public void setCustomInventoryName(String customInventoryName) {
        this.redstoneFluxFurnaceCustomName = customInventoryName;
    }

    @Override
    public String getInventoryName() {
        return this.isCustomInventoryName() ? this.redstoneFluxFurnaceCustomName : "stdemo.container.furnace";
    }

    //////////////////////RF////////////////////////////

    @Override
    public int receiveEnergy(ForgeDirection forgeDirection, int energyReceive, boolean isSimulate) {
        return this.energyStorage.receiveEnergy(energyReceive, isSimulate);
    }

    @Override
    public int getEnergyStored(ForgeDirection forgeDirection) {
        return this.energyStorage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection forgeDirection) {
        return this.energyStorage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection forgeDirection) {
        return true;
    }

    //////////////////////RF////////////////////////////
}
