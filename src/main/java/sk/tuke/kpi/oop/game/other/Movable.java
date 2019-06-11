package sk.tuke.kpi.oop.game.other;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.oop.game.other.Direction;

public interface Movable extends Actor {

    int getSpeed();

    default void startedMoving(Direction direction){}

    default void stoppedMoving(){}

    default void collidedWithWall(){}
}
