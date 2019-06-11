package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.Scenario;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.map.MapMarker;
import sk.tuke.kpi.oop.game.tools.Hammer;

import java.util.Map;

public class Gameplay extends Scenario {

    @Override
    public void setupPlay(Scene scene) {
        Reactor reactor = new Reactor();
        Cooler cooler = new Cooler(reactor);
        SmartCooler smartCooler = new SmartCooler(reactor);
        Hammer hammer = new Hammer();
        //scene.addActor(reactor, 64, 64);
        reactor.turnOn();

        Map<String, MapMarker> markers = scene.getMap().getMarkers();
        MapMarker reactorArea1 = markers.get("reactor-area-1");
        scene.addActor(reactor, reactorArea1.getPosX(), reactorArea1.getPosY());
        scene.addActor(hammer, 64, 64);
        MapMarker coolerArea1 = markers.get("cooler-area-1");
        scene.addActor(smartCooler, coolerArea1.getPosX(), coolerArea1.getPosY());

        /*
        new ActionSequence<>(
            new Wait(5),
            new Loop<>(new Invoke(cooler::coolReactor)).scheduleOn(cooler)
        );
        /*
        new ActionSequence<>(
            new Invoke(() -> {
                reactor.repair();
            })
        );
        new When<>(
            action -> reactor.getTemperature() >= 3000,
            new Invoke(() -> reactor.repair())
        ).scheduleOn(reactor);
        */
    }

}
