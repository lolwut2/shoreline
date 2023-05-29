package com.caspian.client.api.handler;

import com.caspian.client.api.event.listener.EventListener;
import com.caspian.client.api.macro.Macro;
import com.caspian.client.api.module.ToggleModule;
import com.caspian.client.api.module.Module;
import com.caspian.client.impl.event.keyboard.KeyboardInputEvent;
import com.caspian.client.init.Managers;
import org.lwjgl.glfw.GLFW;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class MacroHandler
{
    /**
     *
     *
     * @param event
     */
    @EventListener
    public void onKeyboardInput(KeyboardInputEvent event)
    {
        // module keybinding impl
        for (Module mod : Managers.MODULE.getModules())
        {
            if (mod instanceof ToggleModule)
            {
                Macro keybinding = ((ToggleModule) mod).getKeybinding();
                if (event.getKeycode() != GLFW.GLFW_KEY_UNKNOWN
                        && event.getKeycode() == keybinding.keycode())
                {
                    keybinding.runMacro();
                }
            }
        }

        for (Macro macro : Managers.MACRO.getMacros())
        {
            if (event.getKeycode() != GLFW.GLFW_KEY_UNKNOWN
                    && event.getKeycode() == macro.keycode())
            {
                macro.runMacro();
            }
        }
    }
}