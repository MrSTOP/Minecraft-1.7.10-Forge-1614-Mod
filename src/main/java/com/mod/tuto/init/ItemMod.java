package com.mod.tuto.init;

import com.mod.tuto.ModTuto;
import com.mod.tuto.Reference;
import com.mod.tuto.items.ItemArmorTuto;
import com.mod.tuto.items.ItemFoodMod;
import com.mod.tuto.items.ItemMultiToolMod;
import com.mod.tuto.items.ItemPickaxeTuto;
import com.mod.tuto.items.ItemSeedsMod;
import com.mod.tuto.items.ItemSwordTuto;
import com.mod.tuto.tems.ItemMods;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class ItemMod
{
    public static ToolMaterial toolTuto = EnumHelper.addToolMaterial("toolTuto", 3, 1000, 10, 100, 30);
    public static ArmorMaterial armorTuto = EnumHelper.addArmorMaterial("armorTuto", 40, new int[]{8, 8, 8, 8}, 50);
    
    public static Item item_test, item_sword, item_pickaxe, casqueArmor, plastronArmor, pantalonArmor, bootArmor, multiToolMod, itemFoodTuto, seedTuto;
    
    public static void init()
    {
        item_test = new ItemMods().setUnlocalizedName("item_test").setCreativeTab(CreativeTabs.tabDecorations).setTextureName(Reference.MOD_ID + ":item_test");
        item_sword = new ItemSwordTuto(ItemMod.toolTuto).setCreativeTab(CreativeTabs.tabTools).setTextureName(Reference.MOD_ID + ":item_sword").setUnlocalizedName("item_sword");
        item_pickaxe = new ItemPickaxeTuto(ItemMod.toolTuto).setCreativeTab(CreativeTabs.tabTools).setTextureName(Reference.MOD_ID + ":item_pickaxe").setUnlocalizedName("item_pickaxe");
        casqueArmor = new ItemArmorTuto(armorTuto, 0).setCreativeTab(CreativeTabs.tabCombat).setTextureName(Reference.MOD_ID + ":casqueArmor").setUnlocalizedName("casqueArmor");
        plastronArmor = new ItemArmorTuto(armorTuto, 1).setCreativeTab(CreativeTabs.tabCombat).setTextureName(Reference.MOD_ID + ":plastronArmor").setUnlocalizedName("plastronArmor");
        pantalonArmor = new ItemArmorTuto(armorTuto, 2).setCreativeTab(CreativeTabs.tabCombat).setTextureName(Reference.MOD_ID + ":pantalonArmor").setUnlocalizedName("pantalonArmor");
        bootArmor = new ItemArmorTuto(armorTuto, 3).setCreativeTab(CreativeTabs.tabCombat).setTextureName(Reference.MOD_ID + ":bootArmor").setUnlocalizedName("bootArmor");
        multiToolMod = new ItemMultiToolMod(toolTuto).setCreativeTab(CreativeTabs.tabTools).setTextureName(Reference.MOD_ID + ":item_test").setUnlocalizedName("multiToolMod");
        itemFoodTuto = new ItemFoodMod(3, 0.0F, false).setAlwaysEdible().setCreativeTab(CreativeTabs.tabFood).setTextureName(Reference.MOD_ID + ":itemFoodTuto").setUnlocalizedName("itemFoodTuto");
        seedTuto = new ItemSeedsMod(BlockMod.cultureTest, Blocks.farmland).setCreativeTab(ModTuto.tabTuto).setUnlocalizedName("seedTuto").setTextureName(Reference.MOD_ID + ":seedTuto");
        
    }
    
    public static void register()
    {
        GameRegistry.registerItem(item_test, "item_test");
        GameRegistry.registerItem(item_sword, "item_sword");
        GameRegistry.registerItem(item_pickaxe, "item_pickaxe");
        GameRegistry.registerItem(casqueArmor, "casqueArmor");
        GameRegistry.registerItem(plastronArmor, "plastronArmor");
        GameRegistry.registerItem(pantalonArmor, "pantalonArmor");
        GameRegistry.registerItem(bootArmor, "bootArmor");
        GameRegistry.registerItem(multiToolMod, "multiToolMod");
        GameRegistry.registerItem(itemFoodTuto, "itemFoodTuto");
        GameRegistry.registerItem(seedTuto, "seedTuto");
    }
}