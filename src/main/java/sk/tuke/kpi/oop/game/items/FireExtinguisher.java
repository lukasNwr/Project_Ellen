package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.other.Reactor;
//import sk.tuke.kpi.oop.game.items.BreakableTool;

public class FireExtinguisher extends BreakableTool <Reactor> implements  Collectible{
//    private Animation animation;


    public FireExtinguisher() {
        super();
        Animation animation;
        animation = new Animation("sprites/extinguisher.png", 16,16);
        setAnimation(animation);
        setRemainingUses(1);
    }

    public int getUses() {
        return this.getRemainingUses();
    }

    @Override
    public void useWith(Reactor reactor) {
        super.useWith(reactor);
    }

    @Override
    public Class<Reactor> getUsingActorClass() {
        return Reactor.class;
    }
}
