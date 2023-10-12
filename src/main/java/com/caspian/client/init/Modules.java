package com.caspian.client.init;

import com.caspian.client.Caspian;
import com.caspian.client.api.module.Module;
import com.caspian.client.api.manager.ModuleManager;
import com.caspian.client.impl.module.client.ClickGuiModule;
import com.caspian.client.impl.module.client.ColorsModule;
import com.caspian.client.impl.module.client.HudModule;
import com.caspian.client.impl.module.client.RotationsModule;
import com.caspian.client.impl.module.combat.*;
import com.caspian.client.impl.module.exploit.FakeLatencyModule;
import com.caspian.client.impl.module.exploit.ReachModule;
import com.caspian.client.impl.module.exploit.SwingModule;
import com.caspian.client.impl.module.misc.*;
import com.caspian.client.impl.module.movement.*;
import com.caspian.client.impl.module.exploit.AntiHungerModule;
import com.caspian.client.impl.module.render.*;
import com.caspian.client.impl.module.world.AvoidModule;
import com.caspian.client.impl.module.world.FastPlaceModule;
import com.caspian.client.impl.module.world.SpeedmineModule;

import java.util.HashSet;
import java.util.Set;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 * @see Module
 * @see ModuleManager
 */
public class Modules
{
    // The initialized state of the modules. Once this is true, all modules
    // have been initialized and the init process is complete. As a general
    // rule, it is good practice to check this state before accessing instances.
    private static boolean initialized;
    // The module initialization cache. This prevents modules from being
    // initialized more than once.
    private static Set<Module> CACHE;
    // Module instances.
    public static ClickGuiModule CLICK_GUI;
    public static ColorsModule COLORS;
    public static HudModule HUD;
    public static RotationsModule ROTATIONS;
    // Combat
    public static AuraModule AURA;
    public static AutoArmorModule AUTO_ARMOR;
    public static AutoBowReleaseModule AUTO_BOW_RELEASE;
    public static AutoCrystalModule AUTO_CRYSTAL;
    public static AutoTotemModule AUTO_TOTEM;
    public static CriticalsModule CRITICALS;
    // Exploit
    public static AntiHungerModule ANTI_HUNGER;
    public static FakeLatencyModule FAKE_LATENCY;
    public static ReachModule REACH;
    public static SwingModule SWING;
    // Misc
    public static AutoRespawnModule AUTO_RESPAWN;
    public static FakePlayerModule FAKE_PLAYER;
    public static MiddleClickModule MIDDLE_CLICK;
    public static TimerModule TIMER;
    public static XCarryModule XCARRY;
    // Movement
    public static ElytraFlyModule ELYTRA_FLY;
    public static EntityControlModule ENTITY_CONTROL;
    public static FastFallModule FAST_FALL;
    public static FlightModule FLIGHT;
    public static LongJumpModule LONG_JUMP;
    public static NoFallModule NO_FALL;
    public static NoSlowModule NO_SLOW;
    public static SpeedModule SPEED;
    public static SprintModule SPRINT;
    public static TickShiftModule TICK_SHIFT;
    public static VelocityModule VELOCITY;
    public static YawModule YAW;
    // Render
    public static BlockHighlightModule BLOCK_HIGHLIGHT;
    public static ESPModule ESP;
    public static FullbrightModule FULLBRIGHT;
    public static HoleESPModule HOLE_ESP;
    public static NametagsModule NAMETAGS;
    public static NoRenderModule NO_RENDER;
    public static NoRotateModule NO_ROTATE;
    public static NoWeatherModule NO_WEATHER;
    public static ViewClipModule VIEW_CLIP;
    // public static ViewModelModule VIEW_MODEL;
    // World
    public static AvoidModule AVOID;
    public static FastPlaceModule FAST_PLACE;
    public static SpeedmineModule SPEEDMINE;

    /**
     * Returns the registered {@link Module} with the param name in the
     * {@link ModuleManager}. The same module
     * cannot be retrieved more than once using this method.
     *
     * @param id The module name
     * @return The retrieved module
     * @throws IllegalStateException If the module was not registered
     *
     * @see ModuleManager
     */
    private static Module getRegisteredModule(final String id)
    {
        Module registered = Managers.MODULE.getModule(id);
        if (CACHE.add(registered))
        {
            return registered;
        }
        // already cached!!
        else
        {
            throw new IllegalStateException("Invalid module requested: " + id);
        }
    }

    /**
     * Initializes the modules instances. Should not be used if the
     * modules are already initialized. Cannot function unless the
     * {@link ModuleManager} is initialized.
     *
     * @see #getRegisteredModule(String)
     * @see Managers#isInitialized()
     */
    public static void init()
    {
        if (Managers.isInitialized())
        {
            CACHE = new HashSet<>();
            CLICK_GUI = (ClickGuiModule) getRegisteredModule(
                    "clickgui-module");
            COLORS = (ColorsModule) getRegisteredModule("colors-module");
            HUD = (HudModule) getRegisteredModule("hud-module");
            ROTATIONS = (RotationsModule) getRegisteredModule(
                    "rotations-module");
            AURA = (AuraModule) getRegisteredModule("aura-module");
            AUTO_ARMOR = (AutoArmorModule) getRegisteredModule("autoarmor-module");
            AUTO_BOW_RELEASE = (AutoBowReleaseModule) getRegisteredModule(
                    "autobowrelease-module");
            AUTO_CRYSTAL = (AutoCrystalModule) getRegisteredModule(
                    "autocrystal-module");
            AUTO_TOTEM = (AutoTotemModule) getRegisteredModule(
                    "autototem-module");
            CRITICALS = (CriticalsModule) getRegisteredModule("criticals-module");
            ANTI_HUNGER = (AntiHungerModule) getRegisteredModule(
                    "antihunger-module");
            FAKE_LATENCY = (FakeLatencyModule) getRegisteredModule(
                    "fakelatency-module");
            REACH = (ReachModule) getRegisteredModule("reach-module");
            SWING = (SwingModule) getRegisteredModule("swing-module");
            AUTO_RESPAWN = (AutoRespawnModule) getRegisteredModule(
                    "autorespawn-module");
            FAKE_PLAYER = (FakePlayerModule) getRegisteredModule(
                    "fakeplayer-module");
            MIDDLE_CLICK = (MiddleClickModule) getRegisteredModule(
                    "middleclick-module");
            TIMER = (TimerModule) getRegisteredModule("timer-module");
            XCARRY = (XCarryModule) getRegisteredModule("xcarry-module");
            ENTITY_CONTROL = (EntityControlModule) getRegisteredModule(
                    "entitycontrol-module");
            FAST_FALL = (FastFallModule) getRegisteredModule("fastfall-module");
            FLIGHT = (FlightModule) getRegisteredModule("flight-module");
            LONG_JUMP = (LongJumpModule) getRegisteredModule("longjump-module");
            NO_FALL = (NoFallModule) getRegisteredModule("nofall-module");
            NO_SLOW = (NoSlowModule) getRegisteredModule("noslow-module");
            SPEED = (SpeedModule) getRegisteredModule("speed-module");
            SPRINT = (SprintModule) getRegisteredModule("sprint-module");
            TICK_SHIFT = (TickShiftModule) getRegisteredModule(
                    "tickshift-module");
            VELOCITY = (VelocityModule) getRegisteredModule("velocity-module");
            YAW = (YawModule) getRegisteredModule("yaw-module");
            BLOCK_HIGHLIGHT = (BlockHighlightModule) getRegisteredModule(
                    "blockhighlight-module");
            ESP = (ESPModule) getRegisteredModule("esp-module");
            FULLBRIGHT = (FullbrightModule) getRegisteredModule(
                    "fullbright-module");
            NAMETAGS = (NametagsModule) getRegisteredModule("nametags-module");
            NO_RENDER = (NoRenderModule) getRegisteredModule("norender-module");
            NO_ROTATE = (NoRotateModule) getRegisteredModule("norotate-module");
            NO_WEATHER = (NoWeatherModule) getRegisteredModule(
                    "noweather-module");
            VIEW_CLIP = (ViewClipModule) getRegisteredModule(
                    "viewclip-module");
            // VIEW_MODEL = (ViewModelModule) getRegisteredModule(
            //        "viewmodel-module");
            AVOID = (AvoidModule) getRegisteredModule("avoid-module");
            FAST_PLACE = (FastPlaceModule) getRegisteredModule(
                    "fastplace-module");
            SPEEDMINE = (SpeedmineModule) getRegisteredModule(
                    "speedmine-module");
            initialized = true;
            // reflect configuration properties for each cached module
            for (Module module : CACHE)
            {
                if (module == null)
                {
                    continue;
                }
                module.reflectConfigs();
            }
            CACHE.clear();
        }
        else
        {
            throw new RuntimeException("Accessed modules before managers " +
                    "finished initializing!");
        }
    }

    /**
     * Returns <tt>true</tt> if the {@link Module} instances have been
     * initialized. This should always return <tt>true</tt> if
     * {@link Caspian#preInit()} has finished running.
     *
     * @return <tt>true</tt> if the module instances have been initialized
     *
     * @see #init()
     * @see #initialized
     */
    public static boolean isInitialized()
    {
        return initialized;
    }
}
