package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.other.Direction;
import sk.tuke.kpi.oop.game.other.Movable;
import sk.tuke.kpi.oop.game.actions.Move;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.ArrayList;
import java.util.List;

public class AngryJumping extends AbstractActor implements Behaviour<Movable> {

    private List<Direction> directions = new ArrayList<>();
    private Move<Movable> move;

    @Override
    public void setUp(Movable actor) {
        if(actor == null) {
            return;
        }

        Ripley ripley = actor.getScene().getActors().stream()
            .filter(Ripley.class::isInstance)
            .map(Ripley.class::cast)
            .findFirst()
            .orElse(null);

        if(ripley == null) {
            return;
        }

        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(
                    () -> {
                        if(!actor.intersects(ripley)) {
                            Direction direction = Direction.NONE;
                            if (actor.getPosX() > ripley.getPosX()) {
                                directions.add(Direction.WEST);
                            }
                            if (actor.getPosX() < ripley.getPosX()) {
                                directions.add(Direction.EAST);
                            }
                            if (actor.getPosY() > ripley.getPosY()) {
                                directions.add(Direction.SOUTH);
                            }
                            if (actor.getPosY() < ripley.getPosY()) {
                                directions.add(Direction.NORTH);
                            }

                            if (directions.size() == 1) {
                                direction = directions.get(0);
                            } else
                                for (int i = 0; i < directions.size() - 1; i++) {
                                    direction = directions.get(i).combine(directions.get(i + 1));
                                }
                            move = new Move<>(direction, 0.5f);
                            actor.getScene().scheduleAction(move, actor);
                            directions.clear();
                        }
                    }
                ),
                new Wait<>(2)
            )

        ).scheduleOn(actor);

    }
}

