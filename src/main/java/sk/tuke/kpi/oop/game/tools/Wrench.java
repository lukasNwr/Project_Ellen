package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;

public class Wrench extends BreakableTool {
    private Animation animation;

    public Wrench() {
        super();
        animation = new Animation("sprites/wrench.png", 16, 16);
        setAnimation(animation);
        setRemainingUses(2);
    }

    public int getUses() {
        return this.getRemainingUses();
    }

    @Override
    public void useWith(Reactor reactor) {
        super.useWith(reactor);
    }
}
