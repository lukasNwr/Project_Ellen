package sk.tuke.kpi.oop.game.other;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;

public class Ventilator extends AbstractActor implements Repairable {
    private Animation animation;

    public Ventilator() {
        animation = new Animation("sprites/ventilator.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(animation);
        animation.pause();
    }

    public static final Topic<Ventilator> VENTILATOR_REPAIRED = Topic.create("ventilator repaired", Ventilator.class);

    @Override
    public boolean repair() {
        animation.play();
        this.getScene().getMessageBus().publish(VENTILATOR_REPAIRED, this);
        return true;
    }
}
