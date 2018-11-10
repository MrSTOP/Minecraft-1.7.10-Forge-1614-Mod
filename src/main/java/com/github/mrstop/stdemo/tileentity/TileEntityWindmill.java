package com.github.mrstop.stdemo.tileentity;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityWindmill extends TileEntity implements ISidedInventory, IEnergyProvider {
    private static final float maxPower = 500_000.0F;
    private static final int maxOutput = 50;
    private static final int nextRandomCount = 600;

    private int nextRandom = nextRandomCount;
    private int rotation;
    private int rotationRate;
    private int rotationRateMin;
    private int rotationRateMax;
    private boolean hasRotationRate = false;
    private ItemStack[] windmillItemStacks = new ItemStack[2];
    private String windmillCustomName;
    EnergyStorage energyStorage = new EnergyStorage((int) this.maxPower, this.maxOutput);

    public TileEntityWindmill() {
        super();
        this.rotationRate = (int)(Math.random() * 20);
    }

    public int getRotation() {
        return this.rotation;
    }

    public int getCurrentPower() {
        return this.energyStorage.getEnergyStored();
    }

    public void setCurrentPower(int currentPower) {
        this.energyStorage.setEnergyStored(currentPower);
    }

    public int getPowerScale(int scale){
        return  (int) (((double) this.energyStorage.getEnergyStored() * scale) / this.maxPower);
    }

    @Override
    public void updateEntity() {
        if (!this.worldObj.isRemote) {
            super.updateEntity();
            if (this.nextRandom == 0){
                System.out.print("RANDOM\n");
                this.rotationRate += (Math.random() - 1) * 5;
                if (this.rotationRate <= 0){
                    this.rotationRate = 0;
                }
                this.hasRotationRate = true;
                this.nextRandom = nextRandomCount;
            }
            //产生旋转速度
            if (!this.hasRotationRate) {
                this.rotationRate = (int) (MathHelper.sin(((this.yCoord - 5) / 100.0F)) * 60 + 5);
                System.out.print(this.rotationRate + "\n");
                if (this.rotationRate <= 0){
                    this.rotationRate = 0;
                }
                this.rotationRateMin = this.rotationRate - 10;
                this.rotationRateMax = this.rotationRate + 10;
                if (this.rotationRateMin <= 0){
                    this.rotationRateMin = 0;
                }
                this.hasRotationRate = true;
            }
            //产生能量
            this.energyStorage.modifyEnergyStored(this.rotationRate);
//            System.out.print(this.energyStorage.getEnergyStored() + "\n");
            //判断能量是否达到最大值 EnergyStorage已实现此功能
            //if (this.energyStorage.getEnergyStored() < this.maxPower) {
            //}
            //检测是否存在可以接收能量的TileEntity，如果有则尝试发送能量
            if (this.energyStorage.getMaxEnergyStored() != 0){
                for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
                    int targetX = this.xCoord + dir.offsetX;
                    int targetY = this.yCoord + dir.offsetY;
                    int targetZ = this.zCoord + dir.offsetZ;
                    TileEntity tileEntity = this.worldObj.getTileEntity(targetX, targetY, targetZ);
                    if (tileEntity instanceof IEnergyHandler){
                        int maxAvailable = this.energyStorage.extractEnergy(this.energyStorage.getMaxExtract(), true);
                        int realExtract = ((IEnergyHandler)tileEntity).receiveEnergy(dir.getOpposite(), maxAvailable, false);
                        this.energyStorage.extractEnergy(realExtract, false);
                    }
                }
            }
            this.markDirty();
        }
        //旋转扇叶
        this.rotation += this.rotationRate;
//        System.out.print(this.rotationRate + "<<>>" + this.rotation + "\n");
        //判断旋转量大于360则重置
        if (this.rotation >= 360) {
            this.rotation = 0;
        }
    }

    //TODO 作用不明 好像是用来进行 服务器——客户端 数据同步的
    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTagCompound);
    }

    //TODO 作用不明 好像是用来进行 服务器——客户端 数据同步的
    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.energyStorage.readFromNBT(compound);
        this.rotation = compound.getInteger("rotation");
        this.rotationRate = compound.getInteger("rotationRate");
        this.hasRotationRate = compound.getBoolean("hasRotationRate");
        this.rotationRateMin = compound.getInteger("rotationRateMin");
        this.rotationRateMax = compound.getInteger("rotationRateMax");
        this.nextRandom = compound.getInteger("nextRandom");
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
        this.energyStorage.writeToNBT(compound);
        compound.setInteger("rotation", this.rotation);
        compound.setInteger("rotationRate", this.rotationRate);
        compound.setBoolean("hasRotationRate", this.hasRotationRate);
        compound.setInteger("rotationRateMin", this.rotationRateMin);
        compound.setInteger("rotationRateMax", this.rotationRateMax);
        compound.setInteger("nextRandom", this.nextRandom);
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
        return this.isCustomInventoryName() ? this.windmillCustomName : "container.windmill.name";
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

    @Override
    public int extractEnergy(ForgeDirection forgeDirection, int energyExtract, boolean isSimulate) {
        return this.energyStorage.extractEnergy(energyExtract, isSimulate);
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
}
