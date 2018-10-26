package com.github.mrstop.stdemo.block;


import com.github.mrstop.stdemo.common.EventLoader;
import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;


public class BlockGrassBlock extends Block {

    private  IIcon icon1;
    private  IIcon icon2;
    private  IIcon icon3;
    private  IIcon icon4;
    private  IIcon icon5;
    private  IIcon icon6;

    public BlockGrassBlock() {
        super(Material.gourd);
        super.slipperiness = 2.0F;
        this.setUnlocalizedName("grassBlock");//设置国际化名称
        this.setHardness(0.5F);//硬度
        this.setStepSound(soundTypeGrass);//踩上去的声音
        this.setCreativeTab(CreativeTabsLoader.tabSTDemo);
        this.setResistance(600000.0F);
        //this.setBlockUnbreakable();
        //this.setBlockBounds(0.95F, 0.05F,0.25F, 0.75F, 0.75F,0.75F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        switch (side){
            case 0:
                return icon1;
            case 1:
                return icon2;
            case 2:
                return icon3;
            case 3:
                return icon4;
            case 4:
                return icon5;
            case 5:
                return icon6;
            default:
                return icon1;
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.icon1 = iconRegister.registerIcon("stdemo:grass_block_1");
        this.icon2 = iconRegister.registerIcon("stdemo:grass_block_2");
        this.icon3 = iconRegister.registerIcon("stdemo:grass_block_3");
        this.icon4 = iconRegister.registerIcon("stdemo:grass_block_4");
        this.icon5 = iconRegister.registerIcon("stdemo:grass_block_5");
        this.icon6 = iconRegister.registerIcon("stdemo:grass_block_6");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX, float subY, float subZ) {
        EventLoader.PlayerRightClickGrassBlockEvent event;
        event = new EventLoader.PlayerRightClickGrassBlockEvent(player, x, y, z, worldIn);
        EventLoader.EVENT_BUS.post(event);
        if (!event.isCancelable() && !worldIn.isRemote) {
            worldIn.setBlockToAir(x, y, z);
            return true;
        }
        return false;
    }
}
