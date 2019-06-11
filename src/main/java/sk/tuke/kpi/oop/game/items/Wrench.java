package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.other.DefectiveLight;

public class Wrench extends BreakableTool <DefectiveLight> implements  Collectible{
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
    public void useWith(DefectiveLight light) {
        super.useWith(light);
    }

    @Override
    public Class<DefectiveLight> getUsingActorClass() {
        return DefectiveLight.class;
    }
}
