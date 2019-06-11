package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Color;

public class PowerSwitch extends AbstractActor{
    private Switchable tool;

    public Switchable getDevice(Switchable tool) {
        return tool;
    }


    public PowerSwitch(Switchable tool){
        this.tool = tool;
        Animation animationOn;
        animationOn = new Animation("sprites/switch.png",16,16);
        setAnimation(animationOn);

    }

    public void switchOn() {
        if(tool != null) tool.turnOn();
    }

    public void switchOff() {
        if(tool != null) tool.turnOff();
        getAnimation().setTint(Color.GRAY);
    }

    public void toggle(){
        if(tool == null){
            return;
        }
        if (tool.isOn()) {
            tool.turnOff();
        } else tool.turnOn();
    }

}
