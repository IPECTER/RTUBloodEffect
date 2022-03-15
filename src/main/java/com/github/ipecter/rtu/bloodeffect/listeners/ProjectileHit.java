package com.github.ipecter.rtu.bloodeffect.listeners;

import com.github.ipecter.rtu.bloodeffect.events.BloodEvent;
import com.github.ipecter.rtu.bloodeffect.functions.HitLocation;
import com.github.ipecter.rtu.bloodeffect.util.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileHit implements Listener {
    private Data data = Data.getInstance();
    private HitLocation hl = HitLocation.getInstance();

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        if (e.getHitEntity() != null && e.getHitEntity() instanceof LivingEntity) {
            Location hitlocation = hl.getHitLocation_Projectile((Entity) e.getEntity(), e.getHitEntity(), Double.valueOf(data.getAccuracy()));
            if (hitlocation != null) {
                BloodEvent event = new BloodEvent((Entity) e.getEntity(), e.getHitEntity(), hitlocation);
                Bukkit.getPluginManager().callEvent(event);
                if (!event.isCancelled())
                    if (data.mobList.containsKey(e.getHitEntity().getType().toString())) {
                        hl.particle(e.getEntity().getWorld(), hitlocation, Integer.valueOf(data.getAmount()), data.mobList.get(e.getHitEntity().getType().toString()));
                    } else {
                        hl.particle(e.getEntity().getWorld(), hitlocation, Integer.valueOf(data.getAmount()), Material.REDSTONE_BLOCK);
                    }
            }
        }
    }
}
