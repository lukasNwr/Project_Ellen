package sk.tuke.kpi.oop.game.other;
import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.items.FireExtinguisher;
import sk.tuke.kpi.oop.game.items.Hammer;

import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.max;


public class Reactor extends AbstractActor implements Switchable, Repairable {

    private int temperature;
    public static final int MinDamage = 0;
    public static final int MaxDamage = 100;
    private int damageTaken;
    private Animation normalAnimation;
    private Animation hotAnimation;
    private Animation brokenAnimation;
    private Animation offAnimation;
    private Animation extAnimation;
    private boolean on;
    private Set<EnergyConsumer> devices;

    public Reactor() {
        this.devices = new HashSet<>();
        temperature = 0;
        damageTaken = 0;
        // create animation object
        offAnimation = new Animation("sprites/reactor.png", 80, 80, 0, Animation.PlayMode.ONCE);
        normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        // set actor's animation to just created Animation object
        setAnimation(offAnimation);
        hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
        brokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        extAnimation = new Animation("sprites/reactor_extinguished.png", 80, 80);
        on = false;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getDamage() {
        return damageTaken;
    }

    public void updateAnimation() {
        if(!isOn() && damageTaken < 100) {
            setAnimation(offAnimation);
        } else
        if(temperature <= 4000) {
            setAnimation(normalAnimation);
        } else
        if(temperature > 4000 && temperature < 6000) {
            setAnimation(hotAnimation);
        } else
        if(temperature >= 6000) {
            setAnimation(brokenAnimation);
        }
    }

    public void increaseTemperature(int increment) {

        if(increment < 0) {
            return;
        }
        if(!isOn()) {
            return;
        }
        if(damageTaken >= 33 && damageTaken <= 66) {
            increment = increment + (increment/2);
        }
        if(damageTaken > 66) {
            increment = increment * 2;
        }

        temperature = temperature + increment;

        if(temperature > 2000) {
            damageTaken = (temperature - 2000) / 40;
        }
        if(damageTaken >= 100) {
            damageTaken = 100;
            this.turnOff();
        }

        updateAnimation();
    }

    public void decreaseTemperature(int decrement) {
        if(decrement < 0) return;
        if(!isOn()) return;
        if (damageTaken < 50) {
            if(temperature == 0) return;
            temperature = temperature - decrement;
        }
        if (damageTaken >= 50 && damageTaken < 100) {
            temperature = temperature - decrement / 2;
        }
        if(temperature < 0){
            temperature = 0;
        }
        updateAnimation();
    }

    public boolean repair() {
        Hammer hammer = new Hammer();
//        if(hammer == null) return false;
        if(damageTaken <= 0 || damageTaken > 100) return false;
        if(MinDamage < damageTaken && damageTaken < MaxDamage) {
            hammer.useWith(this);
            damageTaken = max(0, damageTaken - Hammer.RepairValue);
            temperature = (2000 + (damageTaken * 40));
        }
        updateAnimation();
        return true;
    }

    void addDevice(EnergyConsumer device){
        devices.add((EnergyConsumer) device);
        device.setPowered(on);

    }

    void removeDevice(EnergyConsumer device){
        devices.remove(device);
        device.setPowered(false);

    }

    @Override
    public void turnOn() {
        this.on = true;
        updateAnimation();
        if (!devices.isEmpty()) {
            for(EnergyConsumer temp : devices) {
                temp.setPowered(true);
            }
        }
    }

    @Override
    public void turnOff() {
        this.on = false;
        updateAnimation();
        if (!devices.isEmpty()) {
            for(EnergyConsumer temp : devices) {
                temp.setPowered(false);
            }
        }
    }

    public void extinguish(FireExtinguisher extinguisher) {
        if (this.temperature >= 6000){
            this.temperature = 4000;
            setAnimation(extAnimation);
            extinguisher.useWith(this);
        }
    }

    @Override
    public boolean isOn() {
        return on;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new PerpetualReactorHeating(1).scheduleOn(this);
        scene.scheduleAction(new PerpetualReactorHeating(1), this);
    }
}
