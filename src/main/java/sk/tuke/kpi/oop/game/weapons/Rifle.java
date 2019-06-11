package sk.tuke.kpi.oop.game.weapons;

public class Rifle extends Firearm {

    public Rifle(int maxAmmo) {
        super(maxAmmo);
    }

    public Rifle(int ammo, int maxAmmo) {
        super(ammo, maxAmmo);
    }

    @Override
    protected Fireable createBullet() {
        return new Bullet();
    }
}
