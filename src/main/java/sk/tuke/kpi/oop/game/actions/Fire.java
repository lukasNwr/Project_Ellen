package sk.tuke.kpi.oop.game.actions;


import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.other.Direction;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.weapons.Fireable;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Rifle;
import sk.tuke.kpi.oop.game.weapons.Shotgun;

public class Fire<T extends Armed> extends AbstractAction<T> {
    private boolean fired;

    public Fire() {
        this.fired = false;
    }

    @Override
    public void execute(float deltaTime) {
        T actor = getActor();
        if(actor == null) {
            setDone(true);
            return;
        }
        Firearm weapon = actor.getFirearm();
        if(weapon.getAmmo() == 0) {
            setDone(true);
            return;
        }
        if(!fired) {
            if(weapon instanceof Rifle) {
                new ActionSequence<>(
                    new Invoke<>(() -> {
                        Fireable bullet1 = actor.getFirearm().fire();
                        Direction direction = Direction.fromAngle(actor.getAnimation().getRotation());
                        actor.getScene().addActor(bullet1, actor.getPosX() + 8, actor.getPosY() + 8);
                        new Loop<>(
                            new Move<>(direction)
                        ).scheduleOn(bullet1);
                    }),
                    new Wait<>(0.2f),
                    new Invoke<>(() -> {
                        Fireable bullet2 = actor.getFirearm().fire();
                        Direction direction = Direction.fromAngle(actor.getAnimation().getRotation());
                        actor.getScene().addActor(bullet2, actor.getPosX() + 8, actor.getPosY() + 8);
                        new Loop<>(
                            new Move<>(direction)
                        ).scheduleOn(bullet2);
                    }),
                    new Wait<>(0.2f),
                    new Invoke<>(() -> {
                        Fireable bullet2 = actor.getFirearm().fire();
                        Direction direction = Direction.fromAngle(actor.getAnimation().getRotation());
                        actor.getScene().addActor(bullet2, actor.getPosX() + 8, actor.getPosY() + 8);
                        new Loop<>(
                            new Move<>(direction)
                        ).scheduleOn(bullet2);
                    })
                ).scheduleOn(actor);
                fired = true;
                this.setDone(true);
                return;
            }
            if(weapon instanceof Shotgun) {
                new ActionSequence<>(
                    new Invoke<>(() -> {
                        Fireable bullet2 = actor.getFirearm().fire();
                        Direction direction = Direction.fromAngle(actor.getAnimation().getRotation());
                        actor.getScene().addActor(bullet2, actor.getPosX(), actor.getPosY());
                        new Loop<>(
                            new Move<>(direction)
                        ).scheduleOn(bullet2);
                    }),
                    new Invoke<>(() -> {
                        Fireable bullet2 = actor.getFirearm().fire();
                        Direction direction = Direction.fromAngle(actor.getAnimation().getRotation());
                        actor.getScene().addActor(bullet2, actor.getPosX() + 8, actor.getPosY() + 8);
                        new Loop<>(
                            new Move<>(direction)
                        ).scheduleOn(bullet2);
                    })
                ).scheduleOn(actor);
                fired = true;
                this.setDone(true);
                return;
            }
            Fireable bullet = actor.getFirearm().fire();
            Direction direction = Direction.fromAngle(actor.getAnimation().getRotation());
            actor.getScene().addActor(bullet, actor.getPosX()  + 8,actor.getPosY() + 8);
            new Loop<>(
                new Move<>(direction)
            ).scheduleOn(bullet);
            fired = true;
        }
        this.setDone(true);
    }
}
