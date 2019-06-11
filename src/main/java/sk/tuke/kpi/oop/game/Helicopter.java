package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.framework.AbstractActor;

public class Helicopter extends AbstractActor {

    private int x;
    private int y;
    private boolean setToKill;
    private Player player;

    public Helicopter(Player player){
        Animation heli;
        setToKill = false;
        heli = new Animation("sprites/heli.png",64,64,0.1f, Animation.PlayMode.LOOP);
        setAnimation(heli);
        x = this.getPosX();
        y = this.getPosY();
        this.player = player;
    }

    public void searchAndDestroy(){
        this.setToKill = true;
    }
    private void logic() {
        if(setToKill){
            x = this.getPosX();
            y = this.getPosY();
            if(x > player.getPosX()) {
                x --;
            }else{
                x ++;
            }
            if(y > player.getPosY()){
                y --;
            }else{
                y ++;
            }
            this.setPosition(x,y);
            if(this.intersects(player)){
                int energy = player.getEnergy() - 1;
                player.setEnergy(energy);
            }
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Invoke<>(this::searchAndDestroy).scheduleOn(this);
        new Loop<>(new Invoke<>(this::logic)).scheduleOn(this);
    }
}

