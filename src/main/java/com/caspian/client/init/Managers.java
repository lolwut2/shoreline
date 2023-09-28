package com.caspian.client.init;

import com.caspian.client.Caspian;
import com.caspian.client.api.manager.*;
import com.caspian.client.api.manager.anticheat.NCPManager;
import com.caspian.client.api.manager.client.AccountManager;
import com.caspian.client.api.manager.client.CommandManager;
import com.caspian.client.api.manager.client.MacroManager;
import com.caspian.client.api.manager.client.SocialManager;
import com.caspian.client.api.manager.combat.TotemManager;
import com.caspian.client.api.manager.combat.hole.HoleManager;
import com.caspian.client.api.manager.network.latency.LatencyManager;
import com.caspian.client.api.manager.network.NetworkManager;
import com.caspian.client.api.manager.player.InteractionManager;
import com.caspian.client.api.manager.player.InventoryManager;
import com.caspian.client.api.manager.player.MovementManager;
import com.caspian.client.api.manager.player.PositionManager;
import com.caspian.client.api.manager.player.rotation.RotationManager;
import com.caspian.client.api.manager.world.sound.SoundManager;
import com.caspian.client.api.manager.world.tick.TickManager;
import com.caspian.client.api.manager.world.WaypointManager;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class Managers
{
    // The initialized state of the managers. If this is true, all managers
    // have been initialized and the init process is complete. As a general
    // rule, it is good practice to check this state before accessing instances.
    private static boolean initialized;
    // Manager instances. Managers can be statically referenced after
    // initialized. Managers will be initialized in this order.
    public static NetworkManager NETWORK;
    public static ModuleManager MODULE;
    public static MacroManager MACRO;
    public static CommandManager COMMAND;
    public static SocialManager SOCIAL;
    public static WaypointManager WAYPOINT;
    public static AccountManager ACCOUNT;
    public static TickManager TICK;
    public static InventoryManager INVENTORY;
    public static PositionManager POSITION;
    public static RotationManager ROTATION;
    public static NCPManager NCP;
    public static MovementManager MOVEMENT;
    public static HoleManager HOLE;
    public static TotemManager TOTEM;
    public static InteractionManager INTERACT;
    public static LatencyManager LATENCY;
    public static SoundManager SOUND;
    /**
     * Initializes the manager instances. Should not be used if the
     * managers are already initialized.
     *
     * @see #isInitialized()
     */
    public static void init()
    {
        if (!isInitialized())
        {
            NETWORK = new NetworkManager();
            MODULE = new ModuleManager();
            MACRO = new MacroManager();
            SOCIAL = new SocialManager();
            WAYPOINT = new WaypointManager();
            ACCOUNT = new AccountManager();
            TICK = new TickManager();
            INVENTORY = new InventoryManager();
            POSITION = new PositionManager();
            ROTATION = new RotationManager();
            NCP = new NCPManager();
            MOVEMENT = new MovementManager();
            HOLE = new HoleManager();
            TOTEM = new TotemManager();
            INTERACT = new InteractionManager();
            LATENCY = new LatencyManager();
            COMMAND = new CommandManager();
            SOUND = new SoundManager();
            initialized = true;
        }
    }

    /**
     * Initializes final manager properties. Only runs if the Manager
     * instances have been initialized.
     *
     * @see #init()
     * @see #isInitialized()
     */
    public static void postInit()
    {
        if (isInitialized())
        {
            MODULE.postInit();
            MACRO.postInit();
            COMMAND.postInit();
        }
    }

    /**
     * Returns <tt>true</tt> if the Manager instances have been initialized.
     * This should always return <tt>true</tt> if {@link Caspian#preInit()} has
     * finished running.
     *
     * @return <tt>true</tt> if the Manager instances have been initialized
     *
     * @see Caspian#preInit()
     * @see #init()
     * @see #initialized
     */
    public static boolean isInitialized()
    {
        return initialized;
    }
}
