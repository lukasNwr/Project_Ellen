package sk.tuke.kpi.oop.game.Prototype;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Usable;

public class Money extends AbstractActor implements Prototype, Usable<Ripley> {
    private MoneyType type;
    private Animation animation;
    private int value;

    public Money(MoneyType type, int value) {
        this.type = type;
        if(type == MoneyType.COIN) {
            this.animation = new Animation("sprites/coin.png", 16, 16, 1, Animation.PlayMode.LOOP_PINGPONG);
            setAnimation(animation);
        } else {
            this.animation = new Animation("sprites/money.png", 16, 16);
            setAnimation(animation);
        }
        this.value = value;
    }

    @Override
    public Prototype createClone() {
        Money clonedMoney = new Money(this.type, this.value);
        return clonedMoney;
    }

    @Override
    public void useWith(Ripley actor) {
        actor.setWallet(value);
        this.getScene().removeActor(this);
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }
}
