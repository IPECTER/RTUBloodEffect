package com.github.ipecter.rtu.bloodeffect.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedParticle;
import com.github.ipecter.rtu.bloodeffect.managers.ConfigManager;
import org.bukkit.Particle;
import org.bukkit.plugin.Plugin;

public class BloodHeartParticle extends PacketAdapter {

    public BloodHeartParticle(Plugin plugin) {
        super((new PacketAdapter.AdapterParameteters())
                .plugin(plugin)
                .listenerPriority(ListenerPriority.HIGHEST)
                .types(new PacketType[]{PacketType.Play.Server.WORLD_PARTICLES})
                .optionAsync());
    }

    public void onPacketSending(PacketEvent event) {
        PacketContainer packet = event.getPacket();
        if (event.getPacketType() != PacketType.Play.Server.WORLD_PARTICLES)
            return;
        if (((WrappedParticle) packet.getNewParticles().read(0)).getParticle() == Particle.DAMAGE_INDICATOR) {
            if (ConfigManager.getInstance().isParticleDisableVanillaDamage()) event.setCancelled(true);
        }
    }
}
