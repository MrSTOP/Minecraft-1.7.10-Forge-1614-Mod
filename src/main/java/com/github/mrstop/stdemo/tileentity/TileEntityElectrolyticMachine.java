package com.github.mrstop.stdemo.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileEntityElectrolyticMachine extends TileEntity implements IFluidHandler {
    private FluidTank fluidTank;
    private final int fluidTankCapaticy = 10_000;

    private String electrolyticMachineCustomName = null;

    public TileEntityElectrolyticMachine() {
        this.fluidTank = new FluidTank(this.fluidTankCapaticy);
    }

    public void setCustomInventoryName(String electrolyticMachineCustomName) {
        this.electrolyticMachineCustomName = electrolyticMachineCustomName;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[0];
    }

}
