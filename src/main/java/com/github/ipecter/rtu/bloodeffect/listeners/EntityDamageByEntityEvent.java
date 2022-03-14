package com.github.ipecter.rtu.bloodeffect.listeners;

import com.github.ipecter.rtu.bloodeffect.events.BloodEvent;
import com.github.ipecter.rtu.bloodeffect.functions.HitLocation;
import com.github.ipecter.rtu.bloodeffect.util.ConfigManager;
import com.github.ipecter.rtu.bloodeffect.util.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityDamageByEntityEvent implements Listener {
    private ConfigManager configManager;
    private Data data = Data.getInstance();
  public EntityDamageByEntityEvent(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @EventHandler
    public void onEntityDamageByEntity(org.bukkit.event.entity.EntityDamageByEntityEvent e) {
        Entity victim = e.getEntity();
        Entity attacker = e.getDamager();
        if (!(attacker instanceof Projectile) &&  attacker instanceof LivingEntity && victim instanceof LivingEntity) {
            Location hitlocation = HitLocation.getHitLocation_Battle((LivingEntity)attacker, (LivingEntity)victim, Double.valueOf(this.configManager.getAccuracy()));
            if (hitlocation != null) {
                BloodEvent event = new BloodEvent(attacker, victim, hitlocation);
                Bukkit.getPluginManager().callEvent(event);
                if (!event.isCancelled())
                    if (data.mobList.containsKey(e.getEntity().getType().toString())) {
                        HitLocation.particle(e.getEntity(), hitlocation, Integer.valueOf(this.configManager.getAmount()), data.mobList.get(e.getEntity().getType().toString()));
                    } else {
                        HitLocation.particle(e.getEntity(), hitlocation, Integer.valueOf(this.configManager.getAmount()), Material.REDSTONE_BLOCK);
                    }
            }
        }
    }
}
