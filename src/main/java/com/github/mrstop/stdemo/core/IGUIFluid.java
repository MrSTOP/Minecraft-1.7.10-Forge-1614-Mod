package com.github.mrstop.stdemo.core;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IGUIFluid {

    int getFluidAmount(int tankIndex);

    int getFluidID(int tankIndex);

    @SideOnly(Side.CLIENT)
    int getFluidScale(int tankIndex, int scale);
}
