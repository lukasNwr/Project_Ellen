package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.other.Keeper;

public class Drop<T extends Actor> extends AbstractAction<Keeper<T>> {
    public Drop() {
    }

    @Override
    public void execute(float deltaTime) {
        Keeper<T> actor;
        actor = this.getActor();
        if(actor == null) {
            setDone(true);
            return;
        }
        if(actor.getContainer().peek() == null) {
            setDone(true);
            return;
        }
        T item = actor.getContainer().peek();
        int x = this.getActor().getPosX();
        int y = this.getActor().getPosY();
        if(item == null) System.out.println("item null");
        actor.getScene().addActor(item, x, y);
        actor.getContainer().remove(item);
        this.setDone(true);
    }

}
