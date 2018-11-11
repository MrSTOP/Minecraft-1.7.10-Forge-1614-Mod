package com.github.mrstop.stdemo.tileentity;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import com.github.mrstop.stdemo.core.IGUIEnergy;
import com.github.mrstop.stdemo.core.IGUIFluid;
import com.github.mrstop.stdemo.core.IGUIProcessTime;
import com.github.mrstop.stdemo.crafting.CraftingLoader;
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

public class TileEntityMachineCalciner extends TileEntity implements IEnergyReceiver, ISidedInventory, IFluidHandler, IGUIProcessTime, IGUIEnergy, IGUIFluid {
    private static final int fluidTankCapacity = 10_000;
    private static final int totalProcessTime = 200;
    private static final int energyCapacity = 10_000;
    private static final int energyMaxInput = 30;

    @SideOnly(Side.CLIENT)
    public int GUIFluidAmount = 0;
    @SideOnly(Side.CLIENT)
    public int GUIEnergyAmount = 0;
    @SideOnly(Side.CLIENT)
    public int GUIProcessTime = 0;
    @SideOnly(Side.CLIENT)
    public int GUIFluidID = 0;

    private FluidTank fluidTank;
    private EnergyStorage energyStorage;
    private int processTime;
    private ItemStack[] machineCalcinerItemStack;
    private String machineCalcinerCustomName = null;

    public TileEntityMachineCalciner() {
        this.machineCalcinerItemStack = new ItemStack[2];
        this.fluidTank = new FluidTank(fluidTankCapacity);
        this.energyStorage = new EnergyStorage(energyCapacity, energyMaxInput);
    }

    @Override
    public void updateEntity() {
        if (!this.worldObj.isRemote){
            if (this.energyStorage.getEnergyStored() >= 0 && this.machineCalcinerItemStack[0] != null){
                if (canCalcine()){
                    this.energyStorage.modifyEnergyStored(-30);
                    this.processTime += 20;
                    if (this.processTime >= totalProcessTime){
                        calcineItem();
                        this.processTime = 0;
                    }
                    this.markDirty();
                }
            }
        }
    }

    private boolean canCalcine(){
        boolean canCalcineItem = false;
        boolean canCalcineFluid = false;

        ItemStack itemStackOut = CraftingLoader.recipeCalciner.getItemResult(this.machineCalcinerItemStack[0]);
        FluidStack fluidStackOut = CraftingLoader.recipeCalciner.getFluidResult(this.machineCalcinerItemStack[0]);
        if (itemStackOut == null){
            canCalcineItem = true;
        }
        if (fluidStackOut == null){
            canCalcineFluid = true;
        }

        if (this.machineCalcinerItemStack[1] == null){
            canCalcineItem = true;
        }
        else if (itemStackOut != null){
            if (this.machineCalcinerItemStack[1].isItemEqual(itemStackOut)){
                int stackSize = this.machineCalcinerItemStack[1].stackSize + itemStackOut.stackSize;
                if (stackSize <= this.getInventoryStackLimit() && stackSize <= this.machineCalcinerItemStack[1].getMaxStackSize()){
                    canCalcineItem = true;
                }
            }
        }

        if (this.fluidTank.getFluidAmount() == 0){
            canCalcineFluid = true;
        }
        else if (fluidStackOut != null){
            if (this.fluidTank.getFluid().isFluidEqual(fluidStackOut)){
                int fluidAmount = this.fluidTank.getFluidAmount() + fluidStackOut.amount;
                if (fluidAmount <= this.fluidTank.getCapacity()){
                    canCalcineFluid = true;
                }
            }
        }
        return canCalcineItem && canCalcineFluid;
    }

    private void calcineItem(){
        ItemStack itemStackOut = CraftingLoader.recipeCalciner.getItemResult(this.machineCalcinerItemStack[0]);
        FluidStack fluidStackOut = CraftingLoader.recipeCalciner.getFluidResult(this.machineCalcinerItemStack[0]);
        if (itemStackOut != null){
            if (this.machineCalcinerItemStack[1] == null){
                this.machineCalcinerItemStack[1] = itemStackOut.copy();
            }
            else if (this.machineCalcinerItemStack[1].getItem() == itemStackOut.getItem()){
                this.machineCalcinerItemStack[1].stackSize += itemStackOut.stackSize;
            }
        }
        if (fluidStackOut != null){
            if (this.fluidTank.getFluidAmount() == 0){
                this.fluidTank.setFluid(fluidStackOut.copy());
            }
            else if (this.fluidTank.getFluid().isFluidEqual(fluidStackOut)){
                this.fluidTank.fill(fluidStackOut, true);
            }
        }
        --this.machineCalcinerItemStack[0].stackSize;
        if (this.machineCalcinerItemStack[0].stackSize <= 0){
            this.machineCalcinerItemStack[0] = null;
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.fluidTank.readFromNBT(compound);
        this.energyStorage.readFromNBT(compound);
        this.processTime = compound.getInteger("ProcessTime");
        NBTTagList nbtTagList = compound.getTagList("Items", 0);
        for (int i = 0; i < nbtTagList.tagCount(); ++i) {
            NBTTagCompound tagCompound = nbtTagList.getCompoundTagAt(i);
            byte b = tagCompound.getByte("Slot");
            if (b >=0 && b < this.machineCalcinerItemStack.length){
                this.machineCalcinerItemStack[b] = ItemStack.loadItemStackFromNBT(compound);
            }
        }
        if (compound.hasKey("CustomName", 8)){
            this.machineCalcinerCustomName = compound.getString("CustomName");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        this.fluidTank.writeToNBT(compound);
        this.energyStorage.writeToNBT(compound);
        compound.setInteger("ProcessTime", this.processTime);
        NBTTagList nbtTagList = new NBTTagList();
        for (int i = 0; i < this.machineCalcinerItemStack.length; ++i) {
            if (this.machineCalcinerItemStack[i] != null){
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) i);
                this.machineCalcinerItemStack[i].writeToNBT(tagCompound);
                nbtTagList.appendTag(tagCompound);
            }
        }
        compound.setTag("Items", nbtTagList);
        if (this.isCustomInventoryName()){
            compound.setString("CustomName", this.machineCalcinerCustomName);
        }
    }

    @Override
    public int receiveEnergy(ForgeDirection forgeDirection, int receive, boolean isSimulate) {
        return this.energyStorage.receiveEnergy(receive, isSimulate);
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
        return this.machineCalcinerItemStack.length;
    }

    @Override
    public ItemStack getStackInSlot(int slotIn) {
        return this.machineCalcinerItemStack[slotIn];
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack itemstack = null;
        if (this.machineCalcinerItemStack[index] != null) {
            if (this.machineCalcinerItemStack[index].stackSize <= count) {
                itemstack = this.machineCalcinerItemStack[index];
                this.machineCalcinerItemStack[index] = null;
            } else {
                itemstack = this.machineCalcinerItemStack[index].splitStack(count);
                if (this.machineCalcinerItemStack[index].stackSize == 0) {
                    this.machineCalcinerItemStack[index] = null;
                }
            }
        }
        return itemstack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int index) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.machineCalcinerItemStack[index] = stack;
        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public String getInventoryName() {
        return this.isCustomInventoryName() ? this.machineCalcinerCustomName : "container.machineCalciner.name";
    }

    @Override
    public boolean isCustomInventoryName() {
        return this.machineCalcinerCustomName != null && this.machineCalcinerCustomName.length() > 0;
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
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return this.fluidTank.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (resource == null){
            return null;
        }
        if (!resource.isFluidEqual(this.fluidTank.getFluid())){
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

    @Override
    public int getProcessTime() {
        return this.processTime;
    }

    @Override
    public int getProcessTimeScale(int scale) {
        return (int)(((double)this.GUIProcessTime / this.totalProcessTime) * scale);
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
        return (int) (((double) this.GUIFluidAmount / this.fluidTankCapacity) * scale);
    }
}
