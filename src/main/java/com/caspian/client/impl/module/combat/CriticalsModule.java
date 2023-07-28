package com.caspian.client.impl.module.combat;

import com.caspian.client.api.config.Config;
import com.caspian.client.api.config.setting.BooleanConfig;
import com.caspian.client.api.config.setting.EnumConfig;
import com.caspian.client.api.event.listener.EventListener;
import com.caspian.client.api.module.ModuleCategory;
import com.caspian.client.api.module.ToggleModule;
import com.caspian.client.impl.event.TickEvent;
import com.caspian.client.impl.event.network.PacketEvent;
import com.caspian.client.init.Managers;
import com.caspian.client.init.Modules;
import com.caspian.client.util.math.timer.CacheTimer;
import com.caspian.client.util.math.timer.Timer;
import com.caspian.client.util.string.EnumFormatter;
import com.caspian.client.util.world.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class CriticalsModule extends ToggleModule
{
    //
    Config<CritMode> modeConfig = new EnumConfig<>("Mode", "Mode for critical" +
            " attack modifier", CritMode.PACKET, CritMode.values());
    Config<Boolean> packetSyncConfig = new BooleanConfig("Packet-Sync",
            "Syncs the cached packet interaction to the MC tick", false);

    //
    private final Timer attackTimer = new CacheTimer();
    // The cached attack packets that will be resent after manipulating
    // player position packets
    private PlayerInteractEntityC2SPacket attackPacket;
    private HandSwingC2SPacket swingPacket;
    // RANDOM
    private final Random random = new Random();

    /**
     *
     *
     */
    public CriticalsModule()
    {
        super("Criticals", "Modifies attacks to guarentee critical hits",
                ModuleCategory.COMBAT);
    }

    /**
     *
     *
     * @return
     */
    @Override
    public String getMetaData()
    {
        return EnumFormatter.formatEnum(modeConfig.getValue());
    }

    /**
     *
     *
     * @param event
     */
    @EventListener
    public void onTick(TickEvent event)
    {
        if (packetSyncConfig.getValue() && attackPacket != null
                && swingPacket != null)
        {
            Managers.NETWORK.sendPacket(attackPacket);
            Managers.NETWORK.sendPacket(swingPacket);
            attackPacket = null;
            swingPacket = null;
        }
    }

    /**
     *
     *
     * @param event
     */
    @EventListener
    public void onPacketOutbound(PacketEvent.Outbound event)
    {
        // Custom aura crit handling
        if (Modules.AURA.isEnabled())
        {
            return;
        }
        if (event.getPacket() instanceof PlayerInteractEntityC2SPacket packet)
        {
            ServerWorld world = (ServerWorld) mc.player.getWorld();
            // Attacked entity
            final Entity e = packet.getEntity(world);
            if (e != null && e.isAlive())
            {
                if (!Managers.POSITION.isOnGround()
                        || mc.player.isRiding()
                        || mc.player.isSubmergedInWater()
                        || mc.player.isInLava()
                        || mc.player.isHoldingOntoLadder()
                        || mc.player.hasStatusEffect(StatusEffects.BLINDNESS)
                        || mc.player.input.jumping)
                {
                    return;
                }
                if (e instanceof EndCrystalEntity)
                {
                    return;
                }
                if (attackTimer.passed(500))
                {
                    event.cancel();
                    if (EntityUtil.isVehicle(e))
                    {
                        for (int i = 0; i < 5; ++i)
                        {
                            Managers.NETWORK.sendPacket(PlayerInteractEntityC2SPacket.attack(e,
                                    Managers.POSITION.isSneaking()));
                            Managers.NETWORK.sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
                        }
                    }
                    else
                    {
                        attackPacket = packet;
                        preAttack();
                        if (!packetSyncConfig.getValue())
                        {
                            Managers.NETWORK.sendPacket(PlayerInteractEntityC2SPacket.attack(
                                    e, Managers.POSITION.isSneaking()));
                        }
                        mc.player.addCritParticles(e);
                    }
                    attackTimer.reset();
                }
            }
        }
        else if (event.getPacket() instanceof HandSwingC2SPacket packet)
        {
            if (packetSyncConfig.getValue() && attackPacket != null)
            {
                event.cancel();
                swingPacket = packet;
            }
        }
    }

    /**
     *
     *
     */
    public void preAttack()
    {
        double x = Managers.POSITION.getX();
        double y = Managers.POSITION.getY();
        double z = Managers.POSITION.getZ();
        switch (modeConfig.getValue())
        {
            case PACKET ->
            {
                double d = 1.0e-7 + 1.0e-7 * (1.0 + random.nextInt(random.nextBoolean() ? 34 : 43));
                Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                        x, y + 0.1016f + d * 3.0f, z, false));
                Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                        x, y + 0.0202f + d * 2.0f, z, false));
                Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                        x, y + 3.239e-4 + d, z, false));
            }
            case PACKET_STRICT ->
            {
                Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                        x, y + 0.11f, z, false));
                Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                        x, y + 0.1100013579f, z, false));
                Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                        x, y + 0.0000013579f, z, false));
            }
            case VANILLA_STRICT ->
            {
                Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                        x, y + 0.0626024016927725f, z, false));
                Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                        x, y + 0.0726023996066094f, z, false));
                Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                        x, y, z, false));
                Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                        x, y, z, false));
            }
            case LOW_JUMP ->
            {
                mc.player.jump();
                final Vec3d motion = mc.player.getVelocity();
                mc.player.setVelocity(motion.getX(),
                        motion.getY() / 2.0, motion.getZ());
            }
        }
    }

    public enum CritMode
    {
        PACKET,
        PACKET_STRICT,
        VANILLA_STRICT,
        LOW_JUMP
    }
}