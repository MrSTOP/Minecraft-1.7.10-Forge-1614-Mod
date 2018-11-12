package com.github.mrstop.stdemo.block;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.core.util.InventoryHelper;
import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import com.github.mrstop.stdemo.tileentity.TileEntityMachineCalciner;
import com.github.mrstop.stdemo.tileentity.TileEntityMachineElectrolyticMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
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

public class BlockMachineElectrolyticMachine extends BlockContainer {
    @SideOnly(Side.CLIENT)
    private IIcon front;
    @SideOnly(Side.CLIENT)
    private IIcon left;
    @SideOnly(Side.CLIENT)
    private IIcon right;

    public BlockMachineElectrolyticMachine() {
        super(Material.rock);
        this.setUnlocalizedName("machineElectrolyticMachine");
        this.setHardness(0.5F);
        this.setStepSound(soundTypeStone);
        this.setHarvestLevel("pickaxe", 1);
        this.setCreativeTab(CreativeTabsLoader.tabSTDemo);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityMachineElectrolyticMachine();
    }

    @Override
    public void breakBlock(World worldIn, int x, int y, int z, Block blockBroken, int meta) {
        TileEntityMachineElectrolyticMachine tileEntityMachineElectrolyticMachine = (TileEntityMachineElectrolyticMachine) worldIn.getTileEntity(x, y, z);
        if (tileEntityMachineElectrolyticMachine == null){
            super.breakBlock(worldIn, x, y, z, blockBroken, meta);
            return;
        }
        InventoryHelper.dropInventoryItems(worldIn, tileEntityMachineElectrolyticMachine, x, y, z);
        super.breakBlock(worldIn, x, y, z, blockBroken, meta);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack itemIn) {
        int sideFlag = MathHelper.floor_double((double)(entityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
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
            TileEntityMachineElectrolyticMachine tileEntityMachineElectrolyticMachine = (TileEntityMachineElectrolyticMachine) worldIn.getTileEntity(x, y, z);
            if (tileEntityMachineElectrolyticMachine != null){
                player.openGui(STDemo.instance, STDemo.GUIDMachineElectrolyticMachine, worldIn, x, y, z);
            }
            return true;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (side == 0 || side == 1){
            return this.blockIcon;
        }
        else {
            if (meta != 0){
                if (side == meta){
                    return this.front;
                }
                else {
                    if (meta == 2){
                        //方块正面朝北
                        if (side == 5){
                            return this.left;
                        }
                        if (side == 4){
                            return this.right;
                        }
                    }
                    else if (meta == 3){
                        //方块正面朝南
                        if (side == 4){
                            return this.left;
                        }
                        if (side == 5){
                            return this.right;
                        }
                    }
                    else if (meta == 4){
                        //方块正面朝西
                        if (side == 2){
                            return this.left;
                        }
                        if (side == 3){
                            return this.right;
                        }
                    }
                    else if (meta == 5){
                        //方块正面朝东
                        if (side == 3){
                            return this.left;
                        }
                        if (side == 2){
                            return this.right;
                        }

                    }
                }
            }
            else {
                if (side == 3){
                    return this.front;
                }
                if (side == 4){
                    return this.left;
                }
            }
            return this.blockIcon;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon("stdemo:machine_electrolytic_machine");
        this.front = iconRegister.registerIcon("stdemo:machine_electrolytic_machine_front");
        this.left = iconRegister.registerIcon("stdemo:machine_electrolytic_machine_left");
        this.right = iconRegister.registerIcon("stdemo:machine_electrolytic_machine_right");
    }
}
