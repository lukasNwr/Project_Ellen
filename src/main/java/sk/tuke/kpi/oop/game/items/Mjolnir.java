package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.oop.game.other.Repairable;

public class Mjolnir extends Hammer {
    public Mjolnir() {
        super();
        this.setRemainingUses(4);
    }

    @Override
    public void useWith(Repairable actor) {
        super.useWith(actor);
    }
}
