package sk.tuke.kpi.oop.game.other;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.concurrent.atomic.AtomicInteger;

public class RedSwitch extends AbstractActor implements Usable<Ripley> {
    public static final Topic<RedSwitch> RED_PRESSED = Topic.create("red pressed", RedSwitch.class);
    public static final Topic<RedSwitch> SECRET_CODE = Topic.create("secret code", RedSwitch.class);

    public RedSwitch() {
        Animation animation = new Animation("sprites/button_red.png", 16, 16);
        setAnimation(animation);
    }


    @Override
    public void useWith(Ripley actor) {
        this.getScene().getMessageBus().publish(RED_PRESSED, this);
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }

    public void addRedCode(AtomicInteger code, AtomicInteger codeCount) {
        if(codeCount.get() < 3) {
            code.addAndGet(3);
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
}
