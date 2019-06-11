package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements EnergyConsumer, Switchable {
    private Animation lightOn;
    private Animation lightOff;
    private boolean power;
    private boolean on;

    public Light() {
        lightOn = new Animation("sprites/light_on.png", 16, 16);
        lightOff = new Animation ("sprites/light_off.png", 16, 16);
        power = false;
        on = false;
        setAnimation(lightOff);
    }

    public void updateAnimation() {
        if(on && power) {
            setAnimation(lightOn);
        } else setAnimation(lightOff);
    }

    public void toggle() {
        on = !on;
        updateAnimation();
    }

    public void setPowered(boolean power) {
        this.power = power;
        updateAnimation();
    }

    @Override
    public void turnOn() {
        this.on = true;
        updateAnimation();
    }

    @Override
    public void turnOff() {
        this.on = false;
        updateAnimation();
    }

    @Override
    public boolean isOn() {
        return on;
    }
}
