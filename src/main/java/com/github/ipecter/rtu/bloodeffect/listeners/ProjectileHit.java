package com.github.ipecter.rtu.bloodeffect.listeners;

import com.github.ipecter.rtu.bloodeffect.events.BloodEvent;
import com.github.ipecter.rtu.bloodeffect.functions.HitLocation;
import com.github.ipecter.rtu.bloodeffect.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.Map;

public class ProjectileHit implements Listener {

    private ConfigManager configManager = ConfigManager.getInstance();

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        if (!configManager.isEnablePlugin()) return;
        if (e.getHitEntity() != null && e.getHitEntity() instanceof LivingEntity) {
            Location hitlocation = HitLocation.getHitLocation_Projectile((Entity) e.getEntity(), e.getHitEntity(), Double.valueOf(configManager.getAccuracy()));
            if (hitlocation != null) {
                Material material = configManager.getDefaultMaterial();
                Map<String, String> mobMaterial = configManager.getMobMaterial();
                String entityTypeName = e.getEntity().getType().toString();
                if (mobMaterial.keySet().contains(entityTypeName)) {
                    Material findMaterial = Material.getMaterial(mobMaterial.get(entityTypeName));
                    material = findMaterial != null ? findMaterial : material;
                }
                BloodEvent event = new BloodEvent(e.getEntity(), e.getHitEntity(), hitlocation, material);
                Bukkit.getPluginManager().callEvent(event);
                if (!event.isCancelled())
                    HitLocation.particle(e.getEntity().getWorld(), hitlocation, Integer.valueOf(configManager.getAmount()), material);
            }
        }
    }
}
