package sk.tuke.kpi.oop.game.Memento;

import sk.tuke.kpi.gamelib.graphics.Point;
import sk.tuke.kpi.oop.game.weapons.Firearm;

public class Memento {

    private Point position;
    private int health;
    private int ammo;
    private int money;
    private Firearm firearm;

    public Memento(Point position, int health, int ammo,
                   int money, Firearm firearm) {
        this.position = position;
        this.health = health;
        this.ammo = ammo;
        this.money = money;
        this.firearm = firearm;
    }

    public Point getPosition() {
        return position;
    }

    public int getHealth() {
        return health;
    }

    public int getAmmo() {
        return ammo;
    }

    public int getMoney() {
        return money;
    }

    public Firearm getFirearm() {
        return firearm;
    }
}
