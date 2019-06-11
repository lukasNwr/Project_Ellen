package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.other.Direction;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

public class Lurker extends Alien {
    private Health health;
    private Animation animation;
    private Animation bornAnimation;
    private Behaviour<? super Alien> behaviour;

    public Lurker(int healthValue, Behaviour<? super Alien> behaviour) {
        super(healthValue, behaviour);
        this.health = new Health(50);
        this.animation = new Animation("sprites/lurker_alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(animation);
        this.setSpeed(2);

    }

    @Override
    public void startedMoving(Direction direction) {
        animation.setRotation(direction.getAngle());
        animation.play();
    }

    @Override
    public void stoppedMoving() {
        animation.stop();
    }

    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
    }
}
