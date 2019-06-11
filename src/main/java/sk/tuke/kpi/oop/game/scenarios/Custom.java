package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.graphics.Color;
import sk.tuke.kpi.oop.game.other.GreenSwitch;
import sk.tuke.kpi.oop.game.other.Locker;
import sk.tuke.kpi.oop.game.Memento.Saver;
import sk.tuke.kpi.oop.game.Prototype.Money;
import sk.tuke.kpi.oop.game.Prototype.MoneyFactory;
import sk.tuke.kpi.oop.game.Prototype.MoneyType;
import sk.tuke.kpi.oop.game.other.RedSwitch;
import sk.tuke.kpi.oop.game.other.Store;
import sk.tuke.kpi.oop.game.behaviours.Angry;
import sk.tuke.kpi.oop.game.behaviours.Observing;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.AlienEgg;
import sk.tuke.kpi.oop.game.characters.AlienMother;
import sk.tuke.kpi.oop.game.characters.Boss.Boss;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.CollectorController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.*;
import sk.tuke.kpi.oop.game.openables.DamagedDoor;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.LockedDoor;
import sk.tuke.kpi.oop.game.openables.SecretDoor;
import sk.tuke.kpi.oop.game.weapons.Bazooka;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Custom implements SceneListener {

    public static  class Factory implements  ActorFactory {
        private MoneyFactory moneyFactory = new MoneyFactory();
        @Nullable
        @Override
        public Actor create(@Nullable String type, @Nullable String name) {
            switch(name) {
                case "ellen": return new Ripley();
                case "energy": return new Energy();
                case "ammo": return new Ammo();
                case "coin":  return moneyFactory.createMoney(MoneyType.COIN);
                case "store": return new Store();
                case "bill": return moneyFactory.createMoney(MoneyType.BILL);
                case "green button": return new GreenSwitch();
                case "red button": return new RedSwitch();
                case "egg": return new AlienEgg();
                case "dead body": return new DeadBody();
                case "lift to heaven": return new LiftToHome();
                case "ace": return new Ace();
                case "access card": return new AccessCard();
                case "door zone1": return new Door("door zone1", Door.Orientation.HORIZONTAL);
                case "door zone2": return new Door("door zone2", Door.Orientation.HORIZONTAL);
                case "door": return new Door("door", Door.Orientation.HORIZONTAL);
                case "locked door": return new LockedDoor("locked door", Door.Orientation.VERTICAL);
                case "damaged door": return new DamagedDoor("damaged door", Door.Orientation.VERTICAL);
                case "secret door": return new SecretDoor("secret door", Door.Orientation.VERTICAL);
                case "locker": return new Locker();
                case "saver": return new Saver();
                case "alien mother": return new AlienMother(150,
                    new Observing<>(
                        Door.DOOR_OPENED,
                        actor -> actor.getName().equals("door zone2"),
                        new RandomlyMoving()
                    )
                );
                case "boss": return new Boss(250,
                    new Observing<>(
                        Door.DOOR_OPENED,
                        actor -> actor.getName().equals("damaged door"),
                        new Angry()
                    )
                );

                default:
                    switch (type) {
                        case "running": return new Alien(100, new RandomlyMoving());
                        case "waiting zone1": return new Alien(100,
                            new Observing<>(
                                Door.DOOR_OPENED,
                                actor -> actor.getName().equals("door zone1"),
                                new RandomlyMoving()
                            )
                        );
                        case "waiting zone2": return new Alien(100,
                            new Observing<>(
                                Door.DOOR_OPENED,
                                actor -> actor.getName().equals("damaged door"),
                                new RandomlyMoving()
                            )
                        );
                        case "waiting zone3": return new Alien(100,
                            new Observing<>(
                                Door.DOOR_OPENED,
                                actor -> actor.getName().equals("damaged door"),
                                new RandomlyMoving()
                            )
                        );
                        default: return null;
                }
            }
        }
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        Ripley ripley = scene.getFirstActorByType(Ripley.class);
        ripley.showRipleyState();
        scene.follow(ripley);
    }

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        Ripley ripley = scene.getFirstActorByType(Ripley.class);
        MoneyFactory moneyFactory = new MoneyFactory();
        Bazooka bazooka = new Bazooka(50);
        GreenSwitch green = scene.getFirstActorByType(GreenSwitch.class);
        RedSwitch red = scene.getFirstActorByType(RedSwitch.class);

        AtomicInteger codeCount = new AtomicInteger(0);
        AtomicInteger secretCode = new AtomicInteger(0);

        Disposable collectorController = scene.getInput().registerListener(new CollectorController(ripley));
        Disposable movableController = scene.getInput().registerListener(new MovableController(ripley));
        Disposable shooterController = scene.getInput().registerListener(new ShooterController(ripley));

        scene.getMessageBus().subscribe(GreenSwitch.GREEN_PRESSED, x -> {
            green.addGreenCode(secretCode, codeCount);
            System.out.println("Green pressed");
        });
        scene.getMessageBus().subscribe(RedSwitch.RED_PRESSED, x -> {
            red.addRedCode(secretCode, codeCount);
            System.out.println("Red pressed");
        });
        scene.getMessageBus().subscribeOnce(AlienMother.MOTHER_DIED, x -> ripley.setFirearm(bazooka));
        scene.getMessageBus().subscribe(Boss.BOSS_DEAD, a -> {
            System.out.println("BOSS IS DEAD YOU WON");
            scene.getOverlay().drawRectangle(0, 0,
                scene.getGame().getWindowSetup().getWidth() + 50
                , scene.getGame().getWindowSetup().getHeight()+50
                , Color.BLACK).showFor(1000);
            scene.getOverlay().drawText("BOSS IS DEAD, YOU WON!", ripley.getPosX()-100,
                ripley.getPosY()+50).showFor(1000);
            movableController.dispose();
            collectorController.dispose();
            shooterController.dispose();

        });
        scene.getMessageBus().subscribe(LiftToHome.LIFT_ACTIVATED, a -> {
            scene.getOverlay().drawRectangle(0, 0,
                scene.getGame().getWindowSetup().getWidth()+50,
                scene.getGame().getWindowSetup().getHeight()+50
                , Color.BLACK).showFor(1000);
            scene.getOverlay().drawText("You activated teleport to home,\n YOU WON!", ripley.getPosX()-150,
                ripley.getPosY()+50).showFor(1000);
            movableController.dispose();
            collectorController.dispose();
            shooterController.dispose();
        });



        List<Money> money = scene.getActors().stream()
            .filter(Money.class::isInstance)
            .map(Money.class::cast)
            .collect(Collectors.toList());
        for(Money m : money) {
            new When<>(
                (action) -> ripley.intersects(m),
                new Invoke<>(() -> m.useWith(ripley))).scheduleOn(ripley);
        }

        List<Energy> energy = scene.getActors().stream()
            .filter(Energy.class::isInstance)
            .map(Energy.class::cast)
            .collect(Collectors.toList());
        for(Energy e : energy) {
            new When<>(
                (action) -> ripley.intersects(e),
                new Invoke<>(() -> e.useWith(ripley))).scheduleOn(ripley);
        }

        List<Ammo> ammo = scene.getActors().stream()
            .filter(Ammo.class::isInstance)
            .map(Ammo.class::cast)
            .collect(Collectors.toList());
        for(Ammo a : ammo) {
            new When<>(
                (action) -> ripley.intersects(a),
                new Invoke<>(() -> a.useWith(ripley))).scheduleOn(ripley);
        }

        scene.getGame().pushActorContainer(ripley.getContainer());

        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, x -> {
            movableController.dispose();
            collectorController.dispose();
            shooterController.dispose();
            scene.getOverlay().drawText("YOU DIED, GAME OVER!", ripley.getPosX() - 100,
                ripley.getPosY()+50).showFor(1000);
        });

    }
}
