package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.oop.game.Reactor;

public class Mjolnir extends Hammer {
    public Mjolnir() {
        super();
        this.setRemainingUses(4);
    }

    public void useWith(Reactor reactor) {
        super.useWith(reactor);
    }
}
