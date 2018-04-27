package com.github.mrstop.stdemo.common;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

public class Log {

    public static Logger Log;

    public Log(FMLPreInitializationEvent event)
    {
        Log = event.getModLog();
    }
}
