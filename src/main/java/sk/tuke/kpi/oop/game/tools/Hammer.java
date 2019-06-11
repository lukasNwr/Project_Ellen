package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;

public class Hammer extends BreakableTool{
    public static final int RepairValue = 50;

    public Hammer() {
        super();
        Animation animation;
        animation = new Animation("sprites/hammer.png", 16, 16);
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
}
