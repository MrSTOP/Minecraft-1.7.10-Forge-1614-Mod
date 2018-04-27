package com.github.mrstop.stdemo.fluid;


import com.github.mrstop.stdemo.common.ConfigLoader;
import com.github.mrstop.stdemo.common.Log;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class FluidLoader {

    public static final Fluid fluidMercury = new FluidMercury();

    public FluidLoader(FMLPreInitializationEvent event)
    {
        if (FluidRegistry.isFluidRegistered(fluidMercury))
        {
            event.getModLog().info("Found fluid {}, the regidtration is canceled.", fluidMercury.getName());
        }
        else
        {
            FluidRegistry.registerFluid(fluidMercury);
        }
        Log.Log.info("FLUID REGISTER");
    }
}
