package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.other.Repairable;

public class Hammer extends BreakableTool<Repairable> implements Collectible {
    public static final int RepairValue = 50;

    public Hammer() {
        super();
        Animation animation;
        animation = new Animation("sprites/hammer.png", 16, 16);
        setAnimation(animation);
        setRemainingUses(2);
    }

    public int getUses() {
        return this.getRemainingUses();
    }

    @Override
    public void useWith(Repairable repairable) {
        repairable.repair();
        super.useWith(repairable);
    }

    @Override
    public Class<Repairable> getUsingActorClass() {
        return Repairable.class;
    }
}
