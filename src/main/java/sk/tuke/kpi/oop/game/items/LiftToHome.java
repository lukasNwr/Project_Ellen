package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class LiftToHome extends AbstractActor {
    public static final Topic<LiftToHome> LIFT_ACTIVATED = Topic.create("lift activated", LiftToHome.class);
    private boolean messagePublished;

    public LiftToHome() {
        Animation animation = new Animation("sprites/lift.png", 48, 48);
        animation.setAlpha(200);
        setAnimation(animation);
        messagePublished = false;
    }

    public void winGame() {
        Ripley ripley = this.getScene().getActors().stream()
            .filter(Ripley.class::isInstance)
            .map(Ripley.class::cast)
            .findFirst()
            .orElse(null);

        if(ripley == null) return;

        if(this.intersects(ripley)) {
            if(!messagePublished) {
                this.getScene().getMessageBus().publish(LIFT_ACTIVATED, this);
                messagePublished = true;
            }
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::winGame)).scheduleOn(this);
    }
}
