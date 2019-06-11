package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.oop.game.Reactor;

abstract class BreakableTool extends AbstractActor implements Usable<Reactor> {
    private int remainingUses;

    public int getRemainingUses() {
        return remainingUses;
    }

    public void setRemainingUses(int remainingUses) {
        this.remainingUses = remainingUses;
    }

    @Override
    public void useWith(Reactor reactor) {
        remainingUses--;
        if(remainingUses== 0) {
            getScene().removeActor(this);
        }
    }


}
