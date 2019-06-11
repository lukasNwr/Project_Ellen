package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.ActorContainer;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.other.Direction;
import sk.tuke.kpi.oop.game.other.Keeper;
import sk.tuke.kpi.oop.game.other.Movable;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.items.Collectible;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;

public class Ripley extends AbstractActor implements Movable, Keeper<Collectible>, Alive, Armed {
    private int speed;
    private Animation animationAlive;
    private Animation animationDie;;
    private Backpack<Collectible> backpack;
    private Health health;
    private Firearm weapon;
    private int wallet;
    private boolean reset;
    private boolean saved;
    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("ripley died", Ripley.class);
    public static final Topic<Ripley> RIPLEY_RESET = Topic.create("ripley reset", Ripley.class);

    public Ripley() {
        super();
        animationAlive = new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        animationDie = new Animation("sprites/player_die.png", 32, 32, 0.1f, Animation.PlayMode.ONCE);
        setAnimation(animationAlive);
        animationAlive.stop();
        speed = 2;
        this.backpack = new Backpack<>("Ferko Ruksacik", 10);
        this.health = new Health(100);
        this.reset = true;
        this.saved = false;
        this.health.onExhaustion(() -> {
            //reakcia na vycerpanie zdravia
            this.lowerEnergy();
        });
        this.weapon = new Gun(10,200);
        this.wallet = 0;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int amount) {
        this.wallet += amount;
    }

    public void lowerEnergy() {
        if(this.health.getValue() > 0 && this.health.getValue() <= 100)  this.health.drain(1);
        if(this.health.getValue() < 0) this.health.exhaust();
        if(this.health.getValue() == 0) {
            setAnimation(animationDie);
            if(reset && saved) {
                this.getScene().getMessageBus().publish(RIPLEY_RESET, this);
                reset = false;
            } else {
                this.getScene().getMessageBus().publish(RIPLEY_DIED, this);
            }
        }
    }

    public void save() {
        saved = true;
    }

    public Disposable contaminate() {
            return new Loop<>(
                new ActionSequence<>(
                    new Invoke<>(this::lowerEnergy),
                    new Wait<>(1)
                )
            ).scheduleOn(this);
    }

    public void showRipleyState() {
        // nastavenie UI
        Scene scene = this.getScene();
        int windowHeight = scene.getGame().getWindowSetup().getHeight();
        int topOffset = GameApplication.STATUS_LINE_OFFSET;
        int yTextPos = windowHeight - topOffset;
        scene.getGame().getOverlay().drawText("Energy: " + this.health.getValue(),  110, yTextPos);
        scene.getGame().getOverlay().drawText("Ammo: " + this.weapon.getAmmo(), 250, yTextPos);
        scene.getGame().getOverlay().drawText("Money: " + this.getWallet(), 390, yTextPos);
    }

    @Override
    public ActorContainer<Collectible> getContainer() {
        return this.backpack;
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }

    @Override
    public void startedMoving(Direction direction) {
        animationAlive.setRotation(direction.getAngle());
        animationAlive.play();
    }

    @Override
    public void stoppedMoving() {
        animationAlive.stop();
    }

    @Override
    public Health getHealth() {
        return this.health;
    }

    @Override
    public Firearm getFirearm() {
        return this.weapon;
    }

    @Override
    public void setFirearm(Firearm weapon) {
        this.weapon = weapon;
    }

    public void resetAnimation() {
        setAnimation(animationAlive);
    }

}

