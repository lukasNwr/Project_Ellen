package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.other.Direction;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

public class AlienMother extends Alien {
    private Health health;
    private Animation animation;
    public static final Topic<AlienMother> MOTHER_DIED = Topic.create("mother died", AlienMother.class);

    public AlienMother(int healthValue, Behaviour<? super Alien> behaviour) {
        super(healthValue, behaviour);
        this.health = new Health(healthValue);
        animation = new Animation("sprites/mother.png", 112, 162, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(animation);
        this.getHealth().onExhaustion(() -> {
                this.getScene().getMessageBus().publish(MOTHER_DIED, this);
            }
        );
    }

    public AlienMother() {
        super();
        this.health = new Health(200);
        animation = new Animation("sprites/mother.png", 112, 162, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(animation);
    }


    @Override
    public void startedMoving(Direction direction) {
        animation.setRotation(direction.getAngle());
        animation.play();
    }

    @Override
    public void stoppedMoving() {
        animation.stop();
    }

    @Override
    public Health getHealth() {
        return health;
    }
}
