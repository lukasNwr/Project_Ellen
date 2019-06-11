package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.other.Direction;
import sk.tuke.kpi.oop.game.characters.AlienEgg;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Boss.Boss;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.List;
import java.util.stream.Collectors;

public class Bullet extends AbstractActor implements Fireable {
    private int speed;
    private Animation animation;

    public Bullet() {
        this.speed = 4;
        animation = new Animation("sprites/bullet1.png", 16, 16);
        setAnimation(animation);
    }


    @Override
    public void startedMoving(Direction direction) {
        animation.setRotation(direction.getAngle());
    }

    public void collisionWithActor() {

        List<Alive> actors = this.getScene().getActors().stream()
            .filter(Alive.class::isInstance)
            .map(Alive.class::cast)
            .collect(Collectors.toList());

        actors.removeIf(x -> x instanceof Ripley);

        for (Alive a : actors) {
            new When<>(
                (action) -> this.intersects(a),
                new ActionSequence<>(
                    new Invoke<>(() -> {
                        a.getHealth().drain(10);
                        if(a.getHealth().getValue() == 0) {
                            if(a instanceof Boss || a instanceof AlienEgg) {
                                return;
                            }
                            this.getScene().removeActor(a);
                        }
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
    public int getSpeed() {
        return speed;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        this.collisionWithActor();

        //actors.removeIf(x -> x instanceof Ripley);


    }
}
