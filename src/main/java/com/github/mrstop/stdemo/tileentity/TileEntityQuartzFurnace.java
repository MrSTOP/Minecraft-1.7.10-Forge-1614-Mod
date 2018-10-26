package com.github.mrstop.stdemo.tileentity;

import net.minecraft.tileentity.TileEntity;

public class TileEntityQuartzFurnace extends TileEntity {
    private String quartzFurnaceCustomName = null;

    public TileEntityQuartzFurnace() {
    }

    public void setQuartzFurnaceCustomName(String quartzFurnaceCustomName) {
        this.quartzFurnaceCustomName = quartzFurnaceCustomName;
    }
}
