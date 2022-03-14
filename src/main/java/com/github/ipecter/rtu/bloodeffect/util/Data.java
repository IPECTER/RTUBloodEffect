package com.github.ipecter.rtu.bloodeffect.util;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class Data {
    private Data() {
    }

    private static class LazyHolder {
        public static final Data INSTANCE = new Data();
    }

    public static Data getInstance() {
        return LazyHolder.INSTANCE;
    }

    private double accuracy;

    private int amount;

    private boolean rhp;

    public double getAccuracy() {
        return this.accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isRhp() {
        return this.rhp;
    }

    public void setRhp(boolean rhp) {
        this.rhp = rhp;
    }

    public Map<String, Material> mobList = new HashMap<>();

}
