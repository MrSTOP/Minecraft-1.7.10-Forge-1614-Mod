package com.github.mrstop.stdemo.tileentity;

import com.github.mrstop.stdemo.core.IGUIProcessTime;
import net.minecraft.tileentity.TileEntity;

public class TileEntityQuartzFurnace extends TileEntity implements IGUIProcessTime {
    private String quartzFurnaceCustomName = null;

    public TileEntityQuartzFurnace() {
    }

    public void setQuartzFurnaceCustomName(String quartzFurnaceCustomName) {
        this.quartzFurnaceCustomName = quartzFurnaceCustomName;
    }

    @Override
    public int getProcessTime() {
        return 0;
    }

    @Override
    public int getProcessTimeScale(int scale) {
        return 0;
    }
}
