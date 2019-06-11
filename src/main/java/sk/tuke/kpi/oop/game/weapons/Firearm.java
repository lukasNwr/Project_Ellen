package sk.tuke.kpi.oop.game.weapons;

public abstract class Firearm {
    private int ammo;
    private int maxAmmo;

    public Firearm(int maxAmmo) {
        this.maxAmmo = maxAmmo;
        this.ammo = maxAmmo;
    }

    public Firearm(int ammo, int maxAmmo) {
        this.ammo = ammo;
        this.maxAmmo = maxAmmo;
    }

    public Fireable fire() {
        if(ammo == 0) return null;
        ammo--;
        return createBullet();
    }

    protected abstract Fireable createBullet();

    public int getAmmo() {
        return ammo;
    }

    public void reload(int newAmmo) {
       ammo = newAmmo;
       if(ammo >= maxAmmo) ammo = maxAmmo;
    }
}
