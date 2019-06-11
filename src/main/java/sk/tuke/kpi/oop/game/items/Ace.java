package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Memento.Saver;

public class Ace extends BreakableTool<Saver> implements Collectible, Usable<Saver> {

    public Ace() {
        super();
        Animation animation = new Animation("sprites/ace.png", 16, 16);
        setAnimation(animation);
        setRemainingUses(1);
    }


    @Override
    public void useWith(Saver saver) {
        saver.useAce();
        super.useWith(saver);
    }

    @Override
    public Class<Saver> getUsingActorClass() {
        return Saver.class;
    }
}
