package com.github.mrstop.stdemo.client.gui;

import net.minecraft.client.gui.GuiButton;

public class CustomGUIButton extends GuiButton {

    public CustomGUIButton(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
    }

    public CustomGUIButton(int ID, int xPosition, int yPosition, int width, int height, String displayString) {
        super(ID, xPosition, yPosition, width, height, displayString);
    }
}
