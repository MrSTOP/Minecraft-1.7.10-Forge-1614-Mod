package com.github.mrstop.stdemo.block;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import com.github.mrstop.stdemo.tileentity.TileEntityWindmill;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


public class BlockWindmill extends BlockContainer {

    private IIcon icon0;
    private IIcon icon1;
    private IIcon icon2;
    private IIcon icon3;
    private IIcon icon4;
    private IIcon icon5;
    private IIcon icon6;
    private IIcon icon7;

    public BlockWindmill() {
        super(Material.rock);
        this.setUnlocalizedName("windmillBlock");
        this.setHardness(0.8F);
        this.setStepSound(soundTypeStone);
        this.setHarvestLevel("pickaxe", 1);
        this.setCreativeTab(CreativeTabsLoader.tabSTDemo);
        this.setBlockBounds(0.3F, 0F, 0.3F, 0.7F, 1F, 0.7F);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, int x, int y, int z) {
        switch (worldIn.getBlockMetadata(x, y, z)){
            case 4:
            case 5:
            case 6:
            case 7:
                setBlockBounds(0, 0, 0, 1, 1, 1);
                break;
            default:
                setBlockBounds(0.3F, 0F, 0.3F, 0.7F, 1F, 0.7F);
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, int x, int y, int z, EntityLivingBase placer, ItemStack itemIn) {
        super.onBlockPlacedBy(worldIn, x, y, z, placer, itemIn);
        boolean[][] blockMatrix = new boolean[5][5];
        int direction = MathHelper.floor_double((double)(placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        getBlockMatrix(worldIn, x, y - 1, z, blockMatrix);
        if (placer.getHeldItem().stackSize >= 5 &&
            hasEnoughSpace(worldIn, x, y, z, direction) &&
            worldIn.getBlockMetadata(x, y, z) == 0 &&
            judgeMultiBlockStructure(blockMatrix)){
            for (int i = 1; i <= 4; i++) {
                if(i == 4){
                    if (worldIn.setBlock(x, y + i, z , BlockLoader.windmillBlock,direction + 4, 2)){
                        placer.getHeldItem().stackSize--;
                    }
                }else {
                    if (worldIn.setBlock(x ,y + i, z, BlockLoader.windmillBlock, i, 2)){
                        placer.getHeldItem().stackSize--;
                    }
                }
            }
            updateMultiBlockStructure(worldIn, x, y - 1, z, true);
            if (itemIn.hasDisplayName()){
                if (worldIn.getTileEntity(x, y, z) != null){
                    ((TileEntityWindmill)worldIn.getTileEntity(x, y, z)).setInventoryName(itemIn.getDisplayName());
                }
            }
        }
        else {
            if (placer instanceof EntityPlayerMP){
                if (((EntityPlayer)placer).capabilities.isCreativeMode){
                    worldIn.breakBlock(x, y, z, false);
                }
                else {
                    worldIn.breakBlock(x, y, z, true);
                }
            }
            else {
                worldIn.breakBlock(x, y, z, true);
            }
        }
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
        int meta = world.getBlockMetadata(x, y, z);
        if (!player.capabilities.isCreativeMode){
            switch (meta){
                case 0:
                    destroyWindmill(world, x, y, z, true);
                    break;
                case 1:
                    destroyWindmill(world, x, y - 1, z, true);
                    break;
                case 2:
                    destroyWindmill(world, x, y - 2, z, true);
                    break;
                case 3:
                    destroyWindmill(world, x, y - 3, z, true);
                    break;
                case 4:
                case 5:
                case 6:
                case 7:
                    destroyWindmill(world, x, y - 4, z, true);
                    break;
            }
        }
        else {
            switch (meta){
                case 0:
                    destroyWindmill(world, x, y, z, false);
                    break;
                case 1:
                    destroyWindmill(world, x, y - 1, z, false);
                    break;
                case 2:
                    destroyWindmill(world, x, y - 2, z, false);
                    break;
                case 3:
                    destroyWindmill(world, x, y - 3, z, false);
                    break;
                case 4:
                case 5:
                case 6:
                case 7:
                    destroyWindmill(world, x, y - 4, z, false);
                    break;
            }
        }
        return true;
    }

    private boolean hasEnoughSpace(World world, int x, int y, int z, int direction){
        boolean hasSpace = true;
        for (int i = 1; i <= 4; i++) {
            if (!world.getBlock(x, y + i, z).isAir(world, x, y + i, z)){
                hasSpace = false;
            }
        }
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                switch (direction){
                    case 0:
                        //玩家面向南
                        //风车向北
                        if (!world.getBlock(x + i, y + j + 4, z - 1).isAir(world, 0, 0, 0)){
                            hasSpace = false;
                        }
                        break;
                        //玩家面向西
                        //风车向东
                    case 1:
                        if (!world.getBlock(x + 1, y + i + 4, z + j).isAir(world, 0, 0, 0)){
                            hasSpace = false;
                        }
                        break;
                    //玩家面向北
                    //风车向南
                    case 2:
                        if (!world.getBlock(x + i, y + j + 4, z + 1).isAir(world, 0, 0, 0)){
                            hasSpace = false;
                        }
                        break;
                    //玩家面向东
                    //风车向西
                    case 3:
                        if (!world.getBlock(x - 1, y + i + 4, z + j).isAir(world, 0, 0, 0)){
                            hasSpace = false;
                        }
                        break;
                }
            }
        }
        return hasSpace;
    }

    private void destroyWindmill(World world, int x, int startY, int z, boolean isSurvive){
        for (int i = 0; i <= 4; i++) {
            if (world.getBlock(x, startY + i, z).isAssociatedBlock(BlockLoader.windmillBlock)){
                if (world.getBlockMetadata(x, startY + i, z) == 0){
                    world.breakBlock(x, startY + i, z, false);
                    updateMultiBlockStructure(world, x, startY - 1, z, false);
                }
                else {
                    world.breakBlock(x, startY + i, z, isSurvive);
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        switch (meta){
//            case 0:
//                return icon0;
//            case 1:
//                return icon1;
//            case 2:
//                return icon2;
//            case 3:
//                return icon3;
            case 4:
                return icon4;
            case 5:
                return icon5;
            case 6:
                return icon6;
            case 7:
                return icon7;
        }
        return blockIcon;
    }

    private void updateMultiBlockStructure(World world, int x, int y, int z, boolean constructORdestory){
        boolean[][] blockMatrix = new boolean[5][5];
        int blockCount = getBlockMatrix(world, x, y, z, blockMatrix);
        if (constructORdestory){
            if (isMultiBlockStructure(world, x, y, z, blockMatrix, blockCount)){
                constructMultiBLockStructure(world, x, y, z);
            }
        }else {
            destroyMultiBlockStructure(world, x, y, z);
        }
    }

    private int getBlockMatrix(World world, int x, int y, int z, boolean[][] blockMatrix){
        int blockCount = 0;
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                if (world.getBlock(x + i, y, z + j).isAssociatedBlock(BlockLoader.windmillGroundBlock)){
                    blockMatrix[2 - i][2 + j] = true;
                    blockCount++;
                }else {
                    blockMatrix[2 - i][2 + j] = false;
                }
            }
        }
//        System.out.print("===============================");
//        System.out.println();
//        System.out.print("X:" + x + " Y:" + y + " Z: " + z + " COUNT:" + blockCount);
//        System.out.println();
//        for (int i = 0; i < 5; i++) {
//            for (int j = 0; j < 5; j++) {
//                if (i != 2 || j != 2){
//                    if (blockMatrix[i][j]){
//                        System.out.print("■ ");
//                    }
//                    if (!blockMatrix[i][j]){
//                        System.out.print("□ ");
//                    }
//                }else {
//                    if (blockMatrix[i][j]){
//                        System.out.print("● ");
//                    }
//                    if (!blockMatrix[i][j]){
//                        System.out.print("○ ");
//                    }
//                }
//            }
//            System.out.println();
//        }
//        System.out.print("===============================");
//        System.out.println();
        return blockCount;
    }

    private boolean isMultiBlockStructure(World world, int x, int y, int z, boolean[][] blockMatrix, int blockCount){
        if (blockCount < 9 || blockCount > 19){
            return false;
        }
        return judgeMultiBlockStructure(blockMatrix);
    }

    private boolean judgeMultiBlockStructure(boolean[][] blockMatrix){
        if (blockMatrix[1][1] && blockMatrix[1][2] && blockMatrix[1][3]) {
            if (blockMatrix[2][1] && blockMatrix[2][3]) {
                if (blockMatrix[3][1] && blockMatrix[3][2] && blockMatrix[3][3]) {
                    if (!(blockMatrix[0][1] || blockMatrix[0][2] || blockMatrix[0][3])) {
                        if (!(blockMatrix[1][0] || blockMatrix[2][0] || blockMatrix[3][0])) {
                            if (!(blockMatrix[1][4] || blockMatrix[2][0] || blockMatrix[3][4])) {
                                if (!(blockMatrix[4][1] || blockMatrix[4][2] || blockMatrix[4][3])) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private void constructMultiBLockStructure(World world,int x, int y, int z){
        int metadata = 1;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (world.getBlock(x + i, y, z + j).isAssociatedBlock(BlockLoader.windmillGroundBlock)){
                    world.setBlockMetadataWithNotify(x + i, y, z + j, metadata, 2);
                }
                metadata++;
            }
        }
    }

    private void destroyMultiBlockStructure(World world, int x, int y, int z){
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (world.getBlock(x + i, y, z + j).isAssociatedBlock(BlockLoader.windmillGroundBlock)){
                    world.setBlockMetadataWithNotify(x + i, y, z + j, 0, 2);
                }
            }
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX, float subY, float subZ) {
        if (worldIn.isRemote){
            return true;
        }
        else {
            TileEntityWindmill tileEntityWindmill = (TileEntityWindmill)worldIn.getTileEntity(x, y, z);
            if (tileEntityWindmill != null){
                player.openGui(STDemo.instance, STDemo.GUIIDWindmill, worldIn, x, y, z);
            }
            return true;
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta){
        if (meta >= 4){
            return new TileEntityWindmill();
        }
        return null;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType(){
        return 0;
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister blockIcon){
        this.blockIcon = blockIcon.registerIcon("stdemo:windmill_block");
        icon0 = blockIcon.registerIcon("stdemo:metadata_0");
        icon1 = blockIcon.registerIcon("stdemo:metadata_1");
        icon2 = blockIcon.registerIcon("stdemo:metadata_2");
        icon3 = blockIcon.registerIcon("stdemo:metadata_3");
        icon4 = blockIcon.registerIcon("stdemo:metadata_4");
        icon5 = blockIcon.registerIcon("stdemo:metadata_5");
        icon6 = blockIcon.registerIcon("stdemo:metadata_6");
        icon7 = blockIcon.registerIcon("stdemo:metadata_7");
    }

}
