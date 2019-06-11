package sk.tuke.kpi.oop.game.other;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;

public class PerpetualReactorHeating extends AbstractAction<Reactor>  {
    private int increment;

    public PerpetualReactorHeating(int increment) {
        this.increment = increment;
    }


    @Override
    public void execute(float deltaTime) {
        if(getActor() != null) getActor().increaseTemperature(increment);
    }


}
