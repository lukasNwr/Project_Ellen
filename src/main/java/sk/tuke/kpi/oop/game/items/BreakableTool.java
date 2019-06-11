package sk.tuke.kpi.oop.game.items;


import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;

abstract class BreakableTool <T extends Actor> extends AbstractActor implements Usable<T>{
    private int remainingUses;
//    private Actor tool;

    public int getRemainingUses() {
        return remainingUses;
    }

    public void setRemainingUses(int remainingUses) {
        this.remainingUses = remainingUses;
    }

    @Override
    public void useWith(T actor) {
        remainingUses--;
        if(remainingUses == 0) {
            this.getScene().removeActor(this);
        }
    }
}
