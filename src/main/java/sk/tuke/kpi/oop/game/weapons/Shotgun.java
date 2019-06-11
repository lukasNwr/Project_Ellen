package sk.tuke.kpi.oop.game.weapons;

public class Shotgun extends Firearm {

    public Shotgun(int maxAmmo) {
        super(maxAmmo);
    }

    public Shotgun(int ammo, int maxAmmo) {
        super(ammo ,maxAmmo);
    }

    @Override
    protected Fireable createBullet() {
        return new BulletDouble();
    }
}
