package com.github.ipecter.rtu.bloodeffect.events;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class BloodEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private Entity attacker;

    private Entity victim;

    private Location bloodloc;

    private boolean isCancelled;

    public BloodEvent(Entity attacker, Entity victim, Location bloodloc) {
        this.attacker = attacker;
        this.victim = victim;
        this.bloodloc = bloodloc;
    }

    public Entity getAttacker() {
        return this.attacker;
    }

    public Entity getVictim() {
        return this.victim;
    }

    public Location getBloodLocation() {
        return this.bloodloc;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public boolean isCancelled() {
        return this.isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }
}
