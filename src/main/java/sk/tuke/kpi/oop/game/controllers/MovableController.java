package sk.tuke.kpi.oop.game.controllers;

import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.other.Direction;
import sk.tuke.kpi.oop.game.other.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class MovableController implements KeyboardListener {
    private Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.DOWN, Direction.SOUTH),
        Map.entry(Input.Key.LEFT, Direction.WEST),
        Map.entry(Input.Key.RIGHT, Direction.EAST)
    );
    private Movable actor;
    private Move<Movable> move;
    private List<Input.Key> pressedKeys;

    public MovableController(Movable actor) {
        this.actor = actor;
        pressedKeys = new ArrayList<>();
    }

    @Override
    public void keyPressed(Input.Key key) {
        if(keyDirectionMap.containsKey(key)) {
            if(move != null) move.stop();
            pressedKeys.add(key);
            Direction direction;
            if(pressedKeys.size() == 1) {
                move = new Move<>(keyDirectionMap.get(pressedKeys.get(0)), 1);
                actor.getScene().scheduleAction(move, actor);
                //System.out.println(keyDirectionMap.get(key));
            }
            for (int i = 0; i < pressedKeys.size() - 1; i++) {
                direction = keyDirectionMap.get(pressedKeys.get(i)).combine(keyDirectionMap.get(pressedKeys.get(i + 1)));
                //System.out.println(direction);
                move = new Move<>(direction, 1);
                actor.getScene().scheduleAction(move, actor);
            }
        }
        //System.out.println(pressedKeys);
    }

    @Override
    public void keyReleased(Input.Key key) {
        if(keyDirectionMap.containsKey(key)) {
            pressedKeys.remove(key);
            move.stop();
            Direction direction;
            if(pressedKeys.size() == 1) {
                move = new Move<>(keyDirectionMap.get(pressedKeys.get(0)), 1);
                actor.getScene().scheduleAction(move, actor);
                //System.out.println(keyDirectionMap.get(key));
            }
            for (int i = 0; i < pressedKeys.size() - 1; i++) {
                direction = keyDirectionMap.get(pressedKeys.get(i)).combine(keyDirectionMap.get(pressedKeys.get(i + 1)));
                //System.out.println(direction);
                move = new Move<>(direction, 1);
                actor.getScene().scheduleAction(move, actor);
            }
        }
    }
}
