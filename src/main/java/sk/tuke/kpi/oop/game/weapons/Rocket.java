package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.other.Direction;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.Alive;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Rocket extends AbstractActor implements Fireable{
    private int speed;
    private Animation animation;
    private Behaviour<? super Rocket> behaviour;
    private List<Alive> actors = new ArrayList<>();

    public Rocket(Behaviour<? super Rocket> behaviour) {
        this.speed = 1;
        animation = new Animation("sprites/rocket.png", 16, 16);
        setAnimation(animation);
        this.behaviour = behaviour;

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
    public int getSpeed() {
        return this.speed;
    }

    public void collisionWithActor() {

        actors = this.getScene().getActors().stream()
            .filter(Alien.class::isInstance)
            .map(Alien.class::cast)
            .collect(Collectors.toList());


        for (Alive a : actors) {
            new When<>(
                (action) -> this.intersects(a),
                new ActionSequence<>(
                    new Invoke<>(() -> {
                        a.getHealth().drain(10);
                        if (a.getHealth().getValue() == 0)
                            this.getScene().removeActor(a);
                    }),
                    new Invoke<>(() -> this.getScene().removeActor(this))
                )

            ).scheduleOn(this.getScene());
        }
    }


    @Override
    public void collidedWithWall() {
        this.getScene().removeActor(this);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        behaviour.setUp(this);
        new Invoke<>(this::collisionWithActor).scheduleOn(this);
    }
}
