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

public class ProjectileHitEvent implements Listener {
    private Data data = Data.getInstance();
    @EventHandler
    public void onProjectileHit(org.bukkit.event.entity.ProjectileHitEvent e) {
        if (e.getHitEntity() != null && e.getHitEntity() instanceof LivingEntity) {
            Location hitlocation = HitLocation.getHitLocation_Projectile((Entity)e.getEntity(), e.getHitEntity(), Double.valueOf(data.getAccuracy()));
            if (hitlocation != null) {
                BloodEvent event = new BloodEvent((Entity)e.getEntity(), e.getHitEntity(), hitlocation);
                Bukkit.getPluginManager().callEvent(event);
                if (!event.isCancelled())
                    if (data.mobList.containsKey(e.getHitEntity().getType().toString())) {
                        HitLocation.particle(e.getEntity().getWorld(), hitlocation, Integer.valueOf(data.getAmount()), data.mobList.get(e.getHitEntity().getType().toString()));
                    } else {
                        HitLocation.particle(e.getEntity().getWorld(), hitlocation, Integer.valueOf(data.getAmount()), Material.REDSTONE_BLOCK);
                    }
            }
        }
    }
}
