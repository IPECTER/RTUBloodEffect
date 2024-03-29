package com.github.ipecter.rtu.bloodeffect.listeners;

import com.github.ipecter.rtu.bloodeffect.events.BloodEvent;
import com.github.ipecter.rtu.bloodeffect.functions.HitLocation;
import com.github.ipecter.rtu.bloodeffect.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Map;

public class EntityDamageByEntity implements Listener {

    private final ConfigManager configManager = ConfigManager.getInstance();

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (!configManager.isEnablePlugin()) return;
        Entity victim = e.getEntity();
        Entity attacker = e.getDamager();
        if (!(attacker instanceof Projectile) && attacker instanceof LivingEntity && victim instanceof LivingEntity) {
            Location hitlocation = HitLocation.getHitLocation_Attack((LivingEntity) attacker, (LivingEntity) victim, configManager.getAccuracy());
            if (hitlocation == null) hitlocation = victim.getBoundingBox().getCenter().toLocation(victim.getWorld());
            Material material = configManager.getDefaultMaterial();
            Map<String, String> mobMaterial = configManager.getMobMaterial();
            String entityTypeName = victim.getType().toString();
            if (mobMaterial.containsKey(entityTypeName)) {
                Material findMaterial = Material.getMaterial(mobMaterial.get(entityTypeName));
                material = findMaterial != null ? findMaterial : material;
            }
            BloodEvent event = new BloodEvent(attacker, victim, hitlocation, material);
            Bukkit.getPluginManager().callEvent(event);
            if (!event.isCancelled())
                HitLocation.particle(victim.getWorld(), hitlocation, configManager.getAmount(), material);
        }
    }
}
