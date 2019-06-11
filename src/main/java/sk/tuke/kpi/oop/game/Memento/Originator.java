package sk.tuke.kpi.oop.game.Memento;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Point;
import sk.tuke.kpi.oop.game.characters.Health;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.weapons.Firearm;

public class Originator {

    private Ripley ripley;
    private Point position;
    private int health;
    private int ammo;
    private int money;
    private Firearm firearm;

    public Originator(Ripley ripley) {
        this.ripley = ripley;
        this.position = ripley.getPosition();
        this.health = ripley.getHealth().getValue();
        this.ammo = ripley.getFirearm().getAmmo();
        this.money = ripley.getWallet();
        this.firearm = ripley.getFirearm();
    }

    public Memento save() {
        ripley.getScene().getOverlay().drawText("Saved!", ripley.getPosX() - 50, ripley.getPosY() +50);
        return new Memento(position, health, ammo, money, firearm);
    }

    public void restore(Memento m) {
        Memento memento = m;
        this.position = memento.getPosition();
        ripley.setPosition(position.getX(), position.getY());
        ripley.setWallet(memento.getMoney());
        ripley.getFirearm().reload(memento.getAmmo());
        ripley.setFirearm(memento.getFirearm());
        ripley.getHealth().refill(memento.getHealth());
        ripley.resetAnimation();
    }

}
