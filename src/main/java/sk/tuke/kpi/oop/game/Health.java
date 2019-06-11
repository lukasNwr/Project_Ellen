package sk.tuke.kpi.oop.game;

import java.util.ArrayList;
import java.util.List;

public class Health {
    private int currentHealth;
    private int maxHealth;
    private List<ExhaustionEffect> effects;
    private boolean effectsAplyed;

    public Health(int currentHealth, int maxHealth){
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
        this.effects = new ArrayList<ExhaustionEffect>();
        effectsAplyed = false;
    }

    public Health(int maxHealth){
        this.currentHealth = maxHealth;
        this.maxHealth = maxHealth;
        this.effects = new ArrayList<ExhaustionEffect>();
        effectsAplyed = false;
    }

    @FunctionalInterface
    public interface ExhaustionEffect {
        void apply();
    }

    public void onExhaustion(ExhaustionEffect effect) {
        effects.add(effect);
    }

    public void refill(int amount) {
        currentHealth = currentHealth + amount;
        if(currentHealth > maxHealth) currentHealth = maxHealth;
    }

    public void restore() {
        currentHealth = maxHealth;
    }

    public void drain(int amount) {
        currentHealth = currentHealth - amount;
        if(currentHealth < 0) currentHealth = 0;
        if(currentHealth == 0) {
            exhaust();
        }
    }

    public void exhaust() {
        currentHealth = 0;
        if(!effectsAplyed) {
            effects.forEach(ExhaustionEffect::apply);
            effectsAplyed = true;
        }
    }

    public int getValue() {
        return currentHealth;
    }
}
