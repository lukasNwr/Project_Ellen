package sk.tuke.kpi.oop.game.characters.Boss;

public class Empowered implements BossState {

    private Boss boss;

    public Empowered(Boss boss) {
        this.boss = boss;
        this.boss.setBonusDamage(20);
        this.boss.getHealth().refill(150);
        this.boss.setSpeed(2);
    }

    @Override
    public void execute() {
        boss.setReadyToDie(true);
    }
}
