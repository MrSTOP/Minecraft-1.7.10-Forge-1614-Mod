package com.github.mrstop.stdemo.block;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import com.github.mrstop.stdemo.tileentity.TileEntityMachineCalciner;
import com.github.mrstop.stdemo.tileentity.TileEntityMachineElectrolyticMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockMachineCalciner extends BlockContainer {
    @SideOnly(Side.CLIENT)
    private IIcon front;

    public BlockMachineCalciner() {
        super(Material.iron);
        this.setCreativeTab(CreativeTabsLoader.tabSTDemo);
        this.setUnlocalizedName("machineCalciner");
        this.setHarvestLevel("pickaxe", 1);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, int x, int y, int z, EntityLivingBase placer, ItemStack itemIn) {
        int sideFlag = MathHelper.floor_double((double)(placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        if (sideFlag == 0) {
            worldIn.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }
        if (sideFlag == 1) {
            worldIn.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }
        if (sideFlag == 2) {
            worldIn.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }
        if (sideFlag == 3) {
            worldIn.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }
        if (itemIn.hasDisplayName()) {
            ((TileEntityMachineElectrolyticMachine) worldIn.getTileEntity(x, y, z)).setCustomInventoryName(itemIn.getDisplayName());
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX, float subY, float subZ) {
        if (worldIn.isRemote){
            return true;
        }
        else {
            TileEntityMachineCalciner tileEntityMachineCalciner = (TileEntityMachineCalciner) worldIn.getTileEntity(x, y, z);
            if (tileEntityMachineCalciner != null){
                player.openGui(STDemo.instance, STDemo.GUIIDMachineCalciner, worldIn, x, y, z);
            }
            return true;
        }
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityMachineCalciner();
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (meta != 0){
            if (meta == side){
                return this.front;
            }
        }
        else {
            if (side == 3){
                return this.front;
            }
        }
        return this.blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon("stdemo:machine_calciner");
        this.front = iconRegister.registerIcon("stdemo:machine_calciner_front");
    }
}
