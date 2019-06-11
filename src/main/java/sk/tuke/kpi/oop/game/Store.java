package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Facade.BuyWeaponFacade;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Usable;

public class Store extends AbstractActor implements Usable<Ripley> {
    private BuyWeaponFacade buyWeaponFacade;

    public Store() {
        Animation animation = new Animation("sprites/store.png", 47, 18, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(animation);
    }

    @Override
    public void useWith(Ripley actor) {
        buyWeaponFacade = new BuyWeaponFacade(actor);
        buyWeaponFacade.buyWeapon();
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }
}
