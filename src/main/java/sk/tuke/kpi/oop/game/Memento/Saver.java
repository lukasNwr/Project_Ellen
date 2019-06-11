package sk.tuke.kpi.oop.game.Memento;


import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Ace;
import sk.tuke.kpi.oop.game.items.Usable;

public class Saver extends AbstractActor implements Usable<Actor> {

    private boolean off;

    public Saver() {
        Animation animation = new Animation("sprites/computer.png", 80, 48, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(animation);
        this.off = true;
    }

    public void useAce() {

        this.off = false;

        Ripley ripley = this.getScene().getActors().stream()
            .filter(this::intersects)
            .filter(Ripley.class::isInstance)
            .map(Ripley.class::cast)
            .findFirst()
            .orElse(null);

        Caretaker caretaker = new Caretaker();
        Originator originator = new Originator(ripley);
        caretaker.addMemento(originator.save());
        this.getScene().getMessageBus().subscribe(
            Ripley.RIPLEY_RESET, a -> {
                originator.restore(caretaker.getMemento());
            }
        );
        ripley.save();
    }



    @Override
    public void useWith(Actor actor) {

        if(!off && actor instanceof Ace) {
            this.useAce();
        }
    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }
}
