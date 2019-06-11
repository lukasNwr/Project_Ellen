package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.other.Direction;
import sk.tuke.kpi.oop.game.other.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

public class RandomlyMoving implements Behaviour<Movable>{

    @Override
    public void setUp(Movable actor) {
        if(actor == null) return;
        //Direction direction = Direction.getRandom();
        //Move<Movable> move= new Move<>(direction, 1);
        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(
                    () -> {
                        Direction direction = Direction.getRandom();
                        new Move<>(direction, 1).scheduleOn(actor);
                    }
                ),
                new Wait<>(1)

            )
        ).scheduleOn(actor);
    }
}
