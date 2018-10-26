package com.github.mrstop.stdemo.block;

import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import com.github.mrstop.stdemo.tileentity.TileEntityElectrolyticMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockElectrolyticMachine extends BlockContainer {
    @SideOnly(Side.CLIENT)
    private IIcon front;
    @SideOnly(Side.CLIENT)
    private IIcon left;
    @SideOnly(Side.CLIENT)
    private IIcon right;

    public BlockElectrolyticMachine() {
        super(Material.rock);
        this.setUnlocalizedName("electrolyticMachine");
        this.setHardness(0.5F);
        this.setStepSound(soundTypeStone);
        this.setHarvestLevel("pickaxe", 1);
        this.setCreativeTab(CreativeTabsLoader.tabSTDemo);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityElectrolyticMachine();
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
            ((TileEntityElectrolyticMachine) worldIn.getTileEntity(x, y, z)).setCustomInventoryName(itemIn.getDisplayName());
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
                        //�������泯��
                        if (side == 5){
                            return this.left;
                        }
                        if (side == 4){
                            return this.right;
                        }
                    }
                    else if (meta == 3){
                        //�������泯��
                        if (side == 4){
                            return this.left;
                        }
                        if (side == 5){
                            return this.right;
                        }
                    }
                    else if (meta == 4){
                        //�������泯��
                        if (side == 2){
                            return this.left;
                        }
                        if (side == 3){
                            return this.right;
                        }
                    }
                    else if (meta == 5){
                        //�������泯��
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
            }
            return this.blockIcon;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon("stdemo:electrolytic_machine");
        this.front = iconRegister.registerIcon("stdemo:electrolytic_machine_front");
        this.left = iconRegister.registerIcon("stdemo:electrolytic_machine_left");
        this.right = iconRegister.registerIcon("stdemo:electrolytic_machine_right");
    }
}
