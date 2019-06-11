package sk.tuke.kpi.oop.game.actions;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.other.Keeper;


public class Take<T extends Actor> extends AbstractAction<Keeper<T>> {

    private Class<T> takeableActorsClass;

    public Take(Class<T> takeableActorsClass) {
        this.takeableActorsClass = takeableActorsClass;
    }

    @Override
    public void execute(float deltaTime) {
        Keeper<T> keeper = getActor();
        if (keeper == null) {
            this.setDone(true);
            return;
        }
        Scene scene = keeper.getScene();
        T actor = scene.getActors().stream()
            .filter(keeper::intersects)
            .filter(takeableActorsClass::isInstance)
            .map(takeableActorsClass::cast)
            .findFirst()
            .orElse(null);

        if (actor == null) {
            this.setDone(true);
            return;
        }
        if(keeper.getContainer().getSize() == keeper.getContainer().getCapacity()) {
            this.setDone(true);
            return;
        }
        try {
            keeper.getContainer().add(actor);
            scene.removeActor(actor);
        } catch (IllegalStateException e){
            System.out.println("Cannot add another item, backpack is full!");
        }
        this.setDone(true);
    }
}
