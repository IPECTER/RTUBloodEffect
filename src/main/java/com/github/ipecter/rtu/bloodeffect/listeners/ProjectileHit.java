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
        Entity attacker = (Entity) e.getEntity().getShooter();
        if (victim != null && victim instanceof LivingEntity) {
            Location hitlocation = HitLocation.getHitLocation_Attack((LivingEntity) attacker, (LivingEntity) victim, Double.valueOf(configManager.getAccuracy()));
            if (hitlocation != null) {
                Material material = configManager.getDefaultMaterial();
                Map<String, String> mobMaterial = configManager.getMobMaterial();
                String entityTypeName = victim.getType().toString();
                if (mobMaterial.keySet().contains(entityTypeName)) {
                    Material findMaterial = Material.getMaterial(mobMaterial.get(entityTypeName));
                    material = findMaterial != null ? findMaterial : material;
                }
                BloodEvent event = new BloodEvent(attacker, victim, hitlocation, material);
                Bukkit.getPluginManager().callEvent(event);
                if (!event.isCancelled())
                    HitLocation.particle(victim.getWorld(), hitlocation, Integer.valueOf(configManager.getAmount()), material);
            }
        }
    }
}
