package com.github.mrstop.stdemo.fluid;


import net.minecraftforge.fluids.Fluid;


public class FluidMercury extends Fluid {
    private static final int color = 0x00F000;

    public FluidMercury() {
        super("mercury");
        this.setUnlocalizedName("fluidMercury");
        this.setDensity(13600);//设置流体的密度
        this.setViscosity(750);//设置流体粘度
        this.setLuminosity(0);//设置流体亮度
        this.setTemperature(300);//设置流体温度
    }

    @Override
    public int getColor() {
        return super.getColor();
    }

}
