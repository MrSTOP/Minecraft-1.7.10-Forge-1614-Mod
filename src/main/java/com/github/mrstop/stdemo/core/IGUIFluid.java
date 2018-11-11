package com.github.mrstop.stdemo.core;

public interface IGUIFluid {

    default int getFluidAmount(){
        return getFluidAmount(0);
    }
    int getFluidAmount(int tankIndex);

    default int getFluidID(){
        return getFluidID(0);
    }
    int getFluidID(int tankIndex);

    default int getFluidScale(int scale){
        return getFluidScale(0, scale);
    }
    int getFluidScale(int tankIndex, int scale);
}
