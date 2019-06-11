package sk.tuke.kpi.oop.game.other;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Hammer;
import sk.tuke.kpi.oop.game.items.Usable;

public class Locker extends AbstractActor implements Usable <Ripley>{
    private boolean wasUsed;
    public Locker() {
        Animation animation = new Animation("sprites/locker.png", 16, 16);
        setAnimation(animation);
        wasUsed = false;
    }

    @Override
    public void useWith(Ripley ripley) {
        Hammer hammer = new Hammer();
        if(wasUsed == false) {
            ripley.getContainer().add(hammer);
        }
        wasUsed = true;
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }
}
