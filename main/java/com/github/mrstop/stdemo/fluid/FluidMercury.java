package com.github.mrstop.stdemo.fluid;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraftforge.fluids.Fluid;


public class FluidMercury extends Fluid {

    public FluidMercury()
    {
        super("mercury");
        this.setUnlocalizedName("fluidMercury");
        this.setDensity(13600);//设置流体的密度
        this.setViscosity(750);//设置流体粘度
        this.setLuminosity(0);//设置流体亮度
        this.setTemperature(300);//设置流体温度
    }
}
