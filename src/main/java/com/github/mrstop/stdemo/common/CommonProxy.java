package com.github.mrstop.stdemo.common;

import com.github.mrstop.stdemo.achievement.AchievementLoader;
import com.github.mrstop.stdemo.command.CommandLoader;
import com.github.mrstop.stdemo.crafting.CraftingLoader;
import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import com.github.mrstop.stdemo.enchantment.EnchantmentLoader;
import com.github.mrstop.stdemo.entity.EntityLoader;
import com.github.mrstop.stdemo.fluid.FluidLoader;
import com.github.mrstop.stdemo.inventory.GUIElementLoader;
import com.github.mrstop.stdemo.item.ItemLoader;
import com.github.mrstop.stdemo.block.BlockLoader;
import com.github.mrstop.stdemo.tileentity.TileEntityLoader;
import com.github.mrstop.stdemo.worldgen.WorldGeneratorLoader;
import cpw.mods.fml.common.event.*;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.Map;
import java.util.TreeMap;


public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        /*Potion[] potionTypes = null;
        for (Field f : Potion.class.getDeclaredFields())
        {
            f.setAccessible(true);
            try
            {
                if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a"))
                {
                    Field modfield = Field.class.getDeclaredField("modifiers");
                    modfield.setAccessible(true);
                    modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);
                    potionTypes = (Potion[]) f.get(null);
                    final Potion[] newPotionTypes = new Potion[256];
                    System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
                    f.set(null, newPotionTypes);
                }
            }
            catch (Exception e)
            {
                System.err.println("Severe error, please report this to the mod author:");
                System.err.println(e);
            }
        }*/

        new Log(event);
        new ConfigLoader(event);//加载配置文件
        new CreativeTabsLoader(event);//进行创作模式标签注册！！必须先于物品和方块完成！！
        new FluidLoader(event);//进行流体注册
        new ItemLoader(event);//进行物品注册
        new BlockLoader(event);//进行方块注册
        new OreDictionaryLoader(event);//进行矿物词典注册
        //new PotionLoader(event);//无法解决（似乎在1.7添加药水效果是很复杂的）
        new EntityLoader();
        new TileEntityLoader(event);
        new EventLoader();
    }

    public void init(FMLInitializationEvent event) {
        new CraftingLoader();
        new EnchantmentLoader();
        new AchievementLoader();
        new WorldGeneratorLoader();
        new GUIElementLoader();
    }

    public void postInit(FMLPostInitializationEvent event) {

        Map<String, Fluid> stringFluidMap = FluidRegistry.getRegisteredFluids();
        Map<Fluid, Integer> fluidIntegerMap = FluidRegistry.getRegisteredFluidIDsByFluid();

        TreeMap<Integer, String> sortMap = new TreeMap<>();
        for (Map.Entry<String, Fluid> stringFluidEntry : stringFluidMap.entrySet()) {
            String fluidName = stringFluidEntry.getKey();
            Fluid fluid = stringFluidEntry.getValue();
            int fluidID = fluidIntegerMap.get(fluid);
            sortMap.put(fluidID, fluidName);
        }
        System.out.print("===========================================================\n");
        System.out.print("    ID\tName\n");
        for (Map.Entry<Integer, String> integerStringEntry : sortMap.entrySet()) {
            String fluidName = integerStringEntry.getValue();
            int fluidID = integerStringEntry.getKey();
            System.out.print("    " + fluidID + "\t" + fluidName + "\n");
        }
        System.out.print("===========================================================\n");
    }

    public void serverStarting(FMLServerStartingEvent event) {
        new CommandLoader(event);
    }
}
