package sk.tuke.kpi.oop.game.other;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class SmartCooler extends Cooler {
    private Reactor reactor;


    public SmartCooler(@NotNull Reactor reactor) {
        super(reactor);
        this.reactor = reactor;
    }

    private void smartCool() {
        if(reactor == null){
            return;
        }
        if (reactor.getTemperature() < 1500) {
            turnOff();
        }
        if (reactor.getTemperature() > 2500 ) {
            turnOn();
            if (isOn()) {
                reactor.decreaseTemperature(1);
            }
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::smartCool)).scheduleOn(this);
    }
}
