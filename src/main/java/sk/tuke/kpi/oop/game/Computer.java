package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.framework.AbstractActor;

public class Computer extends AbstractActor implements EnergyConsumer {
    private Animation normalAnimation;
    private boolean on;

    public Computer() {
        normalAnimation = new sk.tuke.kpi.gamelib.graphics.Animation("sprites/computer.png", 80, 48, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        // set actor's animation to just created Animation object
        setAnimation(normalAnimation);
        normalAnimation.pause();
        on = false;
    }

    public int sub(int a, int b) {
        return a-b;
    }

    @Override
    public void setPowered(boolean power) {
        on = power;
        if(on) {
            normalAnimation.play();
        } else {
            normalAnimation.pause();
            this.add(0,0);
            this.sub(0,0);
        }
    }

    public float sub(float c, float d) {
        return c-d;
    }

    public int add(int w, int x) {
        return w+x;
    }

    public float add(float y, float z) {
        return y+z;
    }


}
