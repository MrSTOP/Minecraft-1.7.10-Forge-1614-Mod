package com.mod.tuto;

import com.mod.tuto.init.BiomesMod;
import com.mod.tuto.init.BlockMod;
import com.mod.tuto.init.EntityMod;
import com.mod.tuto.init.EventsMod;
import com.mod.tuto.init.FuelHndlerMod;
import com.mod.tuto.init.ItemMod;
import com.mod.tuto.init.RemoveMod;
import com.mod.tuto.proxy.CommonProxy;
import com.mod.tuto.world.WorldRegister;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)

public class ModTuto
{
    @Instance(Reference.MOD_ID)
    public static ModTuto instance;
    
    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
    public static CommonProxy proxy;
    
    public static CreativeTabs tabTuto = new CreativeTabs("tabTuto")
    {
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem()
        {
            return Item.getItemFromBlock(BlockMod.block_test);
        }
    };
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        BlockMod.init();
        BlockMod.register();
        ItemMod.init();
        ItemMod.register();
        RemoveMod.init();
        WorldRegister.mainRegsitry();
        BiomesMod.init();
        EventsMod.init();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.registerRenders();
        EntityMod.init();
        GameRegistry.registerFuelHandler(new FuelHndlerMod());
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        
    }
}