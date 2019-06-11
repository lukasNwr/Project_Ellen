package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.other.Direction;
import sk.tuke.kpi.oop.game.other.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

import java.util.List;
import java.util.stream.Collectors;

public class Alien extends AbstractActor implements Movable, Enemy, Alive {

    private Animation animation;
    private int speed;
    private Health health;
    private Behaviour<? super Alien> behaviour;

    public Alien() {
        super();
        this.animation = new Animation("sprites/alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(animation);
        animation.pause();
        this.speed = 1;
        this.health = new Health(100);
        this.behaviour = null;
    }

    public Alien(int healthValue, Behaviour<? super Alien> behaviour) {
        super();
        this.animation = new Animation("sprites/alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(animation);
        animation.pause();
        this.speed = 1;
        this.health = new Health(healthValue);
        this.behaviour = behaviour;

    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public int getSpeed() {
        return speed;
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

    public void kill(Alive actor) {
        if(this.intersects(actor)) {
            actor.getHealth().drain(2);
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        if(behaviour == null) this.stoppedMoving();
        else behaviour.setUp(this);

        List<Alive> actors = scene.getActors().stream()
            .filter(Alive.class::isInstance)
            .map(Alive.class::cast)
            .collect(Collectors.toList());

        actors.removeIf(x -> x instanceof Enemy);

        for(Alive a : actors) {
            new Loop<>(
                new When<>(
                    (action) -> this.intersects(a),
                    new ActionSequence<>(
                        new Invoke<>(() -> this.kill(a)),
                        new Wait<>(0.2f)
                    )

                )
            ).scheduleOn(this);
        }

    }
}
