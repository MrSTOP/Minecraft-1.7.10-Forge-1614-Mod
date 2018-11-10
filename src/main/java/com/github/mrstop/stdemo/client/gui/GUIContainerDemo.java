package com.github.mrstop.stdemo.client.gui;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.inventory.ContainerDemo;
import com.github.mrstop.stdemo.item.ItemLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GUIContainerDemo extends GuiContainer {

    private static final String TEXTURE_PATH = STDemo.MOD_ID + ":textures/gui/container/gui_demo.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(TEXTURE_PATH);
    private static final int BUTTON_UP = 0;
    private static final int BUTTON_DOWN = 1;
    private Slot ironSlot;

    public GUIContainerDemo(ContainerDemo inventorySlotsIn) {
        super(inventorySlotsIn);
        this.xSize = 176;
        this.ySize = 133;
        this.ironSlot = inventorySlotsIn.getIronSlot();
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(TEXTURE);
        int offsetX = (this.width - this.xSize) / 2;
        int offsetY = (this.height - this.ySize) / 2;

        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
        //调用drawTexturedModalRect方法绘制特定材质
        // 这个方法的六个参数:
        //前两个参数表示绘制的材质在窗口的左上角的坐标，又称xy值，使用了一种技巧使其位于屏幕正中间
        //中间两个参数表示绘制在材质在材质图的左上角的坐标，又称uv值，这里自然都是零
        //最后两个参数表示绘制的矩形的长宽尺寸
    }

    //drawGuiContainerForegroundLayer方法的坐标原点位于GUI界面的左上角，而不是窗口的左上角。
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mousY) {
        this.drawVerticalLine(30, 19, 36, 0xFF000000);
        this.drawHorizontalLine(8, 167, 43, 0x60FF0000);

        String title = I18n.format("container.stdemo.demo");
        this.fontRendererObj.drawString(title, (this.xSize - this.fontRendererObj.getStringWidth(title)) / 2, 6, 0x404040);

        ItemStack itemStack = new ItemStack(ItemLoader.bestSword);
        this.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), itemStack, 8, 20);

    }

    @Override
    public void initGui() {
        super.initGui();
        int offsetX = (this.width - this.xSize) / 2;
        int offsetY = (this.height - this.ySize) / 2;
        this.buttonList.add(new GuiButton(BUTTON_UP, offsetX + 155, offsetY + 13, 15, 10, "")
        {
            @Override
            public void drawButton(Minecraft mc, int mouseX, int mouseY) {
                if (this.visible) {
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

                    mc.getTextureManager().bindTexture(TEXTURE);
                    int x = mouseX - this.xPosition, y = mouseY - this.yPosition;

                    if (x >= 0 && y >= 0 && x < this.width && y < this.height) {
                        this.drawTexturedModalRect(this.xPosition, this.yPosition, 1, 146, this.width, this.height);
                    }
                    else {
                        this.drawTexturedModalRect(this.xPosition, this.yPosition, 1, 134, this.width, this.height);
                    }
                }
            }
        });
        this.buttonList.add(new GuiButton(BUTTON_DOWN, offsetX + 155, offsetY + 31, 15, 10, "")
        {
            @Override
            public void drawButton(Minecraft mc, int mouseX, int mouseY) {
                if (this.visible) {
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

                    mc.getTextureManager().bindTexture(TEXTURE);
                    int x = mouseX - this.xPosition, y = mouseY - this.yPosition;

                    if (x >= 0 && y >= 0 && x < this.width && y < this.height) {
                        this.drawTexturedModalRect(this.xPosition, this.yPosition, 20, 146, this.width, this.height);
                    }
                    else {
                        this.drawTexturedModalRect(this.xPosition, this.yPosition, 20, 134, this.width, this.height);
                    }
                }
            }
        });
    }
    @Override
    protected void actionPerformed(GuiButton guiButton) {
        ItemStack itemStack = this.ironSlot.getStack();
        int amount = itemStack == null ? 0 : itemStack.stackSize;
        switch (guiButton.id) {
            case BUTTON_UP:
                amount = (amount + 1) % 65;
                break;
            case BUTTON_DOWN:
                amount = (amount + 64) % 65;
                break;
            default:
                super.actionPerformed(guiButton);
                return;
        }
        this.ironSlot.putStack(amount == 0 ? null : new ItemStack(Items.iron_ingot, amount));
    }
}
