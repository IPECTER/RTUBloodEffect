package com.github.ipecter.rtu.bloodeffect.listeners;

import com.github.ipecter.rtu.bloodeffect.events.BloodEvent;
import com.github.ipecter.rtu.bloodeffect.functions.HitLocation;
import com.github.ipecter.rtu.bloodeffect.util.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntity implements Listener {
    private Data data = Data.getInstance();
    private HitLocation hl = HitLocation.getInstance();

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        Entity victim = e.getEntity();
        Entity attacker = e.getDamager();
        if (!(attacker instanceof Projectile) && attacker instanceof LivingEntity && victim instanceof LivingEntity) {
            Location hitlocation = hl.getHitLocation_Battle((LivingEntity) attacker, (LivingEntity) victim, Double.valueOf(data.getAccuracy()));
            if (hitlocation != null) {
                BloodEvent event = new BloodEvent(attacker, victim, hitlocation);
                Bukkit.getPluginManager().callEvent(event);
                if (!event.isCancelled())
                    if (data.mobList.containsKey(e.getEntity().getType().toString())) {
                        hl.particle(e.getEntity().getWorld(), hitlocation, Integer.valueOf(data.getAmount()), data.mobList.get(e.getEntity().getType().toString()));
                    } else {
                        hl.particle(e.getEntity().getWorld(), hitlocation, Integer.valueOf(data.getAmount()), Material.REDSTONE_BLOCK);
                    }
            }
        }
    }
}
