package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.concurrent.atomic.AtomicInteger;

public class GreenSwitch extends AbstractActor implements Usable<Ripley> {
    public static final Topic<GreenSwitch> GREEN_PRESSED = Topic.create("green pressed", GreenSwitch.class);
    public static final Topic<GreenSwitch> SECRET_CODE = Topic.create("secret code", GreenSwitch.class);

    public GreenSwitch() {
        Animation animation = new Animation("sprites/button_green.png", 16, 16);
        setAnimation(animation);
    }

    public void addGreenCode(AtomicInteger code, AtomicInteger codeCount) {
        if(codeCount.get() < 3) {
            code.addAndGet(5);
            codeCount.getAndIncrement();
        } else {
            codeCount.set(0);
            code.set(0);
        }
        if(code.get() == 13) {
            this.getScene().getMessageBus().publish(SECRET_CODE, this);
        }
        System.out.println(code);
        System.out.println(codeCount);
    }

    @Override
    public void useWith(Ripley actor) {
        this.getScene().getMessageBus().publish(GREEN_PRESSED, this);
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }
}
