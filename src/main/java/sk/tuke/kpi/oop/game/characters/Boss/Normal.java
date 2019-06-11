package sk.tuke.kpi.oop.game.characters.Boss;

import sk.tuke.kpi.oop.game.characters.Boss.Boss;
import sk.tuke.kpi.oop.game.characters.Boss.BossState;

public class Normal implements BossState {

    private Boss boss;

    public Normal(Boss boss) {
        this.boss = boss;
    }

    @Override
    public void execute() {
        boss.setBonusDamage(10);
    }
}
