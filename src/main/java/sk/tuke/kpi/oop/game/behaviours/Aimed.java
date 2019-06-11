package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.other.Direction;
import sk.tuke.kpi.oop.game.other.Movable;
import sk.tuke.kpi.oop.game.actions.Move;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.weapons.Fireable;

import java.util.ArrayList;
import java.util.List;

public class Aimed extends AbstractActor implements Behaviour<Fireable> {

    private List<Direction> directions = new ArrayList<>();
    private Move<Movable> move;

    @Override
    public void setUp(Fireable actor) {
        if(actor == null) {
            return;
        }

        Alien alien = actor.getScene().getActors().stream()
            .filter(Alien.class::isInstance)
            .map(Alien.class::cast)
            .findFirst()
            .orElse(null);

        if(alien == null) {
            return;
        }

        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(
                    () -> {
                        Direction direction = Direction.NONE;
                        if(actor.getPosX() > alien.getPosX()){
                            directions.add(Direction.WEST);
                        }
                        if(actor.getPosX() < alien.getPosX()){
                            directions.add(Direction.EAST);
                        }
                        if(actor.getPosY() > alien.getPosY()){
                            directions.add(Direction.SOUTH);
                        }
                        if(actor.getPosY() < alien.getPosY()){
                            directions.add(Direction.NORTH);
                        }

                        if(directions.size() == 1) {
                            direction = directions.get(0);
                        } else
                            for(int i = 0; i < directions.size() - 1; i++) {
                                direction = directions.get(i).combine(directions.get(i+1));
                            }
                        move = new Move<>(direction, 1);
                        actor.getScene().scheduleAction(move, actor);
                        directions.clear();
                    }
                ),
                new Wait<>(0.1f)
            )

        ).scheduleOn(actor);

    }
}
