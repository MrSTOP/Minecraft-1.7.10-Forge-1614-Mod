package com.github.mrstop.stdemo.tileentity;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
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

public class TileEntityRedstoneFluxFurnace extends TileEntity implements ISidedInventory, IEnergyHandler {

    private static final int processTime = 100;

    private static final int[] slotTop = new int[]{0};
    private static final int[] slotSide = new int[]{2, 1};
    private ItemStack[] redstoneFluxFurnaceItemStack = new ItemStack[2];
    public int redstoneFluxFurnaceCookTime;
    private String redstoneFluxFurnaceCustomName = "container.metalFurnace";

    private int capacity = 1000000;
    private int maxReceive = 30;
    private int maxExtract = 0;
    public EnergyStorage energyStorage = new EnergyStorage(capacity, maxReceive, maxExtract);

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
        if (this.redstoneFluxFurnaceItemStack[index] != null) {
            ItemStack itemstack = this.redstoneFluxFurnaceItemStack[index];
            this.redstoneFluxFurnaceItemStack[index] = null;
            return itemstack;
        } else {
            return null;
        }
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
        return true;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        energyStorage.readFromNBT(compound);
        NBTTagList nbtTagList = compound.getTagList("Items", 0);
        this.redstoneFluxFurnaceItemStack = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbtTagList.tagCount(); ++i)
        {
            NBTTagCompound nbtTagCompound = nbtTagList.getCompoundTagAt(i);
            byte byte0 = nbtTagCompound.getByte("Slot");

            if (byte0 >= 0 && byte0 < this.redstoneFluxFurnaceItemStack.length)
            {
                this.redstoneFluxFurnaceItemStack[byte0] = ItemStack.loadItemStackFromNBT(nbtTagCompound);
            }
        }

        if (compound.hasKey("CustomName", 8))
        {
            this.redstoneFluxFurnaceCustomName = compound.getString("CustomName");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        energyStorage.writeToNBT(compound);
        NBTTagList nbtTagList = new NBTTagList();

        for (int i = 0; i < this.redstoneFluxFurnaceItemStack.length; ++i)
        {
            if (this.redstoneFluxFurnaceItemStack[i] != null)
            {
                NBTTagCompound nbtTagCompound = new NBTTagCompound();
                nbtTagCompound.setByte("Slot", (byte) i);
                this.redstoneFluxFurnaceItemStack[i].writeToNBT(nbtTagCompound);
                nbtTagList.appendTag(nbtTagCompound);
            }
        }

        compound.setTag("Items", nbtTagList);
        if (this.isCustomInventoryName())
        {
            compound.setString("CustomName", this.redstoneFluxFurnaceCustomName);
        }
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int time)
    {
        return this.redstoneFluxFurnaceCookTime * time / processTime;
    }


    @Override
    public void updateEntity()
    {
        //boolean flag = this.redstoneFluxFurnaceBurnTime > 0;
        boolean isDirty = false;
        if (!this.worldObj.isRemote)
        {
            /*if (flag)
            {
                --this.redstoneFluxFurnaceBurnTime;
            }*/
            if (energyStorage.getEnergyStored() > 0 && this.redstoneFluxFurnaceItemStack[0] != null )
            {
                if (this.canSmelt())
                {
                    int energy = energyStorage.getEnergyStored();
                    energy -= 30;
                    ++this.redstoneFluxFurnaceCookTime;
                    energyStorage.setEnergyStored(energy);
                    if (this.redstoneFluxFurnaceCookTime == processTime)
                    {
                        this.redstoneFluxFurnaceCookTime = 0;
                        this.smeltItem();
                        isDirty = true;
                    }
                }
            }

            /*if (flag != this.redstoneFluxFurnaceBurnTime > 0)
            {
                isDirty = true;
                BlockRedstoneFluxFurnace.updateRedstoneFluxFurnace(this.redstoneFluxFurnaceBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }*/
        }
        if (isDirty)
        {
            this.markDirty();
        }
    }

    private boolean canSmelt()
    {
        if (this.redstoneFluxFurnaceItemStack[0] == null)                                                                      //原料槽为空
        {
            return false;                                                                                               //返回false
        }
        else
        {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.redstoneFluxFurnaceItemStack[0]);           //临时物品栈
            if (itemstack == null)                                                                                      //临时物品栈为空时返回false
                return false;
            if (this.redstoneFluxFurnaceItemStack[1] == null)                                                                  //产物槽为空返回true
                return true;
            if (!this.redstoneFluxFurnaceItemStack[1].isItemEqual(itemstack))                                                  //将ItemStack参数与实例ItemStack进行比较; 如果两个ItemStacks中的Items都相等，则返回true
                return false;                                                                                           //产物槽不是原料的产物时返回false
            int result = redstoneFluxFurnaceItemStack[1].stackSize + itemstack.stackSize;
            return result <= getInventoryStackLimit() && result <= this.redstoneFluxFurnaceItemStack[1].getMaxStackSize();     //烧练结束时结果小于产物槽最大物品容量且小于产物物品栈最大物品数量
            //Forge BugFix: Make it respect stack sizes properly.Forge BugFix：正确地尊重堆栈大小。
        }
    }

    public void smeltItem()
    {
        if (this.canSmelt())                                                                                            //判断是否能烧练
        {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.redstoneFluxFurnaceItemStack[0]);           //临时产物物品栈

            if (this.redstoneFluxFurnaceItemStack[1] == null)                                                                  //判断产物物品栈是否为空
            {
                this.redstoneFluxFurnaceItemStack[1] = itemstack.copy();                                                       //将获得的烧练合成表产物设为烧练产物
            }
            else if (this.redstoneFluxFurnaceItemStack[1].getItem() == itemstack.getItem())                                    //判断产物物品栈是否为烧练合成表产物
            {
                this.redstoneFluxFurnaceItemStack[1].stackSize += itemstack.stackSize;                                         //增加产物物品栈数量
                // Forge BugFix: Results may have multiple items Forge BugFix:结果可能有多个物品
            }

            --this.redstoneFluxFurnaceItemStack[0].stackSize;                                                                  //减少原料物品栈数量

            if (this.redstoneFluxFurnaceItemStack[0].stackSize <= 0)                                                           //判断原料物品栈是否小于等于0
            {
                this.redstoneFluxFurnaceItemStack[0] = null;                                                                   //将原料物品栈设为null
            }
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

    /*public boolean isBurning()
    {
        return this.redstoneFluxFurnaceBurnTime > 0;
    }*/

    public void setCustomInventoryName(String customInventoryName)
    {
        this.redstoneFluxFurnaceCustomName = customInventoryName;
    }

    @Override
    public String getInventoryName()
    {
        return this.isCustomInventoryName() ? this.redstoneFluxFurnaceCustomName : "container.furnace";
    }

    //////////////////////RF////////////////////////////
    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        return energyStorage.receiveEnergy(Math.min(maxReceive, this.maxReceive), simulate); // The energy storage already handles energy flow
        // The from variable is where the energy is coming from; allows for getting energy to come from specific sides (similar to ISidedInventory)
        // The maxReceive variable is just how much energy is received.
        // The simulate just tells us whether to simulate (not actually do anything) the action.
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        return energyStorage.extractEnergy(Math.min(maxExtract, this.maxExtract), simulate);
        // The from variable is where the energy is coming from; allows for getting energy to come from specific sides (similar to ISidedInventory)
        // The maxReceive variable is just how much energy is received.
        // The simulate just tells us whether to simulate (not actually do anything) the action.
    }

    @Override
    public int getEnergyStored(ForgeDirection side) {
        return energyStorage.getEnergyStored();
        // The side variable is which side to check for energy in. Can be used for storing energy on specific sides or something complex similar
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection forgeDirection)
    {
        return this.capacity;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection forgeDirection)
    {
        return true;
    }

    //////////////////////RF////////////////////////////
}
