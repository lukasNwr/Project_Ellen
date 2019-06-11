package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.other.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

public class Shift extends AbstractAction <Keeper<Collectible>>{

    public Shift() {
    }

    @Override
    public void execute(float deltaTime) {
        Keeper<Collectible> actor = getActor();

        if(actor == null) {
            this.setDone(true);
            return;
        }
        actor.getContainer().shift();
        this.setDone(true);
    }
}
