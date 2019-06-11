package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.actions.Wait;

public class DefectiveLight extends Light implements Repairable{


    public DefectiveLight() {
        super();
    }

    private void genRandomNum() {
        int randomNum;
        randomNum = 1 + (int) (Math.random() * ((20 - 1) + 1));
        if (randomNum == 2) {
            this.toggle();
        }
    }

    @Override
    public boolean repair() {
        new Loop<>(new Invoke<>(this::genRandomNum)).scheduleOn(this).dispose();

        new Wait<>(10);
        return true;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(
            new Invoke<>(this::genRandomNum)
        ).scheduleOn(this).dispose();
    }
}
