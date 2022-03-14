package com.github.ipecter.rtu.bloodeffect.functions;

import com.github.ipecter.rtu.bloodeffect.Main;
import com.github.ipecter.rtu.bloodeffect.util.ConfigManager;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class HitLocation {
    public static Location getHitLocation_Battle(LivingEntity attacker, LivingEntity victim, Double accuracy) {
        Location Loc1 = attacker.getEyeLocation();
        Location Loc2 = attacker.getEyeLocation().add(attacker.getLocation().getDirection().multiply(10));
        Vector vector = getDirectionBetweenLocations(Loc1, Loc2);
        double i;
        for (i = 1.0D; i <= Loc1.distance(Loc2); i += accuracy.doubleValue()) {
            vector.multiply(i);
            Loc1.add(vector);
            boolean isX = false;
            boolean isY = false;
            boolean isZ = false;
            if (victim.getBoundingBox().getMinX() - 0.5D <= Loc1.getX() && victim
                    .getBoundingBox().getMaxX() + 0.5D >= Loc1.getX())
                isX = true;
            if (victim.getBoundingBox().getMinY() - 0.5D <= Loc1.getY() && victim
                    .getBoundingBox().getMaxY() + 0.5D >= Loc1.getY())
                isY = true;
            if (victim.getBoundingBox().getMinZ() - 0.5D <= Loc1.getZ() && victim
                    .getBoundingBox().getMaxZ() + 0.5D >= Loc1.getZ())
                isZ = true;
            if (isX && isY && isZ)
                return Loc1;
            Loc1.subtract(vector);
            vector.normalize();
        }
        return null;
    }

    public static Location getHitLocation_Projectile(Entity projectile, Entity victim, Double accuracy) {
        ConfigManager configManager = new ConfigManager((Plugin) Main.getPlugin(Main.class));
        Location Loc1 = projectile.getLocation().add(projectile.getVelocity().multiply(-3));
        Location Loc2 = projectile.getLocation().add(projectile.getVelocity().multiply(3));
        Vector vector = getDirectionBetweenLocations(Loc1, Loc2);
        double i;
        for (i = 1.0D; i <= Loc1.distance(Loc2); i += accuracy.doubleValue()) {
            vector.multiply(i);
            Loc1.add(vector);
            boolean isX = false;
            boolean isY = false;
            boolean isZ = false;
            if (victim.getBoundingBox().getMinX() - 0.5D <= Loc1.getX() && victim
                    .getBoundingBox().getMaxX() + 0.5D >= Loc1.getX())
                isX = true;
            if (victim.getBoundingBox().getMinY() - 0.5D <= Loc1.getY() && victim
                    .getBoundingBox().getMaxY() + 0.5D >= Loc1.getY())
                isY = true;
            if (victim.getBoundingBox().getMinZ() - 0.5D <= Loc1.getZ() && victim
                    .getBoundingBox().getMaxZ() + 0.5D >= Loc1.getZ())
                isZ = true;
            if (isX && isY && isZ)
                return Loc1;
            Loc1.subtract(vector);
            vector.normalize();
        }
        return null;
    }

    public static Vector getDirectionBetweenLocations(Location Start, Location End) {
        Vector from = Start.toVector();
        Vector to = End.toVector();
        return to.subtract(from);
    }

    public static void particle(World world, Location hitlocation, Integer amount, Material material) {
        BlockData blockCrackData = material.createBlockData();
        for (Player player : Bukkit.getOnlinePlayers() ) {
            if ()
            player.spawnParticle(Particle.BLOCK_CRACK, hitlocation.getX(), hitlocation.getY(), hitlocation.getZ(), amount.intValue(), blockCrackData);
        }
    }

    public static boolean isNoBoundingBox(Location loc, Entity victim) {
        if (victim.getType() != EntityType.SLIME) {
            double distance = loc.distance(victim.getLocation().add(0.0D, 0.5D, 0.0D));
            if (distance <= 1.0D)
                return true;
            distance = loc.distance(victim.getLocation().add(0.0D, 1.5D, 0.0D));
            if (distance <= 1.0D)
                return true;
        } else {
            Slime slime = (Slime) victim;
            if (slime.getSize() == 1) {
                double distance = loc.distance(victim.getLocation().add(0.0D, 0.25D, 0.0D));
                if (distance <= 1.0D)
                    return true;
            } else if (slime.getSize() == 2) {
                double distance = loc.distance(victim.getLocation().add(0.0D, 0.5D, 0.0D));
                if (distance <= 1.0D)
                    return true;
            } else if (slime.getSize() == 4) {
                double distance = loc.distance(victim.getLocation().add(0.0D, 1.0D, 0.0D));
                if (distance <= 2.0D)
                    return true;
            }
        }
        return false;
    }
}
