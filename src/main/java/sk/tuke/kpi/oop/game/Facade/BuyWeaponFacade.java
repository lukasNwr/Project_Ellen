package sk.tuke.kpi.oop.game.Facade;

import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Rifle;
import sk.tuke.kpi.oop.game.weapons.Shotgun;

public class BuyWeaponFacade {
    private Ripley ripley;
    private Firearm weapon;

    public BuyWeaponFacade(Ripley ripley) {
        this.ripley = ripley;

    }

    public void buyWeapon() {
        if (ripley.getWallet() >= 50) {
            ripley.setWallet(-50);
            weapon = new Rifle(150);
            ripley.getScene().getOverlay().drawText("Rifle bought!", ripley.getPosX() - 50,
                ripley.getPosY() + 50).showFor(1);
            ripley.setFirearm(weapon);
        } else if (ripley.getWallet() >= 30) {
            ripley.setWallet(-30);
            weapon = new Shotgun(50);
            ripley.getScene().getOverlay().drawText("Shotgun bought!", ripley.getPosX() - 50,
                ripley.getPosY() + 50).showFor(1);
            ripley.setFirearm(weapon);
        } else return;
    }

}
