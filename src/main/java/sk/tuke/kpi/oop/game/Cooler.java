package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Cooler extends AbstractActor implements Switchable {
    private Animation animationOn;
    private Reactor reactor;
    private boolean on;

    public Cooler (Reactor reactor) {
        this.reactor = reactor;
        this.on = false;
        animationOn = new Animation("sprites/fan.png", 32, 32);
        setAnimation(animationOn);
        animationOn.pause();
    }

    @Override
    public void turnOn() {
        on = true;
        animationOn.play();
        animationOn.setPlayMode(Animation.PlayMode.LOOP);
    }

    @Override
    public void turnOff() {
        on = false;
        animationOn.pause();
    }

    @Override
    public boolean isOn() {
        return on;
    }

    public void coolReactor() {
        reactor.decreaseTemperature(1);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::coolReactor)).scheduleOn(this);
    }
}
