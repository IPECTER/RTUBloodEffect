package com.github.ipecter.rtu.bloodeffect.functions;

import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class HitLocation {

    public static final Location getHitLocation_Attack(LivingEntity attacker, LivingEntity victim, Double accuracy) {
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

    public static final Location getHitLocation_Projectile(Entity projectile, Entity victim, Double accuracy) {
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

    private static final Vector getDirectionBetweenLocations(Location Start, Location End) {
        Vector from = Start.toVector();
        Vector to = End.toVector();
        return to.subtract(from);
    }


    public static final void particle(World world, Location hitlocation, Integer amount, Material material) {
        BlockData blockCrackData = material.createBlockData();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getWorld() == world && player.hasPermission("rtube.use") && player.hasPermission("rtube.status")) {
                player.spawnParticle(Particle.BLOCK_CRACK, hitlocation.getX(), hitlocation.getY(), hitlocation.getZ(), amount.intValue(), blockCrackData);
            }
        }
    }
}
