package sk.tuke.kpi.oop.game.characters;


import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.behaviours.AngryJumping;

public class AlienEgg extends AbstractActor implements Alive, Enemy {

    private int timeToBorn;
    private Health health;
    private Animation animation;
    private boolean dead;

    public AlienEgg() {
        super();
        setPosition(0, 0);
        animation = new Animation("sprites/alien_egg.png", 32, 32, 0.5f, Animation.PlayMode.ONCE);
        setAnimation(animation);
        animation.pause();
        this.timeToBorn = 100;
        this.health = new Health(50);
        this.dead = false;

    }

    private void spawn() {
        Lurker lurker = new Lurker(100, new AngryJumping());
        lurker.setPosition(this.getPosX(), this.getPosY());
        this.getScene().addActor(lurker);
    }


    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(
                    () -> {
                        //System.out.println(timeToBorn);
                        if (timeToBorn == 0) {
                            animation.play();
                            spawn();
                        }
                        if (this.health.getValue() <= 0) {
                            if(!dead) {
                                int chanceToBorn = (int) (Math.random() * 20);
                                if (chanceToBorn % 2 == 0) {
                                    animation.play();
                                    spawn();
                                }
                                animation.play();
                                dead = true;
                            }
                        }
                        this.timeToBorn--;
                    }
                ),
                new Wait<>(1)
            )

        ).scheduleOn(this);
    }
}


