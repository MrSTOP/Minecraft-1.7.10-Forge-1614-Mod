package com.github.mrstop.stdemo.tileentity;

import com.github.mrstop.stdemo.block.BlockMetalFurnace;
import com.github.mrstop.stdemo.crafting.MetalFurnaceRecipes;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.Interface(iface =  "ic2.api.energy.tile.IEnergySink", modid = "IC2")
public class TileEntityMetalFurnace extends TileEntity implements ISidedInventory, IEnergySink {

    private static final int processTime = 100;

    private static final int[] slotTop = new int[]{0};
    private static final int[] slotSide = new int[]{2, 1};
    private static final int[] slotBottom = new int[]{1};
    private ItemStack[] metalFurnaceItemStack = new ItemStack[3];
    ///////////////IC2///////////////////////
    private boolean update = false;
    private double receivedEnergyUnit = 0;
    ///////////////IC2///////////////////////
    public int metalFurnaceBurnTime;
    public int currentItemBurnTime;
    public int metalFurnaceCookTime;
    private String metalFurnaceCustomName = null;

    //物品栏个数
    @Override
    public int getSizeInventory() {
        return this.metalFurnaceItemStack.length;
    }

    //返回第i个物品栏
    @Override
    public ItemStack getStackInSlot(int slotIn) {
        return this.metalFurnaceItemStack[slotIn];
    }

    //从库存槽（第一个参数）删除指定数量（第二个参数）的项目，并将它们返回到新栈中。
    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack itemstack = null;                                                //临时ItemStack变量
        if (this.metalFurnaceItemStack[index] != null) {
            if (this.metalFurnaceItemStack[index].stackSize <= count){             //判断物品栈数量，若小于指定数量则设当前物品栈为空，并将当前物品栈返回
                itemstack = this.metalFurnaceItemStack[index];
                this.metalFurnaceItemStack[index] = null;                          //设当前物品栈为空，
            }
            else {                                                                 //否则从当前物品栈中间减去指定数量
                itemstack = this.metalFurnaceItemStack[index].splitStack(count);   //从当前物品栈中间减去指定数量
                if (this.metalFurnaceItemStack[index].stackSize == 0){              //判断当前物品栈是否为0
                    this.metalFurnaceItemStack[index] = null;                      //为0则将当前物品栈设为null
                }
            }
        }
        return itemstack;                                                          //返回临时ItemStack
    }

    //当一些容器被关闭时，在每个槽上调用，抛出返回的EntityItem，就像关闭工作台GUI一样。
    @Override
    public ItemStack getStackInSlotOnClosing(int index) {
//        if (this.metalFurnaceItemStack[index] != null) {
//            ItemStack itemstack = this.metalFurnaceItemStack[index];
//            this.metalFurnaceItemStack[index] = null;
//            return itemstack;
//        } else {
//            return null;
//        }
        return null;
    }

    //将给定物品栈设置为清单中的指定插槽（可以是合成或装甲部分）。
    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.metalFurnaceItemStack[index] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }
    }

    //是否有名称
    @Override
    public boolean isCustomInventoryName() {
        return this.metalFurnaceCustomName != null && this.metalFurnaceCustomName.length() > 0;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        NBTTagList nbtTagList = compound.getTagList("Items", 10);
        this.metalFurnaceItemStack = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbtTagList.tagCount(); ++i) {
            NBTTagCompound nbtTagCompound = nbtTagList.getCompoundTagAt(i);
            byte byte0 = nbtTagCompound.getByte("Slot");

            if (byte0 >= 0 && byte0 < this.metalFurnaceItemStack.length) {
                this.metalFurnaceItemStack[byte0] = ItemStack.loadItemStackFromNBT(nbtTagCompound);                           //传入传入的NBTCompound
            }
        }

        this.receivedEnergyUnit = compound.getDouble("ReceivedEnergy");
        this.metalFurnaceBurnTime = compound.getShort("BurnTime");
        this.metalFurnaceCookTime = compound.getShort("CookTime");
        this.currentItemBurnTime = getItemBurnTime(this.metalFurnaceItemStack[1]);

        if (compound.hasKey("CustomName", 8)) {
            this.metalFurnaceCustomName = compound.getString("CustomName");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setDouble("ReceivedEnergy", this.receivedEnergyUnit);
        compound.setShort("BurnTime", (short) this.metalFurnaceBurnTime);
        compound.setShort("CookTime", (short) this.metalFurnaceCookTime);
        NBTTagList nbtTagList= new NBTTagList();

        for (int i = 0; i < this.metalFurnaceItemStack.length; ++i)
        {
            if (this.metalFurnaceItemStack[i] != null)
            {
                NBTTagCompound nbtTagCompound = new NBTTagCompound();
                nbtTagCompound.setByte("Slot", (byte) i);
                this.metalFurnaceItemStack[i].writeToNBT(nbtTagCompound);
                nbtTagList.appendTag(nbtTagCompound);
            }
        }

        compound.setTag("Items", nbtTagList);
        if (this.isCustomInventoryName())
        {
            compound.setString("CustomName", this.metalFurnaceCustomName);
        }
    }

    //Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.Isn't this more of a set than a get?
    //返回清单插槽的最大堆栈大小。 似乎永远是64，可能会延长。 这不仅仅是一个集合吗？
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    //返回一个介于0和传递的值之间的整数值，表示当前物品被完全加工的距离
    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int time)
    {
        return this.metalFurnaceCookTime * time / processTime;
    }

    //返回一个介于0和传递的值之间的整数值，表示当前燃料物品剩余燃烧时间，其中0表示该物品已耗尽，并且传递的值表示该项目为新的
    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int time)
    {
        if (this.currentItemBurnTime == 0)
        {
            this.currentItemBurnTime = 200;
        }
        return this.metalFurnaceBurnTime * time / this.currentItemBurnTime;
    }

    //熔炉是否在燃烧
    public boolean isBurning()
    {
        return this.metalFurnaceBurnTime > 0;
    }

    @Override
    public void updateEntity()
    {
        boolean flag = this.metalFurnaceBurnTime > 0;                                                                                           //判断燃烧时间是否大于0
        boolean isDirty = false;
        if (!this.worldObj.isRemote)                                                                                                            //判断是否为服务器端
        {

            if (!this.update && Loader.isModLoaded("IC2"))
            {
                this.onIC2MachineLoaded();
                this.update = true;
            }
            if (this.metalFurnaceBurnTime > 0)                                                                                                      //判断燃烧时间是否大于0
            {
                --this.metalFurnaceBurnTime;                                                                                                        //减少燃烧时间
            }
            if (this.metalFurnaceBurnTime != 0 || this.metalFurnaceItemStack[1] != null && this.metalFurnaceItemStack[0] != null)               //燃烧时间不为0或燃料槽不为空且原料槽不为空
            {
                if (this.metalFurnaceBurnTime == 0 && this.canSmelt())                                                                          //燃烧时间为空且能熔炼
                {
                    this.currentItemBurnTime = this.metalFurnaceBurnTime = getItemBurnTime(this.metalFurnaceItemStack[1]);                      //修改currentItemBurnTime以及metalFurnaceBurnTime为燃料槽物品燃烧时间

                    if (this.metalFurnaceBurnTime > 0)                                                                                          //判断燃烧时间是否大于0
                    {
                        isDirty = true;                                                                                                           //将TileEntity标记为脏

                        if (this.metalFurnaceItemStack[1] != null)                                                                              //判断燃料槽是否为空
                        {
                            --this.metalFurnaceItemStack[1].stackSize;                                                                          //减少燃料槽物品栈数量

                            if (this.metalFurnaceItemStack[1].stackSize == 0)                                                                   //判断燃料槽物品的物品栈是否为0
                            {
                                this.metalFurnaceItemStack[1] = metalFurnaceItemStack[1].getItem().getContainerItem(metalFurnaceItemStack[1]);  //返回容器物品(如岩浆桶燃烧后返回空桶)
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt())                                                                                        //判断是否正在燃烧且是否能燃烧
                {
                    ++this.metalFurnaceCookTime;                                                                                                //增加metalFurnaceCookTime

                    if (this.metalFurnaceCookTime == processTime)                                                                                       //判断metalFurnaceCookTime是否等于200
                    {
                        this.metalFurnaceCookTime = 0;                                                                                          //将metalFurnaceCookTime设为0
                        this.smeltItem();                                                                                                       //熔炼物品
                        isDirty = true;                                                                                                           //将TileEntity标记为脏
                    }
                }
                else
                {
                    this.metalFurnaceCookTime = 0;                                                                                              //将metalFurnaceCookTime设为0
                }
            }

            if (this.metalFurnaceItemStack[1] == null && this.metalFurnaceItemStack[0] != null)               //燃烧时间不为0或燃料槽不为空且原料槽不为空
            {
                double requireEnergyPerTick = this.getRequiredEnergyPerTick();
                if (this.receivedEnergyUnit >= requireEnergyPerTick)
                {
                    ++this.metalFurnaceCookTime;
                    this.receivedEnergyUnit -= requireEnergyPerTick;
                    if (++this.metalFurnaceCookTime >= processTime)
                    {
                        this.metalFurnaceBurnTime = 0;
                        this.smeltItem();
                        this.markDirty();
                        this.metalFurnaceBurnTime = 1;
                    }
                }
            }

            if (flag != this.metalFurnaceBurnTime > 0)
            {
                isDirty = true;
                BlockMetalFurnace.updateMentalFurnaceBlockState(this.metalFurnaceBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }
        if (isDirty)
        {
            this.markDirty();//对于TileEntity，确保包含TileEntity的方块在稍后保存到磁盘 - 游戏不会认为它没有更改并跳过它。
        }
    }

    //如果熔炉能够熔炼物品，则返回true，即具有原料物品，产物槽物品栈未满等。
    private boolean canSmelt()
    {
        if (this.metalFurnaceItemStack[0] == null)                                                                      //原料槽为空
        {
            return false;                                                                                               //返回false
        }
        else
        {
            ItemStack itemstack = MetalFurnaceRecipes.metalFurnaceSmelting().getSmeltingResult(this.metalFurnaceItemStack[0]);           //临时物品栈
            if (itemstack == null)                                                                                      //临时物品栈为空时返回false
                return false;
            if (this.metalFurnaceItemStack[2] == null)                                                                  //产物槽为空返回true
                return true;
            if (!this.metalFurnaceItemStack[2].isItemEqual(itemstack))                                                  //将ItemStack参数与实例ItemStack进行比较; 如果两个ItemStacks中的Items都相等，则返回true
                return false;                                                                                           //产物槽不是原料的产物时返回false
            int result = metalFurnaceItemStack[2].stackSize + itemstack.stackSize;
            return result <= getInventoryStackLimit() && result <= this.metalFurnaceItemStack[2].getMaxStackSize();     //烧练结束时结果小于产物槽最大物品容量且小于产物物品栈最大物品数量
            //Forge BugFix: Make it respect stack sizes properly.Forge BugFix：正确地尊重堆栈大小。
        }
    }

    //将炉子原料槽原料物品栈中的一个物品放入炉子产物槽中相应的熔炼物品的物品栈中
    public void smeltItem()
    {
        if (this.canSmelt())                                                                                            //判断是否能烧练
        {
            ItemStack itemstack = MetalFurnaceRecipes.metalFurnaceSmelting().getSmeltingResult(this.metalFurnaceItemStack[0]);           //临时产物物品栈

            if (this.metalFurnaceItemStack[2] == null)                                                                  //判断产物物品栈是否为空
            {
                this.metalFurnaceItemStack[2] = itemstack.copy();                                                       //将获得的烧练合成表产物设为烧练产物
            }
            else if (this.metalFurnaceItemStack[2].getItem() == itemstack.getItem())                                    //判断产物物品栈是否为烧练合成表产物
            {
                this.metalFurnaceItemStack[2].stackSize += itemstack.stackSize;                                         //增加产物物品栈数量
                // Forge BugFix: Results may have multiple items Forge BugFix:结果可能有多个物品
            }

            --this.metalFurnaceItemStack[0].stackSize;                                                                  //减少原料物品栈数量

            if (this.metalFurnaceItemStack[0].stackSize <= 0)                                                           //判断原料物品栈是否小于等于0
            {
                this.metalFurnaceItemStack[0] = null;                                                                   //将原料物品栈设为null
            }
        }
    }

    //返回提供的燃料项目保持炉膛燃烧的Tick，如果该项目不是燃料，则返回0
    public static int getItemBurnTime(ItemStack itemStack)
    {
        if (itemStack == null)                                                                                          //判断物品栈是否为空
        {
            return 0;                                                                                                   //返回0
        }
        else
        {
            int moddedBurnTime = net.minecraftforge.event.ForgeEventFactory.getFuelBurnTime(itemStack);                 //获取mod添加燃料的燃烧时间
            if (moddedBurnTime >= 0)                                                                                    //判断燃烧时间是否大于等于0
                return moddedBurnTime;                                                                                  //返回燃烧时间

            Item item = itemStack.getItem();                                                                            //临时物品

            if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air)                                //判断临时物品是否为ItemBlock的实例且临时物品对应方块是否不是空气
            {
                Block block = Block.getBlockFromItem(item);                                                             //临时方块

                if (block == Blocks.wooden_slab)                                                                        //如果方块是木台阶
                {
                    return 150;                                                                                         //返回150
                }
                if (block.getMaterial() == Material.wood)                                                               //如果方块材质是木制的
                {
                    return 300;                                                                                         //返回300
                }
                if (block == Blocks.coal_block)                                                                         //如果是煤炭块
                {
                    return 16000;                                                                                       //返回16000
                }
            }
            if (item instanceof ItemTool && ((ItemTool)item).getToolMaterialName().equals("WOOD"))                      //如果物品是工具且材质为木质
                return 200;                                                                                             //返回200
            if (item instanceof ItemSword && ((ItemSword)item).getToolMaterialName().equals("WOOD"))                    //如果物品是剑且材质为木质
                return 200;                                                                                             //返回200
            if (item instanceof ItemHoe && ((ItemHoe)item).getMaterialName().equals("WOOD"))                            //如果物品是锄且材质为木质
                return 200;                                                                                             //返回200
            if (item == Items.stick)                                                                                    //如果物品是木棍
                return 100;                                                                                             //返回100
            if (item == Items.coal)                                                                                     //如果物品是煤炭
                return 1600;                                                                                            //返回1600
            if (item == Items.lava_bucket)                                                                              //如果物品是岩浆桶
                return 20000;                                                                                           //返回20000
            if (item == Item.getItemFromBlock(Blocks.sapling))                                                          //如果物品是树苗
                return 100;                                                                                             //返回100
            if (item == Items.blaze_rod)                                                                                //如果物品是烈焰棒
                return 2400;                                                                                            //返回2400
            return GameRegistry.getFuelValue(itemStack);                                                                //返回燃料注册的燃料值，getFuelValue()找不到则返回0
        }
    }

    //判断物品是否为燃料，是返回true，否返回false
    public static boolean isItemFuel(ItemStack itemStack)
    {
        //返回提供的燃料物品保持炉膛燃烧的Tick，如果该项目不是燃料，则返回0
        return getItemBurnTime(itemStack) > 0;
    }

    //不要将该方法命名为canInteractWith，因为它与Container冲突
    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        if (this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D){
            return true;
        }
        else {
            return false;
        }
        //如果worldObj的TileEntity不是这个TileEntity则返回false，如果worldObj的TileEntity是这个TileEntity则返回(玩家距离该TileEntity的直线距离小于等于64.0D则返回true，否则返回false)
    }

    @Override
    public void openChest() {}

    @Override
    public void closeChest() {}

    //如果允许自动化将给定堆栈（忽略堆栈大小）插入给定插槽，则返回true。
    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return index == 2 ? false : (index == 1 ? isItemFuel(stack) : true);
        //如果为产物槽返回false如果不是产物槽返回(如果是燃料槽返回(判断是否为燃料是返回true，否则返回false)如果不是返回true（即判断结果为原料槽）)
    }

    //param side 参数方面
    @Override
    public int[] getSlotsForFace(int slotsSides)
    {
        return slotsSides == 0 ? slotBottom : (slotsSides == 1 ? slotTop : slotSide);
        //slotsSides等于0返回slotBottom为1返回slotTop为其他返回slotSide
    }

    //如果自动化可以将给定物品插入给定侧的给定槽中，则返回true。 参数：槽，物品，面
    @Override
    public boolean canInsertItem(int slots, ItemStack itemStack, int sides)
    {
        return this.isItemValidForSlot(slots, itemStack);
    }

    //如果自动化可以从给定面提取给定槽中的给定物品，则返回true。 参数：槽，物品，面
    @Override
    public boolean canExtractItem(int slots, ItemStack itemStack, int sides)
    {
        return sides != 0 || slots != 1 || itemStack.getItem() == Items.bucket;
        //如果不是下面或物品槽不是原料槽或物品是桶返回true
    }

    public void setCustomInventoryName(String customInventoryName)
    {
        this.metalFurnaceCustomName = customInventoryName;
    }

    //物品栏名称
    @Override
    public String getInventoryName()
    {
        return this.isCustomInventoryName() ? this.metalFurnaceCustomName : "container.furnace";
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////IndustrialCraft///////////////////////////////////////////

    public double getRequiredEnergyPerTick()
    {
        return 4.5;
    }

    public double getEnergyCapacity()
    {
        return 4096;
    }

    @Override
    public double getDemandedEnergy()
    {
        return Math.max(0, this.getEnergyCapacity() - this.receivedEnergyUnit);
    }

    @Override
    public int getSinkTier()
    {
        return 2;
    }

    @Override
    public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage)
    {
        this.receivedEnergyUnit += amount;
        return 0;
    }

    @Override
    @Optional.Method(modid = "IC2")
    public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction)
    {
        return true;
    }

    @Override
    public void invalidate()
    {
        super.invalidate();
        if (!this.worldObj.isRemote && Loader.isModLoaded("IC2"))
        {
            this.onIC2MachineUnloaded();
        }
    }

    @Optional.Method(modid = "IC2")
    private void onIC2MachineLoaded()
    {
        MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
    }

    @Optional.Method(modid = "IC2")
    private void onIC2MachineUnloaded()
    {
        MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////IndustrialCraft///////////////////////////////////////////
}
