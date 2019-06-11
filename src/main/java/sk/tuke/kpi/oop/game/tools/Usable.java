package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.Actor;

public interface Usable <T extends Actor> {
    void useWith(T actor);
}
