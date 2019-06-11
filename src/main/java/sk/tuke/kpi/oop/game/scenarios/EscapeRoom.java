package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.ActorFactory;
import sk.tuke.kpi.gamelib.World;
import sk.tuke.kpi.oop.game.Prototype.MoneyFactory;
import sk.tuke.kpi.oop.game.Prototype.MoneyType;
import sk.tuke.kpi.oop.game.behaviours.Observing;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.AlienMother;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

public class EscapeRoom {
    public static class Factory implements ActorFactory {
        private MoneyFactory moneyFactory = new MoneyFactory();
        @Nullable
        @Override
        public Actor create(@Nullable String type, @Nullable String name) {
            switch(name) {
                case "ellen": return new Ripley();
                case "energy": return new Energy();
                case "ammo": return new Ammo();
                case "alien mother": return new AlienMother();
                case "coin": return moneyFactory.createMoney(MoneyType.COIN);

                default: switch (type) {
                    case "running": return new Alien(100, new RandomlyMoving());
                    case "waiting1": {
                        return new Alien(100,
                            new Observing<>(
                                Door.DOOR_OPENED,
                                actor -> actor.getName().equals("front door"),
                                new RandomlyMoving()
                            )
                        );
                    }
                    case "waiting2":
                        return new Alien(100,
                            new Observing<>(
                                Door.DOOR_OPENED,
                                actor -> actor.getName().equals("back door"),
                                new RandomlyMoving()
                            )
                    );
                    case "horizontal": return new Door("back door", Door.Orientation.HORIZONTAL);
                    case "vertical": return new LockedDoor("front door", Door.Orientation.VERTICAL);
                    default: return null;
                }
            }

        }
    }
}
