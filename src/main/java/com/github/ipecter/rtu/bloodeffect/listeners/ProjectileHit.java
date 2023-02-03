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
        Entity victim = e.getHitEntity();
        if (victim instanceof LivingEntity) {
            Location hitlocation = HitLocation.getHitLocation_Projectile(e.getEntity(), (LivingEntity) victim, configManager.getAccuracy());
            if (hitlocation != null) {
                Material material = configManager.getDefaultMaterial();
                Map<String, String> mobMaterial = configManager.getMobMaterial();
                String entityTypeName = victim.getType().toString();
                if (mobMaterial.containsKey(entityTypeName)) {
                    Material findMaterial = Material.getMaterial(mobMaterial.get(entityTypeName));
                    material = findMaterial != null ? findMaterial : material;
                }
                BloodEvent event = new BloodEvent(e.getEntity(), victim, hitlocation, material);
                Bukkit.getPluginManager().callEvent(event);
                if (!event.isCancelled())
                    HitLocation.particle(victim.getWorld(), hitlocation, configManager.getAmount(), material);
            }
        }
    }
}
