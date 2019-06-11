package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.ActorFactory;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.oop.game.other.Locker;
import sk.tuke.kpi.oop.game.other.Ventilator;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

public class MissionImpossible implements SceneListener {
    public static class Factory implements ActorFactory {
        @Nullable
        @Override
        public Actor create(@Nullable String type, @Nullable String name) {
            switch(name) {
                case "ellen": return new Ripley();
                case "energy": return new Energy();
                case "door": return new LockedDoor("Locked door", Door.Orientation.VERTICAL);
                case "access card": return new AccessCard();
                case "ventilator": return new Ventilator();
                case "locker": return new Locker();
                default: return null;
            }
        }
    }
}
