package com.github.mrstop.stdemo.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityWindmill extends TileEntity implements ISidedInventory {
    private int rotation;
    private int rotationRate;
    private boolean hasRotationRate = false;
    private final int maxPower = 10000;
    private float currentPower = 0.0F;
    private float powerPerRotation = 0.0F;
    private ItemStack[] windmillItemStacks = new ItemStack[2];
    private String windmillCustomName = "container.windmill";

    public TileEntityWindmill() {
        super();
        this.rotationRate = (int)(Math.random() * 20);
        this.powerPerRotation = this.rotationRate / 5;
    }

    public double getRotation() {
        return this.rotation;
    }

    public float getCurrentPower() {
        return currentPower;
    }

    public void setCurrentPower(float currentPower) {
        this.currentPower = currentPower;
    }

    public int getPowerScale(int scale){
        return (int) ((this.currentPower / this.maxPower) * scale);
    }

    @Override
    public void updateEntity() {
        if (!this.worldObj.isRemote) {
            super.updateEntity();
            //产生旋转速度
            if (!this.hasRotationRate) {
                this.rotationRate = (int) (Math.log(yCoord / 10) * 8);
                this.powerPerRotation = this.rotationRate / 5;
                this.hasRotationRate = true;
            }
            //判断能量是否达到最大值
            if (this.currentPower < this.maxPower) {
                //产生能量
                this.currentPower += this.powerPerRotation;
            }
            //this.markDirty();
        }
        //旋转扇叶
        this.rotation += this.rotationRate;
        //判断旋转量大于360则重置
        if (this.rotation >= 360) {
            this.rotation = 0;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.rotation = compound.getInteger("rotation");
        this.rotationRate = compound.getInteger("rotationRate");
        this.hasRotationRate = compound.getBoolean("hasRotationRate");
        this.currentPower = compound.getFloat("currentPower");
        this.powerPerRotation = compound.getFloat("powerPerRotation");
        NBTTagList nbtTagList = compound.getTagList("Items", 10);
        this.windmillItemStacks = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < nbtTagList.tagCount(); ++i) {
            NBTTagCompound tagCompound = nbtTagList.getCompoundTagAt(i);
            byte b = tagCompound.getByte("Slot");
            if (b >= 0 && b < this.windmillItemStacks.length){
                this.windmillItemStacks[b] = ItemStack.loadItemStackFromNBT(tagCompound);
            }
        }
        if (compound.hasKey("CustomName")){
            this.setInventoryName(compound.getString("CustomName"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("rotation", rotation);
        compound.setInteger("rotationRate", rotationRate);
        compound.setBoolean("hasRotationRate", hasRotationRate);
        compound.setFloat("currentPower", currentPower);
        compound.setFloat("powerPerRotation", powerPerRotation);
        NBTTagList nbtTagList = new NBTTagList();
        for (int i = 0; i < this.windmillItemStacks.length; ++i) {
            if (this.windmillItemStacks[i] != null){
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte)i);
                this.windmillItemStacks[i].writeToNBT(tagCompound);
                nbtTagList.appendTag(tagCompound);
            }
        }
//????????????????????????????????????????????????????????????????????????????????????
//???for (int i = 0; i < this.windmillItemStacks.length; i++) {                    ???
//???            if (this.windmillItemStacks[i] != null){                          ???
//???                NBTTagCompound tagCompound = new NBTTagCompound();            ???
//???                tagCompound.setByte("Slot", (byte)i);                         ???
//???                this.windmillItemStacks[i].writeToNBT(tagCompound);           ???
//???                nbtTagList.appendTag(nbtTagList);<<FUCK                       ???
//???            }                                                                 ???
//???        }                                                                     ???
//????????????????????????????????????????????????????????????????????????????????????
        compound.setTag("Items", nbtTagList);
        if (this.isCustomInventoryName()){
            compound.setString("CustomName", this.getInventoryName());
        }
    }

    @Override
    public int[] getSlotsForFace(int p_94128_1_) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
        return false;
    }

    @Override
    public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
        return false;
    }

    @Override
    public int getSizeInventory() {
        return this.windmillItemStacks.length;
    }

    @Override
    public ItemStack getStackInSlot(int slotIn) {
        return this.windmillItemStacks[slotIn];
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack itemStack = null;
        if (this.windmillItemStacks[index] != null){
            if (this.windmillItemStacks[index].stackSize <= count){
                itemStack = this.windmillItemStacks[index];
                this.windmillItemStacks[index] = null;
            }
            else {
                itemStack = this.windmillItemStacks[index].splitStack(count);
                if (this.windmillItemStacks[index].stackSize == 0){
                    this.windmillItemStacks[index] = null;
                }
            }
        }
        return itemStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int index) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.windmillItemStacks[index] = stack;
//        似乎ItemStack.stackSize始终小于等于getInventoryStackLimit()?
        if (stack != null && stack.stackSize > this.getInventoryStackLimit()){
            stack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public String getInventoryName() {
        return this.isCustomInventoryName() ? this.windmillCustomName : "container.windmill";
    }

    public void setInventoryName(String name){
        this.windmillCustomName = name;
    }

    @Override
    public boolean isCustomInventoryName() {
        return this.windmillCustomName != null && this.windmillCustomName.length() > 0;
    }

    @Override
    public int getInventoryStackLimit() {
        return 10;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openChest() {

    }

    @Override
    public void closeChest() {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return false;
    }
}
