package com.github.mrstop.stdemo.tileentity;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import com.github.mrstop.stdemo.core.IGUIEnergy;
import com.github.mrstop.stdemo.core.IGUIFluid;
import com.github.mrstop.stdemo.core.IGUIProcessTime;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileEntityMachineElectrolyticMachine extends TileEntity implements ISidedInventory, IFluidHandler, IEnergyReceiver, IGUIProcessTime, IGUIEnergy, IGUIFluid {
    private static final int fluidTankCapacity = 10_000;
    private static final int totalProcessTime = 100;
    private static final int energyCapacity = 10_000;
    private static final int energyMaxInput = 30;
    private static final int[] slotTop = new int[]{0};
    private static final int[] slotSide = new int[]{1};

    @SideOnly(Side.CLIENT)
    public int GUIFluidAmount = 0;
    @SideOnly(Side.CLIENT)
    public int GUIEnergyAmount = 0;
    @SideOnly(Side.CLIENT)
    public int GUIProcessTime = 0;
    @SideOnly(Side.CLIENT)
    public int GUIFluidID = 0;

    private FluidTank fluidTank;
    private EnergyStorage energyStorage = new EnergyStorage(this.energyCapacity, this.energyMaxInput);
    private int processTime = 0;
    private ItemStack[] machineElectrolyticMachineItemStack = new ItemStack[2];

    private String electrolyticMachineCustomName = null;

    public TileEntityMachineElectrolyticMachine() {
        this.fluidTank = new FluidTank(this.fluidTankCapacity);
    }

    public void setCustomInventoryName(String electrolyticMachineCustomName) {
        this.electrolyticMachineCustomName = electrolyticMachineCustomName;
    }

    @Override
    public void updateEntity() {
        if (!this.worldObj.isRemote){
        }
//        worldObj.markBlockRangeForRenderUpdate();
//        worldObj.notifyBlockChange();
//        worldObj.scheduleBlockUpdate();
        this.markDirty();
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1

















                , nbtTagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.fluidTank.readFromNBT(compound);
        this.energyStorage.readFromNBT(compound);
        this.fluidTank.readFromNBT(compound);
        this.processTime = compound.getInteger("ProcessTime");
        NBTTagList nbtTagList = compound.getTagList("Items", 10);
        this.machineElectrolyticMachineItemStack = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < nbtTagList.tagCount(); ++i) {
            NBTTagCompound tagCompound = nbtTagList.getCompoundTagAt(i);
            byte b = tagCompound.getByte("Slot");
            if (b >= 0 && b < this.machineElectrolyticMachineItemStack.length){
                this.machineElectrolyticMachineItemStack[b] = ItemStack.loadItemStackFromNBT(tagCompound);
            }
        }
        if (compound.hasKey("CustomName", 8)){
            this.electrolyticMachineCustomName = compound.getString("CustomName");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        this.fluidTank.writeToNBT(compound);
        this.energyStorage.writeToNBT(compound);
        this.fluidTank.writeToNBT(compound);
        compound.setInteger("ProcessTime", this.processTime);
        NBTTagList nbtTagList = new NBTTagList();
        for (int i = 0; i < this.machineElectrolyticMachineItemStack.length; ++i) {
            if (this.machineElectrolyticMachineItemStack[i] != null){
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) i);
                this.machineElectrolyticMachineItemStack[i].writeToNBT(tagCompound);
                nbtTagList.appendTag(tagCompound);
            }
        }
        compound.setTag("Items", nbtTagList);
        if (this.isCustomInventoryName()){
            compound.setString("CustomName", this.electrolyticMachineCustomName);
        }
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return this.fluidTank.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (resource == null){
            //流体栈是空的
            return null;
        }
        if (!resource.isFluidEqual(this.fluidTank.getFluid())){
            //传入的流体栈与储罐中的流体栈不同
            return null;
        }
        return this.drain(from, resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return this.fluidTank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        Fluid tankFluid = this.fluidTank.getFluid().getFluid();
        return tankFluid == null || tankFluid == fluid;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        Fluid tankFluid = this.fluidTank.getFluid().getFluid();
        return tankFluid != null && tankFluid == fluid;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[]{this.fluidTank.getInfo()};
    }

    public boolean isUsedByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public int receiveEnergy(ForgeDirection forgeDirection, int eneygyCount, boolean isSimulate) {
        return this.energyStorage.receiveEnergy(eneygyCount, isSimulate);
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

    @Override
    public int[] getSlotsForFace(int side) {
        return side == 1 ? slotTop : slotSide;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack itemStack, int side) {
        if (side == 0){
            return false;
        }
        return this.isItemValidForSlot(slot, itemStack) && this.machineElectrolyticMachineItemStack[slot].isItemEqual(itemStack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
        if (side == 1 && slot == 1){
            return false;
        }
        return true;
    }

    @Override
    public int getSizeInventory() {
        return this.machineElectrolyticMachineItemStack.length;
    }

    @Override
    public ItemStack getStackInSlot(int slotIn) {
        return this.machineElectrolyticMachineItemStack[slotIn];
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack itemStack = null;
        if (this.machineElectrolyticMachineItemStack[index] != null){
            if (this.machineElectrolyticMachineItemStack[index].stackSize <= count){
                itemStack = this.machineElectrolyticMachineItemStack[index];
                this.machineElectrolyticMachineItemStack[index] = null;
            }
            else {
                itemStack = this.machineElectrolyticMachineItemStack[index].splitStack(count);
                if (this.machineElectrolyticMachineItemStack[index].stackSize == 0){
                    this.machineElectrolyticMachineItemStack[index] = null;
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
        this.machineElectrolyticMachineItemStack[index] = stack;
        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public String getInventoryName() {
        return this.isCustomInventoryName() ? this.electrolyticMachineCustomName : "container.machineElectrolyticMachine.name";
    }

    @Override
    public boolean isCustomInventoryName() {
        return this.electrolyticMachineCustomName != null && this.electrolyticMachineCustomName.length() > 0;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
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
        return index == 1 ? false : true;
    }

    @Override
    public int getProcessTime() {
        return this.processTime;
    }

    @Override
    public int getProcessTimeScale(int scale) {
        return (int)(((double)this.processTime / this.totalProcessTime) * scale);
    }

    @Override
    public int getEnergyAmount() {
        return this.energyStorage.getEnergyStored();
    }

    @Override
    public int getEnergyScale(int scale) {
        return (int) (((double) this.GUIEnergyAmount / this.energyCapacity) * scale);
    }

    @Override
    public int getFluidAmount(int tankIndex) {
        return this.fluidTank.getFluidAmount();
    }

    @Override
    public int getFluidID(int tankIndex) {
        if (this.fluidTank.getFluid() != null){
            return this.fluidTank.getFluid().getFluidID();
        }
        return 0;
    }

    @Override
    public int getFluidScale(int tankIndex, int scale) {
        return (int) (((double) this.GUIFluidAmount / this.fluidTankCapacity) * scale);;
    }
}
