package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.other.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.items.Collectible;
import sk.tuke.kpi.oop.game.items.Usable;

public class CollectorController implements KeyboardListener {
    private Keeper<Collectible> actor;

    public CollectorController(Keeper<Collectible> actor) {
        this.actor = actor;
    }
    private boolean isPeekUsable() {
        return actor.getContainer().peek() instanceof Usable;
    }

    private Actor getUsableActor() {
        return actor.getScene().getActors().stream()
            .filter(actor::intersects)
            .filter(Usable.class::isInstance)
            .findFirst()
            .orElse(null);
    }

    @Override
    public void keyPressed(@NotNull Input.Key key) {
        switch(key) {
            case ENTER: {
                new Take<>(Collectible.class).scheduleOn(actor);
                break;
            }
            case BACKSPACE: {
                new Drop<Collectible>().scheduleOn(actor);
                break;
            }
            case S: {
                new Shift().scheduleOn(actor);
                break;
            }
            case U: {
                Actor usable = getUsableActor();
                if(usable == null) break;
                new Use<>((Usable<?>)usable).scheduleOnIntersectingWith(actor);
                break;
            }
            case B: {
                if(actor.getContainer().peek() == null) break;
                if(!isPeekUsable()) break;
                new Use<>(
                    (Usable<?>)actor.getContainer().peek()
                ).scheduleOnIntersectingWith(actor);

                break;
            }
            default: break;
        }
    }
}

