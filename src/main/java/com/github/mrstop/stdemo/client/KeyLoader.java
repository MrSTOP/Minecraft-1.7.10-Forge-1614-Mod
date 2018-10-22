package com.github.mrstop.stdemo.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class KeyLoader {

    public static KeyBinding showTime;

    public KeyLoader()
    {
        KeyLoader.showTime = new KeyBinding("key.stdemo.showTime", Keyboard.KEY_H, "key.categories.stdemo");

        ClientRegistry.registerKeyBinding(KeyLoader.showTime);
    }
}
