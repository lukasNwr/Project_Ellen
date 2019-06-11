package sk.tuke.kpi.oop.game.openables;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.other.Repairable;

public class DamagedDoor extends Door implements Repairable {

    private Animation animation;
    private boolean repaired;

    public DamagedDoor(String name, Orientation orientation) {
        super(name, orientation);
        repaired = false;
        if(orientation == Orientation.VERTICAL) {
            animation = new Animation("sprites/vdoor.png", 16, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
            setAnimation(animation);
            //animation.stop();
        }
        if(orientation == Orientation.HORIZONTAL) {
            animation = new Animation("sprites/hdoor.png", 32, 16, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
            setAnimation(animation);
            //animation.stop();
        }
    }

    @Override
    public void open() {
        if (repaired) {
            super.open();
            animation.setPlayMode(Animation.PlayMode.ONCE_REVERSED);

        }
    }

    @Override
    public void close() {
        if (repaired) {
            super.close();
            animation.setPlayMode(Animation.PlayMode.ONCE);
        }
        else super.close();
    }

    @Override
    public boolean repair() {
        if(!repaired) {
            animation.stop();
            repaired = true;
        }
        return true;
    }

    @Override
    public void useWith(Actor actor) {
            super.useWith(actor);
    }
}
