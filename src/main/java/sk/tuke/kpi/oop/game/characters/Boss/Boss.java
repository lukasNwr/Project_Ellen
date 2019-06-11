package sk.tuke.kpi.oop.game.characters.Boss;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.other.Direction;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;
import sk.tuke.kpi.oop.game.characters.*;

public class Boss extends Alien {
    private int bonusDamage;
    private boolean empowered;
    private BossState state;
    private Animation animation;
    private boolean readyToDie;
    public static final Topic<Boss> BOSS_DEAD = Topic.create("boss dead", Boss.class);

    public Boss(int healthValue, Behaviour<? super Alien> behaviour) {
        super(healthValue, behaviour);
        this.animation = new Animation("sprites/boss.png", 43, 43, 0.1f, Animation.PlayMode.LOOP);
        setAnimation(animation);
        animation.pause();
        this.empowered = true;
        readyToDie = false;
        this.state = new Normal(this);
        this.getHealth().onExhaustion(() -> {
                this.state = new Empowered(this);
                toggle();
            }
        );
    }
    @Override
    public void startedMoving(Direction direction) {
        animation.setRotation(direction.getAngle());
        animation.play();
    }

    public void setReadyToDie(boolean readyToDie) {
        this.readyToDie = readyToDie;
    }

    public void setBonusDamage(int bonusDamage) {
        this.bonusDamage = bonusDamage;
    }

    public void toggle() {
        if(empowered) {
            empowered = false;
        } else empowered = true;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(
            new Invoke<>(() -> {
                if(readyToDie && empowered) {
                    scene.removeActor(this);
                    scene.getMessageBus().publish(BOSS_DEAD, this);
                }
                this.state.execute();
            })
        ).scheduleOn(this);
    }
}
