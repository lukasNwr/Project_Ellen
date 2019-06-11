package sk.tuke.kpi.oop.game.openables;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.oop.game.items.AccessCard;

public class LockedDoor extends Door {
    private boolean isLocked;

    public LockedDoor(String name, Orientation orientation) {
        super(name, orientation);
        this.isLocked = true;
    }

    public void Lock() {
        isLocked = true;
        super.close();
    }

    public void unlock() {
        isLocked = false;
        super.open();
    }

    public boolean isLocked() {
        return this.isLocked;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        this.Lock();
    }

    @Override
    public void useWith(Actor actor) {
        if(this.isLocked && actor instanceof AccessCard) {
            this.unlock();
            return;
        }
        if(!this.isLocked) {
            super.useWith(actor);
        }
    }
}
