package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.graphics.Animation;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;


public class ChainBomb extends TimeBomb {

    private int timer;

    public boolean isActivated() {
        return activated;
    }

    private boolean activated;
    private Animation explosion;
    private Animation activatedBomb;
    private Rectangle2D.Float rectangle;


    public void setRectangle(Rectangle2D.Float rectangle) {
        this.rectangle = rectangle;
    }



    public ChainBomb(int timer) {
        super(timer);
        this.timer = timer ;
        //Animation bomb;
        //Circle circle;
        //bomb = new Animation("images/bomb.png",16,16,10);
        activatedBomb = new Animation("images/bomb_activated.png",16,16,10);
        explosion = new Animation("images/small_explosion.png",16,16,10);
        //circle = new Circle(getX() + 8,getY() +8,50);
        //rectangle = new Rectangle(getX() +8,getY() +8, getWidth(), getHeight());

    }



    @Override
    public void activate(){
        activated = true;
        setAnimation(activatedBomb);
    }

    public void act() {
        Ellipse2D.Float circle;
        if(!activated){
            return;
        }
        circle = new Ellipse2D.Float(50, 50, 50, 50);
        this.timer --;
        if(this.timer == 0){
            getScene().forEach(actor -> {
                if(actor instanceof ChainBomb){
                    ((ChainBomb) actor).rectangle = new Rectangle2D.Float(actor.getPosX() ,actor.getPosY() , 16, 16);
                }
                if(actor instanceof ChainBomb && circle.intersects(((ChainBomb) actor).rectangle)){
                    ((ChainBomb) actor).activate();
                }
            });
        }
        if(this.timer == 0){
            setAnimation(explosion);

        }
        if(this.timer == -20){
            this.getScene().removeActor(this);
        }
        //this.timer --;
    }
}
