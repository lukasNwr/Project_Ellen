package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class TimeBomb extends AbstractActor {
    private float remainingT;
    private Animation activAnimation;
    private Animation exploAnimation;
    private boolean isActivated;


    public TimeBomb(float remainingT) {
        this.remainingT = remainingT;
        Animation normalAnimation;
        normalAnimation = new Animation("sprites/bomb.png", 16, 16);
        activAnimation = new Animation("sprites/bomb_activated.png", 16, 16, 0.2f, Animation.PlayMode.LOOP);
        exploAnimation = new Animation("sprites/small_explosion.png", 32, 16, 0.2f, Animation.PlayMode.ONCE);
        setAnimation(normalAnimation);
    }

    private boolean counter() {
        while(remainingT != 0) {
            remainingT--;
        }
        if(remainingT == 0) return true;
        return false;
    }

    public void activate() {
        isActivated = true;
        setAnimation(activAnimation);
        new Loop<>(new Invoke<>(this::counter)).scheduleOn(this);
        if(remainingT == 0) {
            setAnimation(exploAnimation);
            getScene().removeActor(this);
        }
        new Loop<>(new Invoke<>(this::counter)).scheduleOn(this).dispose();
    }



    public boolean isActivated() {
        if (isActivated) return true;
        else return false;
    }

}
