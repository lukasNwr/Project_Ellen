package sk.tuke.kpi.oop.game.weapons;

import sk.tuke.kpi.oop.game.behaviours.Aimed;

public class Bazooka extends  Firearm {

    public Bazooka(int maxAmmo) {
        super(maxAmmo);
    }

    public Bazooka(int ammo, int maxAmmo) {
        super(ammo, maxAmmo);
    }

    @Override
    protected Fireable createBullet() {
        return new Rocket(new Aimed());
    }
}
