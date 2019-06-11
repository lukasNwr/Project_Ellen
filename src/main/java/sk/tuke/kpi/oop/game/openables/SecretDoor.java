package sk.tuke.kpi.oop.game.openables;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.other.GreenSwitch;
import sk.tuke.kpi.oop.game.other.RedSwitch;

public class SecretDoor extends Door {
    public SecretDoor(String name, Orientation orientation) {
        super(name, orientation);
    }

    @Override
    public void useWith(Actor actor) {
        this.getScene().getOverlay().drawText("LOCKED!", actor.getPosX()-50, actor.getPosY()+50).showFor(2);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(
            new Invoke<>(() -> {
                scene.getMessageBus().subscribeOnce(GreenSwitch.SECRET_CODE, x -> super.open());
                scene.getMessageBus().subscribeOnce(RedSwitch.SECRET_CODE, x -> super.open());
            })
        ).scheduleOn(this);
    }
}
