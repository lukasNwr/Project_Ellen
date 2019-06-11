package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class DeadBody extends AbstractActor implements Usable<Ripley> {
    public DeadBody() {
        Animation animation = new Animation("sprites/body.png", 64, 48);
        setAnimation(animation);
    }

    @Override
    public void useWith(Ripley actor) {
        actor.setWallet(10);
        actor.getFirearm().reload(10);
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }
}
